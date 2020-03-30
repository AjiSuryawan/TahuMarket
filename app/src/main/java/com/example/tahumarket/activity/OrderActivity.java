package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.tahumarket.R;
import com.example.tahumarket.adapter.HeaderAdapter;
import com.example.tahumarket.adapter.ProdukAdapter;
import com.example.tahumarket.helper.Config;
import com.example.tahumarket.helper.RealmHelperDetailNota;
import com.example.tahumarket.helper.RealmHelperHeaderNota;
import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.NotaModel;
import com.example.tahumarket.model.ProdukModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout divTambahDataOrder, divSinkronData, divPickDate;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private TextView tvDate, tvDateKosong;
    private ImageView ivResetDate;
    private String currentDate;
    private String txtDate;
    private String bulan, hari;

    private RecyclerView rvDaftarOrder;
    List<HeaderNotaModel> mList;
    private HeaderAdapter mAdapter;

    List<NotaModel> mListNota;

    RealmHelperHeaderNota realmHelperHeaderNota;
    RealmHelperDetailNota realmHelperDetailNota;
    Realm realm;

    String dataHeader = "";
    String dataNota = "";

    NotaModel nota;

    private LinearLayout divNotaKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelperHeaderNota = new RealmHelperHeaderNota(realm);
        realmHelperDetailNota = new RealmHelperDetailNota(realm);

        mList = new ArrayList<>();
        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        txtDate = currentDate;
        tvDate.setText(txtDate);
        if (txtDate.equalsIgnoreCase("Pilih Tanggal")){
            mList =realmHelperHeaderNota.getAllHeader();
        }else{
            mList = realmHelperHeaderNota.getHeaderNotaByDate(txtDate);
            if (mList.size() == 0){
                divNotaKosong.setVisibility(View.VISIBLE);
                tvDateKosong.setText("Nota Tanggal " + txtDate);
                rvDaftarOrder.setVisibility(View.GONE);
            }else {
                divNotaKosong.setVisibility(View.GONE);
                rvDaftarOrder.setVisibility(View.VISIBLE);
            }
        }
        rvDaftarOrder.setLayoutManager(new GridLayoutManager(this, 3));
        show();
    }

    private void show(){
        mAdapter = new HeaderAdapter(this,mList);
        rvDaftarOrder.setHasFixedSize(true);
        rvDaftarOrder.setAdapter(mAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (txtDate.equalsIgnoreCase("Pilih Tanggal")){
            mList =realmHelperHeaderNota.getAllHeader();
        }else{
            mList = realmHelperHeaderNota.getHeaderNotaByDate(txtDate);
            if (mList.size() == 0){
                divNotaKosong.setVisibility(View.VISIBLE);
                tvDateKosong.setText("Nota Tanggal " + txtDate);
                rvDaftarOrder.setVisibility(View.GONE);
            }else {
                divNotaKosong.setVisibility(View.GONE);
                rvDaftarOrder.setVisibility(View.VISIBLE);
            }
        }
        show();
        mAdapter.notifyDataSetChanged();
    }

    private void binding(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Order");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        divTambahDataOrder = findViewById(R.id.divTambahDataOrder);
        divTambahDataOrder.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OrderCreatActivity.class);
                startActivity(intent);
            }
        });

        divSinkronData = findViewById(R.id.divSinkronData);
        divSinkronData.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                int Size = mList.size();
                if (Size == 0){
                    SweetAlertDialog qDialog = new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.ERROR_TYPE);
                    qDialog.setTitleText("Oops...");
                    qDialog.setContentText("Belum ada data nota yang perlu disinkronisasi");
                    qDialog.setCancelable(false);
                    qDialog.show();
                    Log.d("RBA", txtDate);
                }else {
                    onActionSinkron(txtDate);
                }
            }
        });

        tvDate = findViewById(R.id.tvDate);

        ivResetDate = findViewById(R.id.ivResetDate);
        ivResetDate.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
