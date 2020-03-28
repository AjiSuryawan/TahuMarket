package com.example.tahumarket.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tahumarket.R;
import com.example.tahumarket.adapter.HeaderAdapter;
import com.example.tahumarket.adapter.ProdukAdapter;
import com.example.tahumarket.helper.RealmHelperDetailNota;
import com.example.tahumarket.helper.RealmHelperHeaderNota;
import com.example.tahumarket.model.HeaderNotaModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private LinearLayout divTambahDataOrder, divSinkronData, divPickDate;

    private DatePickerDialog.OnDateSetListener setListener;
    private Calendar calendar = Calendar.getInstance();
    private final int year = calendar.get(Calendar.YEAR);
    private final int month = calendar.get(Calendar.MONTH);
    private final int day = calendar.get(Calendar.DAY_OF_MONTH);
    private TextView tvDate;
    private ImageView ivResetDate;
    private String txtDate;

    private RecyclerView rvDaftarOrder;
    List<HeaderNotaModel> mList;
//    private ArrayList mList = new ArrayList<HeaderNotaModel>();
    private HeaderAdapter mAdapter;

    RealmHelperHeaderNota realmHelperHeaderNota;
    RealmHelperDetailNota realmHelperDetail;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        binding();

        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelperHeaderNota = new RealmHelperHeaderNota(realm);
        realmHelperDetail = new RealmHelperDetailNota(realm);

        mList = new ArrayList<>();
        txtDate = tvDate.getText().toString();
        if (txtDate.equalsIgnoreCase("Pilih Tanggal")){
            mList =realmHelperHeaderNota.getAllHeader();
        }else{
            mList = realmHelperHeaderNota.getHeaderNotaByDate(txtDate);
        }
        rvDaftarOrder.setLayoutManager(new GridLayoutManager(this, 3));
        show();
    }

    private void show(){
        mAdapter = new HeaderAdapter(this,mList);
        rvDaftarOrder.setHasFixedSize(true);
        rvDaftarOrder.setAdapter(mAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (txtDate.equalsIgnoreCase("Pilih Tanggal")){
            mList =realmHelperHeaderNota.getAllHeader();
        }else{
            mList = realmHelperHeaderNota.getHeaderNotaByDate(txtDate);
        }
        show();
        mAdapter.notifyDataSetChanged();
    }

    private void binding(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Order");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        divTambahDataOrder = findViewById(R.id.divTambahDataOrder);
        divTambahDataOrder.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, OrderCreatActivity.class);
                startActivity(intent);
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

        tvDate = findViewById(R.id.tvDate);

        ivResetDate = findViewById(R.id.ivResetDate);
        ivResetDate.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                txtDate = "Pilih Tanggal";
                tvDate.setText("Pilih Tanggal");
                onRestart();
            }
        });

        divPickDate = findViewById(R.id.divPickDate);
        divPickDate.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        rvDaftarOrder = findViewById(R.id.rvDaftarOrder);
    }

    private void datePicker (){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                txtDate = day+"/"+month+"/"+year;
                tvDate.setText(txtDate);
                onRestart();
            }
        },year,month,day);
        datePickerDialog.show();
    }
}
