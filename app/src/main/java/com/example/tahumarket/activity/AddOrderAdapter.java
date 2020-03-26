package com.example.tahumarket.activity;

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

import java.util.ArrayList;
import java.util.List;

public class AddOrderAdapter extends RecyclerView.Adapter<AddOrderAdapter.ProductViewHolder> implements Filterable {
    private Context mCtx;
    private List<NotaModel> produkList;
    private List<NotaModel> contactListFiltered;
    public Callback callback;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d("carian", "performFiltering: "+charSequence);
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = produkList;
                } else {
                    List<NotaModel> filteredList = new ArrayList<>();
                    for (NotaModel row : produkList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNamaBarang().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                    Log.d("masuk sini sini", "performFiltering: "+contactListFiltered.size());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<NotaModel>) filterResults.values;
                Log.d("masuk sini", "publishResults: "+contactListFiltered.size());
                notifyDataSetChanged();
            }
        };
    }

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
        holder.tvPackagingProduk.setText(mModel.getKodePackaging()+" - "+mModel.getJumlahbarang());
        if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW")){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE")){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue);
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN")){
            holder.divProduk.setBackgroundResource(R.drawable.card_green);
        }
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeProduk, tvNamaProduk, tvHargaProduk, tvPackagingProduk;
        LinearLayout divProduk;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvKodeProduk = itemView.findViewById(R.id.tvKodeProduk);
            tvNamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tvHargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tvPackagingProduk = itemView.findViewById(R.id.tvPackagingProduk);
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
