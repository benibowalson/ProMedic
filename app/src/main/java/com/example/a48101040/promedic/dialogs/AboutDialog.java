package com.example.a48101040.promedic.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;

import com.example.a48101040.promedic.R;

/**
 * Created by 48101040 on 2/8/2018.
 */

public class AboutDialog extends android.app.DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        myBuilder.setView(inflater.inflate(R.layout.about, null))
        .setPositiveButton("Ok", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return myBuilder.create();
    }
}
