package com.example.tahumarket.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tahumarket.R;
import com.example.tahumarket.model.NotaModel;

import java.util.List;

public class AddOrderAdapter extends RecyclerView.Adapter<AddOrderAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<NotaModel> produkList;
    public Callback callback;

    interface Callback {
        void onClick(int position);
        void test();
    }

    public AddOrderAdapter(Context mCtx, List<NotaModel> productList, Callback callback) {
        this.mCtx = mCtx;
        this.produkList = productList;
        this.callback = callback;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_row_order, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final NotaModel mModel = produkList.get(position);
        holder.tvKodeProduk.setText(mModel.getKodeBarang());
        holder.tvNamaProduk.setText(mModel.getNamaBarang());
        holder.tvHargaProduk.setText(String.valueOf(mModel.getHargaBarang()));
        holder.tvPackagingProduk.setText(mModel.getKodePackaging());
        if (mModel.getJumlahbarang() == null){
            holder.tvQty.setVisibility(View.GONE);
        }else{
            holder.tvQty.setVisibility(View.VISIBLE);
            holder.tvQty.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging());
        }

//        if (mModel.getJumlahbarang() == null){
//
//        }else{
//            holder.tvPackagingProduk.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging());
//        }
        if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW") && mModel.getJumlahbarang() == null){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW") && mModel.getJumlahbarang() != null){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow_dark);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE") && mModel.getJumlahbarang() == null){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE") && mModel.getJumlahbarang() != null){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue_dark);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN") && mModel.getJumlahbarang() == null){
            holder.divProduk.setBackgroundResource(R.drawable.card_green);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN") && mModel.getJumlahbarang() != null){
            holder.divProduk.setBackgroundResource(R.drawable.card_green_dark);
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getAdapterPosition());
                }
            });
        }
    }
}
