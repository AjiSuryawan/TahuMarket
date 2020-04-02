package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tahumarket.R;
import com.example.tahumarket.helper.Config;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName, etPassword;
    private LinearLayout divLogin;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding();


        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        divLogin.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                String id = etUserName.getText().toString();
                String pass = etPassword.getText().toString();
                if (id.equals("admin") && pass.equals("admin")) {
                    SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String urlProduk = sp.getString(Config.CONFIG_URL_PRODUK,"");
                    if (urlProduk.equalsIgnoreCase("")) {
                        SweetAlertDialog sDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE);
                        sDialog.setTitleText("Oops...");
                        sDialog.setContentText("Aplikasi harus di konfigurasi menggunakan id super admin");
                        sDialog.setCancelable(false);
                        sDialog.show();
                    }else{
//                        preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        sp.edit()
                                .putString(Config.LOGIN_ID_SHARED_PREF, Config.ROLE_ADMIN)
                                .apply();
                        Intent daftar = new Intent(LoginActivity.this, DasboardActivity.class);
                        LoginActivity.this.startActivity(daftar);
                        finish();
                    }
                } else if (id.equals("superAdmin001") && pass.equals("superAdmin001")) {
                    SharedPreferences sp = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    String urlProduk = sp.getString(Config.CONFIG_URL_PRODUK,"");
                    if (urlProduk.equalsIgnoreCase("")) {
                        SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE);
                        pDialog.setTitleText("Aplikasi harus di konfigurasi");
                        pDialog.setCancelable(false);
                        pDialog.setConfirmText("Ya");
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent daftar = new Intent(LoginActivity.this, ConfigurasiActivity.class);
                                LoginActivity.this.startActivity(daftar);
                                finish();
                            }
                        });
                        pDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        });
                        pDialog.show();
                    }else {
//                        preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        sp.edit()
                                .putString(Config.LOGIN_ID_SHARED_PREF, Config.ROLE_ADMIN)
                                .apply();
                        Intent daftar = new Intent(LoginActivity.this, DasboardActivity.class);
                        LoginActivity.this.startActivity(daftar);
                        finish();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Username atau Password Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void binding(){
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        divLogin = findViewById(R.id.divLogin);
    }
}
