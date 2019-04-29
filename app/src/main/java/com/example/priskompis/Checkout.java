package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.priskompis.Adapter.ProductAdapter;
import com.example.priskompis.Model.Order;
import com.example.priskompis.Model.ProductModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Checkout extends AppCompatActivity {

    //a list to store all the products
    List<ProductModel> productList;

    //the recyclerview
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    Intent intent = this.getIntent();
    Bundle bundle = intent.getExtras();
//Type object = (Type) bundle.getSerializable("KEY");
    HashMap<String, Order> myData = (HashMap<String, Order>) bundle.getSerializable("orderList");

        recyclerView = (RecyclerView) findViewById(R.id.cartListView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();
        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(this, productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

}
