package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tahumarket.R;

public class DasboardActivity extends AppCompatActivity {
    private LinearLayout divMenuProduk, divMenuOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void binding(){
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
                Intent intent = new Intent(DasboardActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
