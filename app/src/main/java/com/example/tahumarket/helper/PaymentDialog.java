package com.example.tahumarket.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.example.tahumarket.R;
import com.example.tahumarket.activity.NumberTextWatcher;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentDialog extends AppCompatDialogFragment {
    private EditText etPayment;
    private PaymentDialogListener listener;
    private TextView tvTotal, tvPPN, tvDiskon, tvGrandTotal;
    private LinearLayout divSimpan;

    public static PaymentDialog newInstance(String tvTotal, String tvPPN, String tvDiskon, String tvGrandTotal) {
        PaymentDialog frag = new PaymentDialog();
        Bundle args = new Bundle();
        args.putString("tvTotal", tvTotal);
        args.putString("tvPPN", tvPPN);
        args.putString("tvDiskon", tvDiskon);
        args.putString("tvGrandTotal", tvGrandTotal);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_payment_dialog, null);

        builder.setView(view);
//                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String payment = etPayment.getText().toString();
//                        if (payment.equalsIgnoreCase("")){
//                            int paymentKu = 0;
//                            if (paymentKu <= 0){
//                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                                        .setTitleText("Oops...")
//                                        .setContentText("Masukkan Nominal Uang Customer")
//                                        .show();
//                            }
//                        }else{
//                            listener.applyText(Integer.parseInt(payment));
//                        }
//                    }
//                });
        etPayment = view.findViewById(R.id.etPayment);
        etPayment.addTextChangedListener(new NumberTextWatcher(etPayment));
        tvTotal = view.findViewById(R.id.tvTotal);
        tvPPN = view.findViewById(R.id.tvPPN);
        tvDiskon = view.findViewById(R.id.tvDiskon);
        tvGrandTotal = view.findViewById(R.id.tvGrandTotal);
        divSimpan = view.findViewById(R.id.divSimpan);

        tvTotal.setText(getArguments().getString("tvTotal"));
        tvPPN.setText(getArguments().getString("tvPPN"));
        tvDiskon.setText(getArguments().getString("tvDiskon"));
        tvGrandTotal.setText(getArguments().getString("tvGrandTotal"));

        divSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("uang cst", "onClick: "+etPayment.getText().toString().contains("."));
                String payment="";
                if(etPayment.getText().toString().contains(",")){
                    payment = etPayment.getText().toString().replace(",", "");
                    Log.d("masuk if duit", "onClick: "+payment);
                }else if(etPayment.getText().toString().contains(".")){
                    payment = etPayment.getText().toString().replace(".", "");
                    Log.d("masuk else duit", "onClick: "+payment);
                }



                Log.d("duit cst", "onClick: "+payment);
                        if (payment.equalsIgnoreCase("")){
                            int paymentKu = 0;
                            if (paymentKu <= 0){
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Masukkan Nominal Uang Customer")
                                        .show();
                            }
                        }else{
                            listener.applyText(Integer.parseInt(payment));
                        }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PaymentDialogListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString() + "must implement PaymentDialogListener");
        }
    }

    public interface PaymentDialogListener {
        void applyText(int payment);
    }
}
