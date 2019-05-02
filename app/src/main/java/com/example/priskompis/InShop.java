package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priskompis.Model.Order;
import com.example.priskompis.Model.ProductModel;
import com.example.priskompis.Operations.Database;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.HashMap;

public class InShop extends AppCompatActivity
{

    Intent intent = new Intent();
    private int budget;
    private Database dat = new Database();
    public ProductModel product =new ProductModel();
    private TextView displayName;
    private TextView displayQuantity;
    private TextView displayPrice;
    private EditText requiredQuantity;
    private TextView resultView;
    private float reqQuantity = 1;
    private float result;
    private float orderTotal;
    private TextView quantityLabel;
    private Button addToCart;
    private HashMap<String, Float> quantityList=new HashMap<>();
    private Order order = new Order();

    private CompoundButton autoFocus;
    //private CompoundButton useFlash;
    //private TextView statusMessage;
    private TextView barcodeValue;
    private TextView totalBudget;
    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";
    private Barcode barcode = null;
    private ProgressBar fraction;
    private ProgressBar totalProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        setContentView(R.layout.activity_in_shop);
        displayName = findViewById(R.id.productNameDisplay);
        displayQuantity = findViewById(R.id.productQuantityDisplay);
        displayPrice = findViewById(R.id.productPriceDisplay);
        requiredQuantity = findViewById(R.id.editQuantity);
        resultView = findViewById(R.id.textViewResult);
        quantityLabel = findViewById(R.id.quantityLabel);
        addToCart = findViewById(R.id.buttonAddCart);

