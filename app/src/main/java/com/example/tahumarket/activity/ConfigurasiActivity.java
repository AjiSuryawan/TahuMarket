package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tahumarket.R;
import com.example.tahumarket.helper.Config;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ConfigurasiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etURLProduk, etURLOrder, etPPN, etDiskon;
    private LinearLayout divSimpan;
    private SharedPreferences configurasi;
//    String txtURLProduk = "", txtURLOrder = "", txtPPN = "", txtDiskon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurasi);
        binding();
        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final String txtURLProduk = etURLProduk.getText().toString();
        final String txtURLOrder = etURLOrder.getText().toString();
        final String txtPPN = etPPN.getText().toString();
        final String txtDiskon = etDiskon.getText().toString();

        divSimpan.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                Log.d("RBA", "onError: " + txtURLProduk + "\n" + txtURLOrder + "\n" + txtPPN + "\n" + txtDiskon + "\n");
                SweetAlertDialog pDialog = new SweetAlertDialog(ConfigurasiActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Yakin untuk menyimpan konfigurasi?");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Yakin");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (txtURLProduk.isEmpty() || txtURLOrder.isEmpty() || txtPPN.isEmpty() || txtDiskon.isEmpty()){
                            Toast.makeText(ConfigurasiActivity.this, "Harap lengkapi isian yang tersedia", Toast.LENGTH_SHORT).show();
                        }else{
                            configurasi = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            configurasi.edit()
                                    .putString(Config.CONFIG_URL_PRODUK, txtURLProduk)
                                    .putString(Config.CONFIG_URL_ORDER, txtURLOrder)
                                    .putString(Config.CONFIG_PPN, txtPPN)
                                    .putString(Config.CONFIG_DISKON, txtDiskon)
                                    .apply();
                            Intent daftar = new Intent(ConfigurasiActivity.this, DasboardActivity.class);
                            ConfigurasiActivity.this.startActivity(daftar);
                            finish();
                        }
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


    }

    private void binding() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Configurasi Awal");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etURLProduk = findViewById(R.id.etURLProduk);
        etURLOrder = findViewById(R.id.etURLOrder);
        etPPN = findViewById(R.id.etPPN);
        etDiskon = findViewById(R.id.etDiskon);
        divSimpan = findViewById(R.id.divSimpan);
    }
}
