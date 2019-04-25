package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priskompis.Model.ProductModel;
import com.example.priskompis.Operations.Database;

public class InShop extends AppCompatActivity {

    Intent intent =new Intent();
    private int budget;
    private Database dat=new Database();
    private ProductModel product;
    private TextView displayName;
    private TextView displayQuantity;
    private TextView displayPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.activity_in_shop);
        displayName=findViewById(R.id.productNameDisplay);
    displayQuantity=findViewById(R.id.productQuantityDisplay);
    displayPrice=findViewById(R.id.productPriceDisplay);
        budget=intent.getIntExtra("budget",0);




    }

public void scanItem(View view)
    {
    product=dat.getItemByID("0000042");
    displayName.setText(product.getName());
    displayQuantity.setText(product.getQuantity());
    displayPrice.setText(String.valueOf(product.getPriceICA()));

    }
}
