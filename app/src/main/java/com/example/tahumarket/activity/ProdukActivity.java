package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tahumarket.R;
import com.example.tahumarket.adapter.ProdukAdapter;
import com.example.tahumarket.helper.Config;
import com.example.tahumarket.model.ProdukModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ProdukActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etSearch;
    private ImageView ivSearch;
    private SearchView searchProduk;
    private LinearLayout divSinkronData;
    private RecyclerView rvDaftarProduk;
    private ProdukModel produkModel;
    private ArrayList mProdukList = new ArrayList<ProdukModel>();
    private ProdukAdapter produkAdapter;
    private Realm realm;
    private ShimmerFrameLayout mShimmerViewContainer;
    private SharedPreferences preferences;
    private String URL_PRODUK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        URL_PRODUK = preferences.getString(Config.CONFIG_URL_PRODUK,"");

        searchProduk.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        searchProduk.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                mProdukList.clear();
                RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).contains("namaBarang",query, Case.INSENSITIVE).findAll();
                mProdukList.addAll(produkModel);
                produkAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) {
                    mProdukList.clear();
                    RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
                    mProdukList.addAll(produkModel);
                    produkAdapter.notifyDataSetChanged();
                }
                return true;
            }

        });

        fetchDataFromDb();
        rvDaftarProduk.setLayoutManager(new GridLayoutManager(this, 3));
        show();


    }

    private void show(){
        produkAdapter = new ProdukAdapter(this,mProdukList);
        rvDaftarProduk.setHasFixedSize(true);
        rvDaftarProduk.setAdapter(produkAdapter);
    }

    private void binding(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Produk");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchProduk = findViewById(R.id.searchProduk);
        ivSearch = findViewById(R.id.ivSearch);

        divSinkronData = findViewById(R.id.divSinkronData);
        divSinkronData.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                realm.executeTransactionAsync(new Realm.Transaction() {
                    private void doNothing() {

                    }

                    @Override
                    public void execute(Realm realm) {
                        realm.where(ProdukModel.class).findAll().deleteAllFromRealm();
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.e("RBA", "Realm onSuccess: success delete");
                        RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
                        Log.e("RBA", "Realm size: "+produkModel.size());
                        rvDaftarProduk.setVisibility(View.GONE);
                        mShimmerViewContainer.setVisibility(View.VISIBLE);
                        mProdukList.clear();
                        fetchDataProdukAPI();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.e("RBA", "Realm onError: " + error.getLocalizedMessage());
                    }
                });
            }
        });

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        rvDaftarProduk = findViewById(R.id.rvDaftarProduk);
    }

    private void fetchDataFromDb() {
        mProdukList.clear();
        try {
            RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
            if (produkModel.size() <= 0) {
                Log.d("RBA", "data produk from API ");
                fetchDataProdukAPI();
            }
            else {
                Log.d("RBA", "data produk from Realm: " + produkModel.size());
                mProdukList.addAll(produkModel);
                mShimmerViewContainer.stopShimmer();
                mShimmerViewContainer.setVisibility(View.GONE);
                rvDaftarProduk.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e) {
            Log.e(TAG, "fetchProdukFromDb: " + e.getLocalizedMessage());
        }
    }

    private void fetchDataProdukAPI(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SessionId", "SesionId_190630");
            jsonObject.put("Data", "");
            jsonObject.put("Crud", "r");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(URL_PRODUK)
                .addHeaders("Content-Type","application/json")
                .addHeaders("Accept","application/json")
                .addHeaders("Authorization","Basic V0FZSFlhV0EzZlhTTU83anVJZzJmZz09OlF3NUNNWld4TlQwRUNDRmZhK2g4MmVjSWcvREFEeFM3")
                .addJSONObjectBody(jsonObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String dataProduk = response.optString("Data");
                        String [] dataItemProduk = dataProduk.split(Pattern.quote("#"));
                        for (int i = 0; i < dataItemProduk.length ; i++) {
                            produkModel = new ProdukModel();
                            String [] dataFieldProduk = dataItemProduk[i].split(Pattern.quote(";"));
                            produkModel.setKodeBarang(dataFieldProduk[0]);
                            produkModel.setNamaBarang(dataFieldProduk[1]);
                            produkModel.setHargaBarang(Integer.parseInt(dataFieldProduk[2]));
                            produkModel.setKodeWarna(dataFieldProduk[3]);
                            produkModel.setKodePackaging(dataFieldProduk[4]);
                            mProdukList.add(produkModel);
                        }
                        mShimmerViewContainer.stopShimmer();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        rvDaftarProduk.setVisibility(View.VISIBLE);
                        produkAdapter.notifyDataSetChanged();
                        realm.executeTransactionAsync(new Realm.Transaction() {
                            private void doNothing() {

                            }

                            @Override
                            public void execute(Realm _realm) {
                                _realm.insertOrUpdate(mProdukList);
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                Log.e("RBA", "Realm onSuccess: success insert");
                                RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
                                Log.d("RBA", "Realm Size From Api : " + produkModel.size());
                            }
                        }, new Realm.Transaction.OnError() {
                            @Override
                            public void onError(Throwable error) {
                                Log.e("RBA", "Realm onError: " + error.getLocalizedMessage());
                            }
                        });
                    }
                    @Override
                    public void onError(ANError error) {
                        SweetAlertDialog sDialog = new SweetAlertDialog(ProdukActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sDialog.setTitleText("Oops...");
                        sDialog.setContentText(Config.TOAST_AN_ERROR);
                        sDialog.setCancelable(false);
                        sDialog.setConfirmText("Ya");
                        sDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            private void doNothing() {

                            }

                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                fetchDataProdukAPI();
                            }
                        });
                        sDialog.show();
                        Log.d("RBA", "onError: " + error.getErrorBody());
                        Log.d("RBA", "onError: " + error.getLocalizedMessage());
                        Log.d("RBA", "onError: " + error.getErrorDetail());
                        Log.d("RBA", "onError: " + error.getResponse());
                        Log.d("RBA", "onError: " + error.getErrorCode());
                    }
                });
    }

}
