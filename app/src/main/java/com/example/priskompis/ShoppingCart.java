package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.priskompis.Adapter.ProductAdapter;
import com.example.priskompis.Model.Order;
import com.example.priskompis.Model.ProductModel;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Set;

public class ShoppingCart extends AppCompatActivity {

    //a list to store all the products
    HashMap<String, ProductModel> productList;
    HashMap<String, Float> productQuantity;
    private DecimalFormat df = new DecimalFormat("#.##");

    //the recyclerview
    RecyclerView recyclerView;

    Order myOrder;
    ProductAdapter adapter;
    ProductAdapter.ProductViewHolder productHolder;
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon36);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        //Type object = (Type) bundle.getSerializable("KEY");
        myOrder = (Order) bundle.getSerializable("order");
        HashMap<String,Float> quantities=(HashMap<String, Float>) bundle.getSerializable("quantities");

        recyclerView = (RecyclerView) findViewById(R.id.cartListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = myOrder.getProducts();
        productQuantity = myOrder.getProductQuantity();
        Set<String> keys = productQuantity.keySet();
        System.out.println("keys: " + keys.size());
        //print all the keys
        for (String key : keys) {
            System.out.println(key);
            System.out.println(productQuantity.get(key));
        }
        //creating recyclerview adapter
        adapter = new ProductAdapter(this, productList, productQuantity);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
        totalPrice = findViewById(R.id.txt_totalprice);
        totalPrice.setText(String.valueOf(df.format(myOrder.getTotalPrice()) + " SEK"));
    }

    public void goToPayment(View view) {
        Intent intent = new Intent(this, Payment.class);
        TextView amt = findViewById(R.id.txt_totalprice);
        float totalPrice = Float.parseFloat(amt.getText().toString().split(" ")[0])*100;
        intent.putExtra("totalPrice", df.format(totalPrice));
        this.startActivity(intent);
    }

    public void removeProduct (ProductModel product) {
        myOrder.removeProduct(product);
        System.out.println("In Shopping Cart");
        myOrder.printProducts();
        totalPrice.setText(String.valueOf(df.format(myOrder.getTotalPrice())) + " SEK");
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", myOrder);
        Intent intent = new Intent(getApplicationContext(), InShop.class);
        intent.putExtras(bundle);
        setResult(CommonStatusCodes.SUCCESS, intent);
        finish();
    }
}
