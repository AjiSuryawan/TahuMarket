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
    String idbarang;
    int valueadd = 0;
    int valueremove = 0;


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
        idbarang = getIntent().getStringExtra("kode");
        final int qtyExtras = getIntent().getIntExtra("jumlah" , 0);
        if (qtyExtras == 0){
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
                    if (valueremove == 0){
                        Toast.makeText(AddQtyOrder.this, "Jumlah barang tidak boleh 0", Toast.LENGTH_SHORT).show();
                    }else{
                        valueremove--;
                        String qtyremove = String.valueOf(valueremove);
                        txtjumlah.setText(qtyremove);
                        valueadd = valueremove;
                    }
                }
            });
        }else {
            txtjumlah.setText(String.valueOf(qtyExtras));
            valueadd = qtyExtras;
            valueremove = qtyExtras;
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
                    if (valueremove == 0){

                    }else{
                        valueremove--;
                        String qtyremove = String.valueOf(valueremove);
                        txtjumlah.setText(qtyremove);
                        valueadd = valueremove;
                    }
                }
            });
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String qtyDone = txtjumlah.getText().toString();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("jumlah", qtyDone);
                    returnIntent.putExtra("kode", idbarang);
                    setResult(23, returnIntent);
                    finish();
            }
        });
    }
}
