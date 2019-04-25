package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class InShop extends AppCompatActivity {

    Intent intent =new Intent();
    private int budget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.activity_in_shop);

        budget=intent.getIntExtra("budget",0);



    }
}
