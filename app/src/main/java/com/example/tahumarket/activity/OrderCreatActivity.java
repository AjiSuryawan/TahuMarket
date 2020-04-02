package com.example.tahumarket.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tahumarket.R;
import com.example.tahumarket.adapter.AddOrderAdapter;
import com.example.tahumarket.helper.Config;
import com.example.tahumarket.helper.PaymentDialog;
import com.example.tahumarket.helper.RealmHelperDetailNota;
import com.example.tahumarket.helper.RealmHelperHeaderNota;
import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.NotaModel;
import com.example.tahumarket.model.ProdukModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.stream.IntStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class OrderCreatActivity extends AppCompatActivity implements AddOrderAdapter.ContactsAdapterListener , PaymentDialog.PaymentDialogListener{
    private Toolbar toolbar;
    private EditText etDateTime, etId, etNamaPemesan, etDiskon, etPPN, etSearch;
    private TextView tvTotalBelanja;
    private TextInputLayout divEtDiskon;
    private CheckBox cbDiskon;
    private ImageView ivSearch;
    private LinearLayout divSinkronData;
    private RecyclerView rvTambahOrder;
    private ArrayList<NotaModel> mProdukList = new ArrayList<>();
    NotaModel notaModel;
    private AddOrderAdapter produkAdapter;
    private Realm realm;
    HeaderNotaModel headerNotaModel;
    String currentDate;
    int totalBayar = 0;
    int subTotalBarang = 0;

    String txtNoNota, txtNoCustomer, txtTransDate;
    int txttotalOrigin = 0;
    int txtPPN = 0;
    int txtDiscount = 0;
    int txtGrandTotal = 0;
    int txtPayment = 0;
    int txtKembalian = 0;

    private RealmHelperHeaderNota realmHelperHeader;
    private RealmHelperDetailNota realmHelperdetail;

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
            totalBayar = 0;
            for (int i = 0; i < mProdukList.size(); i++) {
                if (mProdukList.get(i).getKodeBarang().equalsIgnoreCase(id)) {
                    mProdukList.get(i).setJumlahbarang(jumlah);
                    subTotalBarang = jumlah * mProdukList.get(i).getHargaBarang();
                    mProdukList.get(i).setSubtotal(subTotalBarang);
                    produkAdapter.notifyDataSetChanged();
                }
                totalBayar += mProdukList.get(i).getJumlahbarang() * mProdukList.get(i).getHargaBarang();
            }
            Log.d("rba", "int total Bayar: "+totalBayar);
            tvTotalBelanja.setText(String.valueOf(totalBayar));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creat);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelperHeader = new RealmHelperHeaderNota(realm);
