package com.example.tahumarket.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tahumarket.R;
import com.example.tahumarket.adapter.DetailNotaAdapter;
import com.example.tahumarket.adapter.HeaderAdapter;
import com.example.tahumarket.helper.RealmHelperDetailNota;
import com.example.tahumarket.helper.RealmHelperHeaderNota;
import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.NotaModel;
import com.example.tahumarket.model.ProdukModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class OrderViewActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText etDateTime, etId, etNamaPemesan, etPPN, etDiskon, etTotalKotor, etTotalBersih ;
    private TextView tvBayar, tvKembalian;
    private TextInputLayout divEtDiskon;
    private CheckBox cbDiskon;

    private RecyclerView rvViewOrder;
    List<NotaModel> mList;
    private DetailNotaAdapter mAdapter;

    RealmHelperDetailNota realmHelperDetailNota;
    Realm realm;

    String txtNoNota, txtNoCustomer, txtTransDate, txttotalOrigin, txtPPN, txtDiscount, txtGrandTotal, txtPayment, txtKembalian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        //set Orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        binding();

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelperDetailNota = new RealmHelperDetailNota(realm);

        txtNoNota = getIntent().getStringExtra("noNota");
        txtNoCustomer = getIntent().getStringExtra("noCustomer");
        txtTransDate = getIntent().getStringExtra("transDate");
        txttotalOrigin = getIntent().getStringExtra("totalOrigin");
        txtPPN = getIntent().getStringExtra("ppn");
        txtDiscount = getIntent().getStringExtra("discount");
        txtGrandTotal = getIntent().getStringExtra("grandTotal");
        txtPayment = getIntent().getStringExtra("payment");
        txtKembalian = getIntent().getStringExtra("kembalian");

        etDateTime.setText(txtTransDate);
        etId.setText(txtNoNota);
        etNamaPemesan.setText(txtNoCustomer);
        etPPN.setText(txtPPN);
        etDiskon.setText(txtDiscount);
        etTotalKotor.setText(txttotalOrigin);
        etTotalBersih.setText(txtGrandTotal);
        tvBayar.setText(txtPayment);
        tvKembalian.setText(txtKembalian);

        mList = new ArrayList<>();
        mList = realmHelperDetailNota.getAllDetailNotaById(txtNoNota);
        rvViewOrder.setLayoutManager(new GridLayoutManager(this, 3));
        show();
    }

    private void show(){
        mAdapter = new DetailNotaAdapter(this,mList);
        rvViewOrder.setHasFixedSize(true);
        rvViewOrder.setAdapter(mAdapter);
    }

    private void binding() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List Barang");
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
        etTotalKotor = findViewById(R.id.etTotalKotor);
        etTotalBersih = findViewById(R.id.etTotalBersih);
        tvBayar = findViewById(R.id.tvBayar);
        tvKembalian = findViewById(R.id.tvKembalian);

        rvViewOrder = findViewById(R.id.rvViewOrder);
    }

}
