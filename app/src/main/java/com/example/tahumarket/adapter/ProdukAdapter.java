package com.example.tahumarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahumarket.R;
import com.example.tahumarket.model.ProdukModel;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<ProdukModel> produkList;

    public ProdukAdapter(Context mCtx, List<ProdukModel> productList) {
        this.mCtx = mCtx;
        this.produkList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_row_produk, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final ProdukModel mModel = produkList.get(position);
        holder.tvKodeProduk.setText(mModel.getKodeBarang());
        holder.tvNamaProduk.setText(mModel.getNamaBarang());
        holder.tvHargaProduk.setText(String.valueOf(mModel.getHargaBarang()));
        holder.tvPackagingProduk.setText(mModel.getKodePackaging());
        if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW")){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvSlash1.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvSlash2.setTextColor(Color.parseColor("#ab5b05"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE")){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvSlash1.setTextColor(Color.parseColor("#555721"));
            holder.tvSlash2.setTextColor(Color.parseColor("#555721"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN")){
            holder.divProduk.setBackgroundResource(R.drawable.card_green);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvSlash1.setTextColor(Color.parseColor("#606141"));
            holder.tvSlash2.setTextColor(Color.parseColor("#606141"));
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeProduk, tvNamaProduk, tvHargaProduk, tvPackagingProduk, tvSlash1, tvSlash2;
        LinearLayout divProduk;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvKodeProduk = itemView.findViewById(R.id.tvKodeProduk);
            tvNamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tvHargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tvPackagingProduk = itemView.findViewById(R.id.tvPackagingProduk);
            divProduk = itemView.findViewById(R.id.divProduk);
            tvSlash1 = itemView.findViewById(R.id.tvSlash1);
            tvSlash2 = itemView.findViewById(R.id.tvSlash2);
        }
    }
}
