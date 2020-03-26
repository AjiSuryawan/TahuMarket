package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tahumarket.R;

public class AddQtyOrder extends AppCompatActivity {

    EditText txtjumlah;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qty_order);
        txtjumlah = (EditText)findViewById(R.id.txtjumlah);
        btnSubmit = (Button)findViewById(R.id.btnsubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("id", txtjumlah.getText().toString());
                setResult(23, returnIntent);
                finish();
            }
        });
    }
}
