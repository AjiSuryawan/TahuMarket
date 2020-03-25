package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tahumarket.R;
import com.example.tahumarket.helper.Config;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderCreatActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etDateTime, etId, etNamaPemesan,etDiskon, etPPN, etSearch;
    private TextInputLayout divEtDiskon;
    private CheckBox cbDiskon;
    private ImageView ivSearch;
    private LinearLayout divSinkronData;
    private RecyclerView rvTambahOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        etDateTime.setText(currentDate+"/"+currentTime);
        etDateTime.setEnabled(false);
        String currentDateId = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
        String currentTimeId = new SimpleDateFormat("HH/mm/ss", Locale.getDefault()).format(new Date());
        etId.setText(currentTimeId+"/"+Config.randomString(5).toUpperCase());

//        if(cbDiskon.isChecked()==true)
//        {
//            cbDiskon.setTag("Y");
//        }
//        if(cbDiskon.isChecked()==false)
//        {
//            cbDiskon.setTag("N");
//        }


        cbDiskon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    divEtDiskon.setVisibility(View.VISIBLE);
                    divEtDiskon.setEnabled(true);
                }
                if(cbDiskon.isChecked()==false)
                {
                    divEtDiskon.setVisibility(View.GONE);
                    divEtDiskon.setEnabled(false);
                }
            }
        });


    }
    private void binding(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Order");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etDateTime = findViewById(R.id.etDateTime);

        etId = findViewById(R.id.etId);

        etNamaPemesan = findViewById(R.id.etNamaPemesan);

        cbDiskon = findViewById(R.id.cbDiskon);
        etDiskon = findViewById(R.id.etDiskon);
        divEtDiskon = findViewById(R.id.divEtDiskon);

        etPPN = findViewById(R.id.etPPN);

        etSearch = findViewById(R.id.etSearch);
        ivSearch = findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {

            }
        });

        divSinkronData = findViewById(R.id.divSinkronData);
        divSinkronData.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {

            }
        });

        rvTambahOrder = findViewById(R.id.rvTambahOrder);
    }
}
