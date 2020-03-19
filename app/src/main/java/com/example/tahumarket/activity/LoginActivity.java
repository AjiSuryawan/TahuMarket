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
                    preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    preferences.edit()
                            .putString(Config.LOGIN_ID_SHARED_PREF, id)
                            .apply();
                    Intent daftar = new Intent(LoginActivity.this, DasboardActivity.class);
                    LoginActivity.this.startActivity(daftar);
                    finish();
                } else {
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