//                txtDate = "Pilih Tanggal";
                tvDate.setText(currentDate);
                onRestart();
            }
        });

        divPickDate = findViewById(R.id.divPickDate);
        divPickDate.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        rvDaftarOrder = findViewById(R.id.rvDaftarOrder);
        divNotaKosong = findViewById(R.id.divNotaKosong);
        tvDateKosong = findViewById(R.id.tvDateKosong);
    }

    private void onActionSinkron (final String date){
        SweetAlertDialog aDialog = new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.WARNING_TYPE);
        aDialog.setTitleText("Yakin sinkronisasi data nota pada " + date +" ?");
        aDialog.setCancelable(false);
        aDialog.setConfirmText("Yakin");
        aDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
                if (date.equalsIgnoreCase("Pilih Tanggal")){
                    SweetAlertDialog qDialog = new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.ERROR_TYPE);
                    qDialog.setTitleText("Oops...");
                    qDialog.setContentText("Silahkan pilih hari untuk sinkronisasi nota");
                    qDialog.setCancelable(false);
                    qDialog.show();
                }
                else{
                    final SweetAlertDialog pDialog = new SweetAlertDialog(OrderActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading ...");
                    pDialog.setCancelable(true);
                    pDialog.show();
                    for (int i = 0; i < mList.size() ; i++) {
                        HeaderNotaModel header = mList.get(i);
                        if (header.getTransdate().equalsIgnoreCase(date)){
                            dataHeader = header.getNoNota() + ";" + header.getNoCustomer() + ";" + header.getTransdate() + ";" + header.getTotalOrigin() + ";" + header.getPpn() + ";" + header.getDiscount() + ";" + header.getGrandTotal() + ";" + header.getPayment() + ";" + header.getKembalian();
                            mListNota = new ArrayList<>();
                            mListNota = realmHelperDetailNota.getAllDetailNotaById(header.getNoNota());
                            dataNota = "";
                            for (int j = 0; j < mListNota.size() ; j++) {
                                nota = mListNota.get(j);
                                int lastIndex = mListNota.size()-1;
                                if (j == lastIndex){
                                    dataNota += nota.getKodeNota() + ";" + nota.getKodeBarang() + ";" + nota.getNamaBarang() + ";" + nota.getJumlahbarang() + ";" + nota.getHargaBarang() + ";" + nota.getSubtotal();
                                }
                                else {
                                    dataNota += nota.getKodeNota() + ";" + nota.getKodeBarang() + ";" + nota.getNamaBarang() + ";" + nota.getJumlahbarang() + ";" + nota.getHargaBarang() + ";" + nota.getSubtotal() + "#";
                                }
                            }
//                    Log.d("RBA", "Item : " + header.getNoCustomer());
//                    Log.d("RBA", "Header : "+dataHeader);
//                    Log.d("RBA", "Detail Nota : "+dataNota);
                            sendNotaToServer(header.getNoNota(), dataHeader, dataNota);
                            int lastIndexNota = mListNota.size()-1;
                            if (i == lastIndexNota){
                                pDialog.dismissWithAnimation();
                            }
                        }
                    }
                }
            }
        });
        aDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        });
        aDialog.show();
    }

    private void datePicker (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                if(month < 10){
                    bulan = "0" + month;
                }
                else {
                    bulan = String.valueOf(month);
                }

                if (day < 10){
                    hari = "0" + day;
                }
                else {
                    hari = String.valueOf(day);
                }
                txtDate = year+ "-" + bulan + "-" + hari;
                tvDate.setText(txtDate);
                onRestart();
            }
        },year,month,day);
        datePickerDialog.show();
    }

    private void sendNotaToServer(final String noHeader, String dataHeader, String dataNota){
        Log.d("RBA", "Item : " + noHeader);
        Log.d("RBA", "Header : "+ dataHeader);
        Log.d("RBA", "Detail Nota : "+ dataNota);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SessionId", "SesionId_190630");
            jsonObject.put("Header", dataHeader);
            jsonObject.put("Detail", dataNota);
            jsonObject.put("Crud", "c");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Config.BASE_URL_API_ORDER)
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
                        Log.d("RBA", "Respon : " + dataProduk);
                        if (dataProduk.equalsIgnoreCase(noHeader + " has been sent to server!")){
                            //hapus data di realm
                            realmHelperHeaderNota.deleteHeader(noHeader);
                            realmHelperDetailNota.deleteDetail(noHeader);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("RBA", "onError Item : " + noHeader);
                        Log.d("RBA", "onError: " + error.getErrorBody());
                        Log.d("RBA", "onError: " + error.getLocalizedMessage());
                        Log.d("RBA", "onError: " + error.getErrorDetail());
                        Log.d("RBA", "onError: " + error.getResponse());
                        Log.d("RBA", "onError: " + error.getErrorCode());
                    }
                });
    }
}
