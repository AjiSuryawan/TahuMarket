package com.example.tahumarket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tahumarket.R;
import com.example.tahumarket.model.NotaModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailNotaAdapter extends RecyclerView.Adapter<DetailNotaAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<NotaModel> produkList;
    private List<NotaModel> contactListFiltered;
//    public Callback callback;
    private ContactsAdapterListener listener;
    DecimalFormat kursIndonesia;

    public interface Callback {
        void onClick(int position);
        void test();
    }

    public DetailNotaAdapter(Context mCtx, List<NotaModel> productList) {
        this.mCtx = mCtx;
        this.produkList = productList;
        this.contactListFiltered = productList;
//        this.callback = callback;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_row_order, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final NotaModel mModel = contactListFiltered.get(position);
        holder.tvKodeProduk.setText(mModel.getKodeBarang());
        holder.tvNamaProduk.setText(mModel.getNamaBarang());

        Locale.setDefault(new Locale("id", "ID"));

        kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        kursIndonesia.setMaximumFractionDigits(0);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);
//        Log.d("duit rupiah", "onCreate: "+kursIndonesia.format(mModel.getHargaBarang()));
        holder.tvHargaProduk.setText(String.valueOf(kursIndonesia.format(mModel.getHargaBarang())));

        holder.tvPackagingProduk.setText(mModel.getKodePackaging());
        if (mModel.getJumlahbarang() == 0){
            holder.tvQty.setVisibility(View.GONE);
        }else{
            holder.tvQty.setVisibility(View.VISIBLE);
//            holder.tvQty.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging() + "\n" + mModel.getSubtotal());

            holder.tvQty.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging() + "\n" + String.valueOf(kursIndonesia.format(mModel.getSubtotal())));
        }


        if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_green);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BROWN") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_brown);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("PURPLE") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_purple);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("RED") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_red);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("MAGENTA") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_magenta);
        }
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeProduk, tvNamaProduk, tvHargaProduk, tvPackagingProduk, tvQty;
        LinearLayout divProduk;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvKodeProduk = itemView.findViewById(R.id.tvKodeProduk);
            tvNamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tvHargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tvPackagingProduk = itemView.findViewById(R.id.tvPackagingProduk);
            tvQty = itemView.findViewById(R.id.tvQty);
            divProduk = itemView.findViewById(R.id.divProduk);
        }
    }
    public interface ContactsAdapterListener {
        void onContactSelected(NotaModel contact);
    }
}
