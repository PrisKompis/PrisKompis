package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.priskompis.Model.Order;

import java.util.HashMap;

public class Checkout extends AppCompatActivity {

private RecyclerView cartList;
private RecyclerView.Adapter cartListAdapter;
private RecyclerView.LayoutManager cartListlayoutManager;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    Intent intent = this.getIntent();
    Bundle bundle = intent.getExtras();
//Type object = (Type) bundle.getSerializable("KEY");
    HashMap<String, Order> myData = (HashMap<String, Order>) bundle.getSerializable("orderList");
    cartList=findViewById(R.id.cartListView);
    cartList.setHasFixedSize(true);
    cartListlayoutManager = new LinearLayoutManager(this);
    cartList.setLayoutManager(cartListlayoutManager);
    }
}
