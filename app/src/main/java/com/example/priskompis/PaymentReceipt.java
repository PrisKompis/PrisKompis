package com.example.priskompis;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.priskompis.Operations.EmailReceipt;

public class PaymentReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_receipt);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        String receiptURL = getIntent().getStringExtra("RECEIPT_URL");
        WebView myWebView = (WebView) findViewById(R.id.webview_receipt);
        myWebView.loadUrl(receiptURL);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String emailto=preferences.getString("userEmail", null);

        new EmailReceipt(receiptURL, emailto).execute();
    }
}
