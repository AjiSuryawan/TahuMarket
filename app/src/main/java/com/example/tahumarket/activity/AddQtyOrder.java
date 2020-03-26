package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tahumarket.R;

public class AddQtyOrder extends AppCompatActivity {

    private EditText txtjumlah;
    private LinearLayout btnSubmit;
    private ImageView divAdd, divRemove;
    String qtyadd;
    int valueadd = 1;
    int valueremove = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qty_order);
        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtjumlah = findViewById(R.id.txtjumlah);
        btnSubmit = findViewById(R.id.btnsubmit);
        divAdd = findViewById(R.id.divAdd);
        divRemove = findViewById(R.id.divRemove);


        divAdd.setOnClickListener(new View.OnClickListener() {
            int qty = Integer.parseInt(txtjumlah.getText().toString());
            @Override
            public void onClick(View v) {
                valueadd++;
                String qtyadd = String.valueOf(valueadd);
                txtjumlah.setText(qtyadd);
                valueremove = valueadd;
            }
        });

        divRemove.setOnClickListener(new View.OnClickListener() {
            int qty = Integer.parseInt(txtjumlah.getText().toString());
            @Override
            public void onClick(View v) {
                if (valueremove == 1){
                    Toast.makeText(AddQtyOrder.this, "Jumlah barang tidak boleh 0", Toast.LENGTH_SHORT).show();
                }else{
                    valueremove--;
                    String qtyremove = String.valueOf(valueremove);
                    txtjumlah.setText(qtyremove);
                    valueadd = valueremove;
                }

//                Toast.makeText(AddQtyOrder.this, "OK" + qty, Toast.LENGTH_SHORT).show();
//                int qtyku = Integer.parseInt(qtyadd);
//                value--;
//                String qtymin = String.valueOf(value);
//                txtjumlah.setText(qtymin);
//                Toast.makeText(AddQtyOrder.this, "min" + qtyku, Toast.LENGTH_SHORT).show();
//                String qtyadd = String.valueOf(qty);
//                txtjumlah.setText(qtyadd);
//                if (qty == 0){
//                    Toast.makeText(AddQtyOrder.this, "Jumlah barang tidak boleh 0", Toast.LENGTH_SHORT).show();
//                }else{
//                    qty--;
//                    String qtyRemove = String.valueOf(qty);
//                    txtjumlah.setText(qtyRemove);
//                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("jumlah", txtjumlah.getText().toString());
                setResult(23, returnIntent);
                finish();
            }
        });
    }
}
