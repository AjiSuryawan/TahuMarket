package com.example.tahumarket.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.tahumarket.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PaymentDialog extends AppCompatDialogFragment {
    private EditText etPayment;
    private PaymentDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_payment_dialog, null);

        builder.setView(view)
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String payment = etPayment.getText().toString();
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
        etPayment = view.findViewById(R.id.etPayment);
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
