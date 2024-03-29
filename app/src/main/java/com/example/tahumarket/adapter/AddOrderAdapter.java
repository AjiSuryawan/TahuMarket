package com.example.tahumarket.adapter;

import android.content.Context;
import android.graphics.Color;
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

public class AddOrderAdapter extends RecyclerView.Adapter<AddOrderAdapter.ProductViewHolder> implements Filterable {
    private Context mCtx;
    private List<NotaModel> produkList;
    private List<NotaModel> contactListFiltered;
//    public Callback callback;
    private ContactsAdapterListener listener;

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
                        if (row.getNamaBarang().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getKodeBarang().toLowerCase().contains(charString.toLowerCase())) {
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

    public interface Callback {
        void onClick(int position);
        void test();
    }

    public AddOrderAdapter(Context mCtx, List<NotaModel> productList, ContactsAdapterListener listener) {
        this.mCtx = mCtx;
        this.produkList = productList;
        this.contactListFiltered = productList;
//        this.callback = callback;
        this.listener = listener;
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
        Locale.setDefault(new Locale("id", "ID"));
        holder.tvKodeProduk.setText(mModel.getKodeBarang());
        holder.tvNamaProduk.setText(mModel.getNamaBarang());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        kursIndonesia.setMaximumFractionDigits(0);
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        Log.d("duit rupiah", "onCreate: "+kursIndonesia.format(mModel.getHargaBarang()));

        holder.tvHargaProduk.setText(String.valueOf(kursIndonesia.format(mModel.getHargaBarang())));
//        holder.tvHargaProduk.setText(String.valueOf(mModel.getHargaBarang()));
        holder.tvPackagingProduk.setText(mModel.getKodePackaging());
        if (mModel.getJumlahbarang() == 0){
            holder.tvQty.setVisibility(View.GONE);
        }else{
            holder.tvQty.setVisibility(View.VISIBLE);
//            holder.tvQty.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging() + "\n" + mModel.getSubtotal());
            holder.tvQty.setText(mModel.getJumlahbarang() + " - " + mModel.getKodePackaging() + "\n" + String.valueOf(kursIndonesia.format(mModel.getSubtotal())));

        }


        if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvSlash1.setTextColor(Color.parseColor("#ab5b05"));
            holder.tvSlash2.setTextColor(Color.parseColor("#ab5b05"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("YELLOW") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_yellow_dark);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#555721"));
            holder.tvSlash1.setTextColor(Color.parseColor("#555721"));
            holder.tvSlash2.setTextColor(Color.parseColor("#555721"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BLUE") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_blue_dark);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_green);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#606141"));
            holder.tvSlash1.setTextColor(Color.parseColor("#606141"));
            holder.tvSlash2.setTextColor(Color.parseColor("#606141"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("GREEN") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_green_dark);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BROWN") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_brown_light);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash1.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash2.setTextColor(Color.parseColor("#e8dacb"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("BROWN") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_brown);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("PURPLE") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_purple_light);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash1.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash2.setTextColor(Color.parseColor("#e8dacb"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("PURPLE") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_purple);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("RED") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_red_light);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash1.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash2.setTextColor(Color.parseColor("#e8dacb"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("RED") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_red);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("MAGENTA") && mModel.getJumlahbarang() == 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_magenta_light);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash1.setTextColor(Color.parseColor("#e8dacb"));
            holder.tvSlash2.setTextColor(Color.parseColor("#e8dacb"));
        }else if (mModel.getKodeWarna().equalsIgnoreCase("MAGENTA") && mModel.getJumlahbarang() != 0){
            holder.divProduk.setBackgroundResource(R.drawable.card_magenta);
            holder.tvKodeProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvNamaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvHargaProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvPackagingProduk.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash1.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.tvSlash2.setTextColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvKodeProduk, tvNamaProduk, tvHargaProduk, tvPackagingProduk, tvQty, tvSlash1, tvSlash2;
        LinearLayout divProduk;

        public ProductViewHolder(View itemView) {
            super(itemView);
            tvKodeProduk = itemView.findViewById(R.id.tvKodeProduk);
            tvNamaProduk = itemView.findViewById(R.id.tvNamaProduk);
            tvHargaProduk = itemView.findViewById(R.id.tvHargaProduk);
            tvPackagingProduk = itemView.findViewById(R.id.tvPackagingProduk);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvSlash1 = itemView.findViewById(R.id.tvSlash1);
            tvSlash2 = itemView.findViewById(R.id.tvSlash2);
            divProduk = itemView.findViewById(R.id.divProduk);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    callback.onClick(getAdapterPosition());
                    listener.onContactSelected(contactListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
    public interface ContactsAdapterListener {
        void onContactSelected(NotaModel contact);
    }
}
