package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class InShop extends AppCompatActivity {

    Intent intent =new Intent();
    private int userSetBudget;
    private int currentItemPrice = 0;
    //private int userSetBudget = 0;
    private int totalPrice =0;
    AlertDialog.Builder builder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.activity_in_shop);

        userSetBudget=intent.getIntExtra("budget",0);
        updateChart();

    }

    private void updateChart(){
        // Update the text in a center of the chart:
        TextView totalBudget = findViewById(R.id.total_budget);
        totalBudget.setText(String.valueOf(totalPrice) + " / " + userSetBudget);

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) totalPrice / (double) userSetBudget;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
    }



}
