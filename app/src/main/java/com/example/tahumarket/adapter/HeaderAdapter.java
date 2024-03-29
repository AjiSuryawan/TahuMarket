package com.example.tahumarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tahumarket.R;
import com.example.tahumarket.activity.OrderViewActivity;
import com.example.tahumarket.model.HeaderNotaModel;
import com.example.tahumarket.model.ProdukModel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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
        Locale.setDefault(new Locale("id", "ID"));
        holder.tvNoNota.setText(mModel.getNoNota());
        holder.tvTransDate.setText(mModel.getTransdate());
        holder.tvNamaPemesan.setText(mModel.getNoCustomer());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        kursIndonesia.setMaximumFractionDigits(0);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        Log.d("duit rupiah", "onCreate: "+String.valueOf(kursIndonesia.format(Integer.parseInt(mModel.getGrandTotal()))));
        holder.tvGrandTotal.setText(String.valueOf(kursIndonesia.format(Integer.parseInt(mModel.getGrandTotal()))));
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mCtx, OrderViewActivity.class);
                    i.putExtra("noNota", produkList.get(getAdapterPosition()).getNoNota());
                    i.putExtra("noCustomer", produkList.get(getAdapterPosition()).getNoCustomer());
                    i.putExtra("transDate", produkList.get(getAdapterPosition()).getTransdate());
                    i.putExtra("totalOrigin", produkList.get(getAdapterPosition()).getTotalOrigin());
                    i.putExtra("ppn", produkList.get(getAdapterPosition()).getPpn());
                    i.putExtra("discount", produkList.get(getAdapterPosition()).getDiscount());
                    i.putExtra("grandTotal", produkList.get(getAdapterPosition()).getGrandTotal());
                    i.putExtra("payment", produkList.get(getAdapterPosition()).getPayment());
                    i.putExtra("kembalian", produkList.get(getAdapterPosition()).getKembalian());
                    mCtx.startActivity(i);
                }
            });
        }
    }
}
