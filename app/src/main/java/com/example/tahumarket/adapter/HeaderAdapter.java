package com.example.tahumarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tahumarket.R;
import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.ProdukModel;

import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<HeaderNotaModel> produkList;

    public HeaderAdapter(Context mCtx, List<HeaderNotaModel> productList) {
        this.mCtx = mCtx;
        this.produkList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_row_header_nota, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final HeaderNotaModel mModel = produkList.get(position);
        holder.tvNoNota.setText(mModel.getNoNota());
        holder.tvTransDate.setText(mModel.getTransdate());
        holder.tvNamaPemesan.setText(mModel.getNoCustomer());
        holder.tvGrandTotal.setText(mModel.getGrandTotal());
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoNota, tvTransDate, tvNamaPemesan, tvGrandTotal;
        LinearLayout divProduk;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvNoNota = itemView.findViewById(R.id.tvNoNota);
            tvTransDate = itemView.findViewById(R.id.tvTransDate);
            tvNamaPemesan = itemView.findViewById(R.id.tvNamaPemesan);
            tvGrandTotal = itemView.findViewById(R.id.tvGrandTotal);
            divProduk = itemView.findViewById(R.id.divProduk);
        }
    }
}
