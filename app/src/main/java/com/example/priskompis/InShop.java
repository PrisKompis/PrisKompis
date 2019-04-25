package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class InShop extends AppCompatActivity {

    Intent intent =new Intent();
    private int userBudget;
    private int currentItemPrice = 0;
    private int budget = 0;
    private int totalPrice =0;
    AlertDialog.Builder builder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.activity_in_shop);

        userBudget=intent.getIntExtra("budget",0);



    }
}
