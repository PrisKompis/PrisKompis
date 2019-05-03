package com.example.priskompis;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Popup {
    AlertDialog alertDialog;

    // create popup
    public Popup(View view, Context context) {
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.alert_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(popupView);
        alertDialog = builder.create();
    }

    public void show(){
        alertDialog.show();
    }

    public void dismiss(){
        alertDialog.dismiss();
    }
}
