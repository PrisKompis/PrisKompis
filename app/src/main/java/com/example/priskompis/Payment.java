package com.example.priskompis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.TintableBackgroundView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.priskompis.Operations.StripeCharge;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Payment extends AppCompatActivity {

    String receipturl;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

    }

    public void payAmount(View view) {

        final int amount = Integer.parseInt(getIntent().getStringExtra("totalPrice"));

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);
        Card card = mCardInputWidget.getCard();

        if (card != null) {

            card.setCurrency("sek");
            card.setName("Saranya");
            card.setAddressZip("12143");

            System.out.println("Creating Token..");
            System.out.println(card.getNumber());

            final Context mContext = this;
            Stripe stripe = new Stripe(mContext, "pk_test_WwGsbygMxfvO3kv7ZGS1IL0n006cDpCCd4");
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            // Send token to your server
                            Toast.makeText(getApplicationContext(), "Payment successful", Toast.LENGTH_LONG).show();
                            String receiptURL = null;
                            try {
                                receiptURL = new StripeCharge(token.getId(), amount).execute().get(30, TimeUnit.SECONDS);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (TimeoutException e) {
                                e.printStackTrace();
                            }

                            if (receiptURL != null) {
                                goToReceiptActivity(receiptURL);
                            }
                            else {
                                String errorMessage = "Something went wrong when processing your payment. Please try again.";
                                System.out.println(errorMessage);
                                Toast.makeText(Payment.this,
                                        errorMessage,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            System.out.println(error.getLocalizedMessage());
                            Toast.makeText(Payment.this,
                                    error.getLocalizedMessage(),
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        } else
        {
            Toast.makeText(Payment.this,
                    "Enter card details to proceed with payment",
                    Toast.LENGTH_LONG
            ).show();
            Log.d("Invalid","Invalid");
            return;
        }
    }

    public void goToReceiptActivity(String receiptURL) {
        Intent intent = new Intent(this, PaymentReceipt.class);
        intent.putExtra("RECEIPT_URL", receiptURL);
        this.startActivity ( intent );
    }
}
