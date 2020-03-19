package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tahumarket.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName, etPassword;
    private LinearLayout divLogin;

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
                    Intent daftar = new Intent(LoginActivity.this, DasboardActivity.class);
                    LoginActivity.this.startActivity(daftar);
                } else {
                    Toast.makeText(LoginActivity.this, "Ussername atau Password Salah", Toast.LENGTH_SHORT).show();
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