//        realmHelperdetail = new RealmHelperDetailNota(realm);

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

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        etDateTime.setText(currentDate);
        etDateTime.setEnabled(false);
        String currentDateId = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());
        String currentTimeId = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        etId.setText(currentDateId + currentTimeId + Config.randomString(5).toUpperCase());
        etId.setEnabled(false);

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
                SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.setTitleText("Yakin untuk membatalkan nota?");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Yakin");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                });
                pDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
            }
        });

        etDateTime = findViewById(R.id.etDateTime);

        etId = findViewById(R.id.etId);

        etNamaPemesan = findViewById(R.id.etNamaPemesan);

        cbDiskon = findViewById(R.id.cbDiskon);
        etDiskon = findViewById(R.id.etDiskon);
        divEtDiskon = findViewById(R.id.divEtDiskon);

        etPPN = findViewById(R.id.etPPN);

        tvTotalBelanja = findViewById(R.id.tvTotalBelanja);

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
                String namaCustomer = etNamaPemesan.getText().toString();
                if (namaCustomer.isEmpty() && totalBayar <= 0){
                    SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.ERROR_TYPE);
                        pDialog.setTitleText("Oops...");
                        pDialog.setContentText("Lengkapi nama customer dan harap tambahkan barang");
                        pDialog.setCancelable(false);
                        pDialog.show();
                }else if (namaCustomer.isEmpty() && totalBayar != 0){
                    SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Oops...");
                    pDialog.setContentText("Nama customer tidak boleh kosong");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else if (totalBayar <= 0 && !namaCustomer.isEmpty()) {
                    SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Oops...");
                    pDialog.setContentText("Harap tambahkan Barang");
                    pDialog.setCancelable(false);
                    pDialog.show();
                }else{
                    txtNoNota = etId.getText().toString();
                    txtNoCustomer = etNamaPemesan.getText().toString();
                    txtTransDate = currentDate;
                    txttotalOrigin = totalBayar;
                    //Calculate PPN
                    String textPPN = etPPN.getText().toString();
                    if (textPPN.equalsIgnoreCase("")){
                        txtPPN = 0;
                    }else{
                        int ppn = Integer.parseInt(textPPN);
                        int perSeratus = 100;
                        double doublePPN = (double) ppn / (double) perSeratus * (double) txttotalOrigin;
                        txtPPN = (int)doublePPN;
                    }
                    //Calculate Diskon
                    String disc = etDiskon.getText().toString();
                    if (disc.equalsIgnoreCase("")){
//                        int ok = txttotalOrigin  + (int)txtPPN;
//                        txtGrandTotal = ok - 0;
//                        txtDiscount = 0;
                        int ok = txttotalOrigin - 0;
                        txtGrandTotal = ok + (int)txtPPN;
                        txtDiscount = 0;
                    }else{
//                        int ok = txttotalOrigin  + (int)txtPPN;
//                        txtGrandTotal = ok - Integer.parseInt(disc);
//                        txtDiscount = Integer.parseInt(disc);
                        int disct = Integer.parseInt(disc);
                        int perSeratus = 100;
                        double doubleDisc = (double) disct / (double) perSeratus * (double) txttotalOrigin;
                        int ok = txttotalOrigin - (int)doubleDisc;
                        txtGrandTotal = ok + (int) txtPPN;
                        txtDiscount = (int)doubleDisc;
                    }
                    new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Anda yakin untuk simpan nota ?")
                            .setConfirmText("Ya")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                private void doNothing() {

                                }

                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    openDialog();

                                    //txtPayment done in Listener

                                }
                            })
                            .setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }
        });

