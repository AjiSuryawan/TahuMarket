package com.example.tahumarket.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.tahumarket.R;
import com.example.tahumarket.adapter.AddOrderAdapter;
import com.example.tahumarket.helper.Config;
import com.example.tahumarket.model.NotaModel;
import com.example.tahumarket.model.ProdukModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class OrderCreatActivity extends AppCompatActivity implements AddOrderAdapter.ContactsAdapterListener {
    private Toolbar toolbar;
    private EditText etDateTime, etId, etNamaPemesan, etDiskon, etPPN, etSearch;
    private TextInputLayout divEtDiskon;
    private CheckBox cbDiskon;
    private ImageView ivSearch;
    private LinearLayout divSinkronData;
    private RecyclerView rvTambahOrder;
    private ArrayList<NotaModel> mProdukList = new ArrayList<>();
    NotaModel notaModel;
    private AddOrderAdapter produkAdapter;
    private Realm realm;
    int posisi = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("jumlah") != null) {
            //refresh list
//            Toast.makeText(this, "jumlah : "+data.getStringExtra("jumlah"), Toast.LENGTH_SHORT).show();
//            mProdukList.get(posisi).setJumlahbarang(Integer.parseInt(data.getStringExtra("jumlah")));
//            produkAdapter.notifyDataSetChanged();
            String id = data.getStringExtra("kode");
            int jumlah = Integer.parseInt(data.getStringExtra("jumlah"));
            for (int i = 0; i < mProdukList.size(); i++) {
                if (mProdukList.get(i).getKodeBarang().equalsIgnoreCase(id)) {
                    mProdukList.get(i).setJumlahbarang(jumlah);
                    produkAdapter.notifyDataSetChanged();
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        RealmResults<ProdukModel> produkModel = realm.where(ProdukModel.class).findAll();
        for (int i = 0; i < produkModel.size(); i++) {
            notaModel = new NotaModel();
            notaModel.setKodeBarang(produkModel.get(i).getKodeBarang());
            notaModel.setNamaBarang(produkModel.get(i).getNamaBarang());
            notaModel.setKodePackaging(produkModel.get(i).getKodePackaging());
            notaModel.setHargaBarang(produkModel.get(i).getHargaBarang());
            notaModel.setKodeWarna(produkModel.get(i).getKodeWarna());
            notaModel.setKodePackaging(produkModel.get(i).getKodePackaging());
            mProdukList.add(notaModel);
        }

        binding();
        rvTambahOrder = findViewById(R.id.rvTambahOrder);
        rvTambahOrder.setHasFixedSize(true);
        rvTambahOrder.setLayoutManager(new GridLayoutManager(this, 3));
        produkAdapter = new AddOrderAdapter(this, mProdukList, this);
//        produkAdapter = new AddOrderAdapter(this, mProdukList, new AddOrderAdapter.Callback() {
//            @Override
//            public void onClick(int position) {
//                posisi=position;
//                NotaModel nota=mProdukList.get(position);
//                Intent in= new Intent(getApplicationContext(), AddQtyOrder.class);
//                int qtyExtra = nota.getJumlahbarang();
//                in.putExtra("jumlah" , qtyExtra);
//                in.putExtra("kode" , nota.getKodeBarang());
//                startActivityForResult(in, 23);
//                Log.d("klik", "onClick: "+qtyExtra+" , kode : "+nota.getKodeBarang());
//            }
//
//            @Override
//            public void test() {
//
//            }
//        });
        rvTambahOrder.setAdapter(produkAdapter);


        //set orientation
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //hide keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        etDateTime.setText(currentDate + "/" + currentTime);
        etDateTime.setEnabled(false);
        String currentDateId = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(new Date());
        String currentTimeId = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        etId.setText(currentDateId + currentTimeId + Config.randomString(5).toUpperCase());

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
                if (b) {
                    divEtDiskon.setVisibility(View.VISIBLE);
                    divEtDiskon.setEnabled(true);
                }
                if (cbDiskon.isChecked() == false) {
                    divEtDiskon.setVisibility(View.GONE);
                    divEtDiskon.setEnabled(false);
                }
            }
        });


    }

    private void binding() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tambah Order");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(OrderCreatActivity.this)
                        .setTitle("Really Exit?")
                        .setMessage("Are you sure you want to exit ?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                            }
                        }).create().show();
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
                produkAdapter.getFilter().filter(etSearch.getText().toString());
            }
        });

        divSinkronData = findViewById(R.id.divSinkronData);
        divSinkronData.setOnClickListener(new View.OnClickListener() {
            private void doNothing() {

            }

            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                int countbeli = 0;
                                for (int i = 0; i < mProdukList.size(); i++) {
                                    if (mProdukList.get(i).getJumlahbarang() > 0) {
                                        countbeli++;
                                    }
                                }
                                Log.d("jumlah beli", "onClick: " + countbeli + "");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderCreatActivity.this);
                builder.setMessage("Are you sure want to save data ?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });


    }

    @Override
    public void onContactSelected(NotaModel contact) {
        Intent in = new Intent(getApplicationContext(), AddQtyOrder.class);
        int qtyExtra = contact.getJumlahbarang();
        in.putExtra("jumlah", qtyExtra);
        in.putExtra("kode", contact.getKodeBarang());
        startActivityForResult(in, 23);
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getKodeBarang() + ", " + contact.getNamaBarang(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (!etSearch.getText().toString().matches("")){
            etSearch.setText("");
            produkAdapter.getFilter().filter(etSearch.getText().toString());
        }else{
            Log.d("kosong", "onBackPressed: ");
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit ?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    }).create().show();
        }
    }
}
