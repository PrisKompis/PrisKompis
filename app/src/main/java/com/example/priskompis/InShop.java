package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priskompis.Model.Order;
import com.example.priskompis.Model.ProductModel;
import com.example.priskompis.Operations.Database;

import java.util.HashMap;

public class InShop extends AppCompatActivity {

    Intent intent =new Intent();
    private int budget;
    private Database dat=new Database();
    private ProductModel product;
    private TextView displayName;
    private TextView displayQuantity;
    private TextView displayPrice;
    private EditText requiredQuantity;
    private TextView resultView;
    private float reqQuantity=1;
    private float result;
    private TextView quantityLabel;
    private Button addToCart;
    private HashMap<String, Order> orderList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.activity_in_shop);
        displayName=findViewById(R.id.productNameDisplay);
    displayQuantity=findViewById(R.id.productQuantityDisplay);
    displayPrice=findViewById(R.id.productPriceDisplay);
    requiredQuantity=findViewById(R.id.editQuantity);
    resultView=findViewById(R.id.textViewResult);
    quantityLabel=findViewById(R.id.quantityLabel);
    addToCart=findViewById(R.id.buttonAddCart);
    orderList=new HashMap<>();

    requiredQuantity.addTextChangedListener(new TextWatcher()
        {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

        @Override
        public void afterTextChanged(Editable s)
            {
            if (TextUtils.isEmpty(requiredQuantity.getText().toString())||requiredQuantity.getText().toString().endsWith("."))
                {
                resultView.setText("0");
                }
            else
                {
                reqQuantity = Float.parseFloat(requiredQuantity.getText().toString());
                float price = product.getPriceICA();
                result = price * reqQuantity;
                result=(float)Math.ceil(result);
                resultView.setText(String.valueOf(result));
                }
            }
        });
        budget=intent.getIntExtra("budget",0);




    }

public void scanItem(View view)
    {
    product=dat.getItemByID("0000042"); //After we get the id from scanning the barcode.
    displayName.setText(product.getName());
    displayQuantity.setText(product.getQuantity());
    displayPrice.setText(String.valueOf(product.getPriceICA())+" SEK");
    resultView.setText(String.valueOf(product.getPriceICA()));
    requiredQuantity.setText("1");
    addToCart.setVisibility(View.VISIBLE);
    resultView.setVisibility(View.VISIBLE);
    quantityLabel.setVisibility(View.VISIBLE);
    requiredQuantity.setVisibility(View.VISIBLE);


    }


public void AddToCart(View view)
    {
    if(TextUtils.isEmpty(requiredQuantity.getText()))
        {
        requiredQuantity.setError("Quantity can not be empty");

        return;
        }
    reqQuantity=Float.parseFloat(requiredQuantity.getText().toString());
    float price=product.getPriceICA();
    result=price*reqQuantity;
    result=(float)Math.ceil(result);
    Order order =new Order();
    order.setProduct(product);
    order.setQuantity(reqQuantity);
    order.setSinglePrice(product.getPriceICA());
    order.setTotalPrice(result);
    orderList.put(product.getID(),order);


    }

public void clearText(View view)
    {
        requiredQuantity.getText().clear();
    }

public void checkOut(View view)
    {
    Bundle bundle = new Bundle();
    Intent intent=new Intent(getApplicationContext(),Checkout.class);
    bundle.putSerializable("orderList",orderList);
    intent.putExtras(bundle);
    startActivity(intent);

    }
}