//        divSinkronData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(OrderCreatActivity.this);
//                builder.setTitle("Yakin untuk simpan nota ?");
//                final  EditText etPayment = new EditText(OrderCreatActivity.this);
//                etPayment.setInputType(InputType.TYPE_CLASS_NUMBER);
//                builder.setView(etPayment);
//                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case DialogInterface.BUTTON_POSITIVE:
//                                //Yes button clicked
//                                //tolong muncul dialog input nominal
//
//                                //save header nya ke realm
////                                headerNotaModel = new HeaderNotaModel();
////                                headerNotaModel.setNoNota(etId.getText().toString());
////                                headerNotaModel.setTransdate(currentDate);
////                                headerNotaModel.setNoCustomer(etNamaPemesan.getText().toString());
////                                headerNotaModel.setTotalOrigin(String.valueOf(totalBayar));
//
//                                String txtPPN = etPPN.getText().toString();
//                                if (txtPPN.equalsIgnoreCase("")){
//                                    calculatePPN = 0;
//                                }else{
//                                    int ppn = Integer.parseInt(txtPPN);
//                                    int perSeratus = 100;
//                                    double doublePPN = (double) ppn / (double) perSeratus * (double) totalBayar;
//                                    calculatePPN = (int)doublePPN;
//                                }
//
////                                headerNotaModel.setPpn(String.valueOf(calculatePPN));
//
//                                String discount = etDiskon.getText().toString();
////                                if (discount.equalsIgnoreCase("")){
////                                    headerNotaModel.setDiscount("0");
////                                }else{
////                                    headerNotaModel.setDiscount(discount);
////                                }
//                                if (discount.equalsIgnoreCase("")){
//                                    int disc = totalBayar  + (int)calculatePPN;
//                                    calculateGrandTotal = disc - 0;
//                                }else{
//                                    int disc = totalBayar  + (int)calculatePPN;
//                                    calculateGrandTotal = disc - Integer.parseInt(discount);
//                                }
//
//
//                                Log.d("RBA", "headerNota: " + "\n" + "\n"
//                                       + "noNota : " + etId.getText().toString() + "\n"
//                                        + "noCustomer : " + etNamaPemesan.getText().toString() + "\n"
//                                        + "transDate : " + currentDate + "\n"
//                                        + "totalOrigin : " + totalBayar + "\n"
//                                        + "PPN : " + calculatePPN + "\n"
//                                        + "Discount : " + discount + "\n"
//                                        + "grandTotal point2 : " + calculateGrandTotal + "\n");
//
//
//                                //save detail nya ke realm
////                                for (int i = 0; i < mProdukList.size(); i++) {
////                                    if (mProdukList.get(i).getJumlahbarang() > 0) {
////
////                                    }
////                                }
//                                break;
//
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                //No button clicked
//                                break;
//                        }
//                    }
//                };
//
//
//
//
//
//            }
//        });


    }

    public void openDialog(){
        FragmentManager fm = getSupportFragmentManager();
//        PaymentDialog paymentDialog = new PaymentDialog();
        PaymentDialog titlenya = PaymentDialog.newInstance(String.valueOf(txttotalOrigin), String.valueOf(txtPPN), String.valueOf(txtDiscount), String.valueOf(txtGrandTotal));
        titlenya.show(fm, "paymentDialog");
    }

    @Override
    public void applyText(int payment) {
        txtPayment = payment;
        if (txtPayment <= txtGrandTotal){
            if (txtPayment == txtGrandTotal){
                txtKembalian = txtPayment - txtGrandTotal;
                Log.d("RBA", "headerNota: " + "\n" + "\n"
                        + "noNota : " + txtNoNota + "\n"
                        + "noCustomer : " + txtNoCustomer + "\n"
                        + "transDate : " + txtTransDate + "\n"
                        + "totalOrigin : " + txttotalOrigin + "\n"
                        + "PPN : " + txtPPN + "\n"
                        + "Discount : " + txtDiscount + "\n"
                        + "grandTotal : " + txtGrandTotal + "\n"
                        + "Payment : " + txtPayment + "\n"
                        + "Kembalian : " + txtKembalian + "\n");
                //saveHeader
                HeaderNotaModel headerNotaModel = new HeaderNotaModel();
                headerNotaModel.setNoNota(String.valueOf(txtNoNota));
                headerNotaModel.setNoCustomer(String.valueOf(txtNoCustomer));
                headerNotaModel.setTransdate(String.valueOf(txtTransDate));
                headerNotaModel.setTotalOrigin(String.valueOf(txttotalOrigin));
                headerNotaModel.setPpn(String.valueOf(txtPPN));
                headerNotaModel.setDiscount(String.valueOf(txtDiscount));
                headerNotaModel.setGrandTotal(String.valueOf(txtGrandTotal));
                headerNotaModel.setPayment(String.valueOf(txtPayment));
                headerNotaModel.setKembalian(String.valueOf(txtKembalian));
                realmHelperHeader.saveheader(headerNotaModel);
                //saveDetailNota
                for (int i = 0; i < mProdukList.size(); i++) {
                    if (mProdukList.get(i).getJumlahbarang() > 0) {
                        NotaModel detailNotaModel = new NotaModel();
                        detailNotaModel.setIddata((int)System.currentTimeMillis());
                        detailNotaModel.setKodeNota(txtNoNota);
                        detailNotaModel.setKodeBarang(mProdukList.get(i).getKodeBarang());
                        detailNotaModel.setNamaBarang(mProdukList.get(i).getNamaBarang());
                        detailNotaModel.setHargaBarang(mProdukList.get(i).getHargaBarang());
                        detailNotaModel.setKodeWarna(mProdukList.get(i).getKodeWarna());
                        detailNotaModel.setKodePackaging(mProdukList.get(i).getKodePackaging());
                        detailNotaModel.setJumlahbarang(mProdukList.get(i).getJumlahbarang());
                        detailNotaModel.setSubtotal(mProdukList.get(i).getSubtotal());
                        realmHelperdetail = new RealmHelperDetailNota(realm);
                        realmHelperdetail.savedetail(detailNotaModel);
                    }
                }
                SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                pDialog.setTitleText("Sukses !!");
                pDialog.setCancelable(false);
                pDialog.setConfirmText("Ya");
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                });
                pDialog.show();
            }else {
                SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.ERROR_TYPE);
                pDialog.setTitleText("Oops...");
                pDialog.setContentText("Uang Customer Kurang");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        }else {
            txtKembalian = txtPayment - txtGrandTotal;
            Log.d("RBA", "headerNota: " + "\n" + "\n"
                    + "noNota : " + txtNoNota + "\n"
                    + "noCustomer : " + txtNoCustomer + "\n"
                    + "transDate : " + txtTransDate + "\n"
                    + "totalOrigin : " + txttotalOrigin + "\n"
                    + "PPN : " + txtPPN + "\n"
                    + "Discount : " + txtDiscount + "\n"
                    + "grandTotal : " + txtGrandTotal + "\n"
                    + "Payment : " + txtPayment + "\n"
                    + "Kembalian : " + txtKembalian + "\n");
            //saveHeader
            HeaderNotaModel headerNotaModel = new HeaderNotaModel();
            headerNotaModel.setNoNota(String.valueOf(txtNoNota));
            headerNotaModel.setNoCustomer(String.valueOf(txtNoCustomer));
            headerNotaModel.setTransdate(String.valueOf(txtTransDate));
            headerNotaModel.setTotalOrigin(String.valueOf(txttotalOrigin));
            headerNotaModel.setPpn(String.valueOf(txtPPN));
            headerNotaModel.setDiscount(String.valueOf(txtDiscount));
            headerNotaModel.setGrandTotal(String.valueOf(txtGrandTotal));
            headerNotaModel.setPayment(String.valueOf(txtPayment));
            headerNotaModel.setKembalian(String.valueOf(txtKembalian));
            realmHelperHeader.saveheader(headerNotaModel);
            //saveDetailNota
            for (int i = 0; i < mProdukList.size(); i++) {
                if (mProdukList.get(i).getJumlahbarang() > 0) {
                    NotaModel detailNotaModel = new NotaModel();
                    detailNotaModel.setIddata((int)System.currentTimeMillis());
                    detailNotaModel.setKodeNota(txtNoNota);
                    detailNotaModel.setKodeBarang(mProdukList.get(i).getKodeBarang());
                    detailNotaModel.setNamaBarang(mProdukList.get(i).getNamaBarang());
                    detailNotaModel.setHargaBarang(mProdukList.get(i).getHargaBarang());
                    detailNotaModel.setKodeWarna(mProdukList.get(i).getKodeWarna());
                    detailNotaModel.setKodePackaging(mProdukList.get(i).getKodePackaging());
                    detailNotaModel.setJumlahbarang(mProdukList.get(i).getJumlahbarang());
                    detailNotaModel.setSubtotal(mProdukList.get(i).getSubtotal());
                    realmHelperdetail = new RealmHelperDetailNota(realm);
                    realmHelperdetail.savedetail(detailNotaModel);
                }
            }
            SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.SUCCESS_TYPE);
            pDialog.setTitleText("Kembalian");
            pDialog.setContentText("Kembalian Customer Sebesar \n" + String.valueOf(txtKembalian));
            pDialog.setCancelable(false);
            pDialog.setConfirmText("Ya");
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    });
            pDialog.show();
        }

    }

    @Override
    public void onContactSelected(NotaModel contact) {
        Intent in = new Intent(getApplicationContext(), AddQtyOrder.class);
        int qtyExtra = contact.getJumlahbarang();
        in.putExtra("jumlah", qtyExtra);
        in.putExtra("kode", contact.getKodeBarang());
        in.putExtra("namaBarang", contact.getKodeBarang() + " / " +contact.getNamaBarang());
        startActivityForResult(in, 23);
//        Toast.makeText(getApplicationContext(), "Selected: " + contact.getKodeBarang() + ", " + contact.getNamaBarang(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        if (!etSearch.getText().toString().matches("")){
            etSearch.setText("");
            produkAdapter.getFilter().filter(etSearch.getText().toString());
        }else{
            Log.d("kosong", "onBackPressed: ");
            SweetAlertDialog pDialog = new SweetAlertDialog(OrderCreatActivity.this, SweetAlertDialog.WARNING_TYPE);
            pDialog.setTitleText("Yakin untuk membatalkan nota?");
            pDialog.setCancelable(false);
            pDialog.setConfirmText("Yakin");
            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                    finish();
                }
            });
            pDialog.setCancelButton("Batal", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sDialog) {
                    sDialog.dismissWithAnimation();
                }
            });
            pDialog.show();
        }
    }
}
