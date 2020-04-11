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
    private String txtURLProduk, txtURLOrder, txtPPN, txtDiskon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurasi);
        binding();
        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        txtURLProduk = etURLProduk.getText().toString();
        txtURLOrder = etURLOrder.getText().toString();
        txtPPN = etPPN.getText().toString();
        txtDiskon = etDiskon.getText().toString();

        divSimpan.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(ConfigurasiActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Yakin untuk menyimpan konfigurasi?");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Yakin");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (etURLProduk.getText().toString().isEmpty() || etURLOrder.getText().toString().isEmpty() || etPPN.getText().toString().isEmpty() || etDiskon.getText().toString().isEmpty()){
                            Toast.makeText(ConfigurasiActivity.this, "Harap lengkapi isian yang tersedia", Toast.LENGTH_SHORT).show();
                        }else{
                            configurasi = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            configurasi.edit()
                                    .putString(Config.LOGIN_ID_SHARED_PREF, Config.ROLE_SUPER_ADMIN)
                                    .putString(Config.CONFIG_URL_PRODUK, etURLProduk.getText().toString())
                                    .putString(Config.CONFIG_URL_ORDER, etURLOrder.getText().toString())
                                    .putString(Config.CONFIG_PPN, etPPN.getText().toString())
                                    .putString(Config.CONFIG_DISKON, etDiskon.getText().toString())
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
