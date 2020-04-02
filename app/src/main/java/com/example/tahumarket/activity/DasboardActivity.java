package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tahumarket.R;
import com.example.tahumarket.helper.Config;
import com.example.tahumarket.model.ProdukModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DasboardActivity extends AppCompatActivity {
    private LinearLayout divMenuProduk, divMenuOrder;
    private ProdukModel produkModel;
    private ArrayList mProdukList = new ArrayList<ProdukModel>();
    private Realm realm;
    private ImageView ivSetting, ivLogout;
    private SharedPreferences preferences;
    private String URL_PRODUK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

        preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        URL_PRODUK = preferences.getString(Config.CONFIG_URL_PRODUK,"");

        fetchDataFromDb();



    }

    private void binding(){
        ivSetting = findViewById(R.id.ivSetting);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DasboardActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        ivLogout = findViewById(R.id.ivLogout);
        ivLogout.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(DasboardActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Yakin Log Out?");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Yakin");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Config.forceLogout(DasboardActivity.this);
                    }
                });
                pDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
            }
        });

        divMenuProduk = findViewById(R.id.divMenuProduk);
        divMenuProduk.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DasboardActivity.this, ProdukActivity.class);
                startActivity(intent);
            }
        });

        divMenuOrder = findViewById(R.id.divMenuOrder);
        divMenuOrder.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
                if (produkModel.size() <= 0) {
                    SweetAlertDialog sDialog = new SweetAlertDialog(DasboardActivity.this, SweetAlertDialog.ERROR_TYPE);
                    sDialog.setTitleText("Oops...");
                    sDialog.setContentText("Data produk perlu di sinkronisasi pada menu produk");
                    sDialog.setCancelable(false);
                    sDialog.show();
                }else{
                    Intent intent = new Intent(DasboardActivity.this, OrderActivity.class);
                    startActivity(intent);
                }
            }
        });
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
            }
        }
        catch (Exception e) {
            Log.e(TAG, "fetchProdukFromDb: " + e.getLocalizedMessage());
        }
    }

    private void fetchDataProdukAPI(){
        final SweetAlertDialog pDialog = new SweetAlertDialog(DasboardActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(true);
        pDialog.show();
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
                                pDialog.dismissWithAnimation();
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
                        pDialog.dismissWithAnimation();
                        SweetAlertDialog sDialog = new SweetAlertDialog(DasboardActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sDialog.setTitleText("Oops...");
                        sDialog.setContentText(Config.TOAST_AN_ERROR + ", silahkan sinkronisasi ulang pada menu produk");
                        sDialog.setCancelable(false);
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