        result=0;
        totalBudget=findViewById(R.id.total_budget);
        fraction=findViewById(R.id.stats_progressbar);
        totalProgress = findViewById(R.id.stats_totalprogressbar);
        //product=new ProductModel();

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
                if (TextUtils.isEmpty(requiredQuantity.getText().toString()) || requiredQuantity.getText().toString().endsWith("."))
                {
                    resultView.setText("0");
                }
                else
                {
                    reqQuantity = Float.parseFloat(requiredQuantity.getText().toString());
                    float price = product.getPriceICA();
                    result=(float)(Math.round(price*reqQuantity*10.0)/10.0);
                    resultView.setText(String.valueOf(result));
                    totalBudget.setText((float)(Math.round(orderTotal*10.0)/10.0) +result+ " SEK/ " + budget+" SEK");
                    totalBudget.startAnimation(getBlinkAnimation());
                    //totalProgress.setVisibility(View.INVISIBLE);

                    //updateChart();
                    fraction.setVisibility(View.VISIBLE);
                    fraction.setProgress((int)((double)(orderTotal+result)/(double)(budget)));

                }
            }
        });
        budget = intent.getIntExtra("budget", 0);

        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        //useFlash = (CompoundButton) findViewById(R.id.use_flash);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    //statusMessage.setText(R.string.barcode_success);
                    barcodeValue.setText(barcode.displayValue);
                    updateProduct(barcode.rawValue.substring(0, barcode.rawValue.length() - 1));
                    //updateProduct("0000042");
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                } else {
                    //statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                //statusMessage.setText(String.format(getString(R.string.barcode_error),
                // CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void updateChart()
    {
        //orderTotal = order.getTotalPrice();

        // Update the text in a center of the chart:
        TextView totalBudget = findViewById(R.id.total_budget);
        totalBudget.setText(orderTotal + " / " + budget);

        fraction.setVisibility(View.VISIBLE);
        fraction.startAnimation(getBlinkAnimation());
        displayPrice.setVisibility(View.VISIBLE);
        displayQuantity.setVisibility(View.VISIBLE);
        displayName.setTextSize(18);


// Calculate the slice size and update the pie chart:


        double d = (double) orderTotal / (double) budget;
        int progress = (int) (d * 100);
        totalProgress.setProgress(progress);
        fraction.setProgress((int)((double)(orderTotal+result)/(double)(budget)));

    }

    public Animation getBlinkAnimation(){
        Animation animation = new AlphaAnimation(1, 0);         // Change alpha from fully visible to invisible
        animation.setDuration(300);                             // duration - half a second
        animation.setInterpolator(new LinearInterpolator());    // do not alter animation rate
        animation.setRepeatCount(100);                            // Repeat animation infinitely
        animation.setRepeatMode(Animation.REVERSE);             // Reverse animation at the end so the button will fade back in

        return animation;
    }

public Animation stopBlinkAnimation(){
Animation animation = new AlphaAnimation(1,1);         // Change alpha from fully visible to invisible
animation.setDuration(300);                             // duration - half a second
animation.setInterpolator(new LinearInterpolator());    // do not alter animation rate
animation.setRepeatCount(100);                            // Repeat animation infinitely
animation.setRepeatMode(Animation.REVERSE);             // Reverse animation at the end so the button will fade back in

return animation;
}

    public void updateProduct(String barCode)
    {
        product = dat.getItemByID(barCode); //After we get the id from scanning the barcode.

        if(product != null) {
            System.out.println("Setting all the display properties");
            displayName.setText(product.getName());
            displayQuantity.setText(String.valueOf(product.getQuantity()));
            displayPrice.setText(String.valueOf(product.getPriceICA()) + " SEK");
            //resultView.setText(String.valueOf(product.getPriceICA()));
            requiredQuantity.setText("1");
            reqQuantity = Float.parseFloat(requiredQuantity.getText().toString());
            result=reqQuantity*product.getPriceICA();
            resultView.setText(String.valueOf(result));
            addToCart.setVisibility(View.VISIBLE);
            resultView.setVisibility(View.VISIBLE);
            quantityLabel.setVisibility(View.VISIBLE);
            requiredQuantity.setVisibility(View.VISIBLE);
            totalProgress.setVisibility(View.VISIBLE);
            System.out.println("Finished Setting all the display properties");
        }
    else
            {
            Log.i("Product:","Not found");
            }

    }
    public void scanItem(View view){
        // launch barcode activity.

        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        // intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
        updateProduct("0000042");
        //intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());
        //startActivityForResult(intent, RC_BARCODE_CAPTURE);
    }


    public void AddToCart(View view)
    {
        if (TextUtils.isEmpty(requiredQuantity.getText()))
        {
            requiredQuantity.setError("Quantity can not be empty");
            return;
        }

        reqQuantity = Float.parseFloat(requiredQuantity.getText().toString());
        order.addProduct(product,reqQuantity);
        orderTotal+=Float.parseFloat(resultView.getText().toString());
        quantityList.put(product.getID(),reqQuantity);
        updateChart();

        resetView();
    }

    public void resetView(){
        /*displayName.setText("");
        displayQuantity.setText("");
        displayPrice.setText("");
        resultView.setText("");
        requiredQuantity.setText("");*/
        quantityLabel.setVisibility(View.INVISIBLE);
        requiredQuantity.setVisibility(View.INVISIBLE);
        resultView.setVisibility(View.INVISIBLE);
        addToCart.setVisibility(View.INVISIBLE);
        barcodeValue.setVisibility(View.INVISIBLE);
        displayPrice.setVisibility(View.INVISIBLE);
        displayQuantity.setVisibility(View.INVISIBLE);
        displayName.setTextSize(25);
        displayName.setText("Scan Next Item or Click Checkout to complete Shopping");
        fraction.setVisibility(View.INVISIBLE);
        totalProgress.setVisibility(View.VISIBLE);
        totalBudget.startAnimation(stopBlinkAnimation());


    }

    public void clearText(View view)
    {
        requiredQuantity.getText().clear();
    }

    public void checkOut(View view)
    {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(), Checkout.class);
        bundle.putSerializable("order", order);
        bundle.putSerializable("quantities",quantityList);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}