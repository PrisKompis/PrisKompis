package com.example.priskompis.Operations;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.priskompis.LoginActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class StripeCharge extends AsyncTask<String, Void, String> {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String token;
    int amount;

    public StripeCharge(String token, int amount) {
        this.token = token;
        this.amount = amount;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        String description = "Payment for your purchase at Pris Kompis";
        String response = null;
        try {
            response = postData(description, token, amount);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("Result", s);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String postData(String description, String token, int amount) throws IOException, JSONException {

        URL url = new URL("https://us-central1-pris-kompis.cloudfunctions.net/charge");

        JSONObject json = new JSONObject();

        JSONObject tokenObject = new JSONObject();
        tokenObject.put("id", token);

        JSONObject chargeObject = new JSONObject();
        chargeObject.put("amount", amount);
        chargeObject.put("currency", "SEK");
        chargeObject.put("description", "Pris Kompis Bill Amount");
       // chargeObject.put("receipt_email", "");

        json.put("token", tokenObject);
        json.put("charge", chargeObject);


        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        System.out.println("Request \n"+request);
        Response httpresponse = client.newCall(request).execute();

        if (httpresponse.isSuccessful() == true) {
            JSONObject reader = new JSONObject(httpresponse.body().string());
            JSONObject response = new JSONObject(reader.get("body").toString());
            return getReceiptURL(response.toString());
        } else {
            Log.e("Error code:", String.valueOf(httpresponse.code()));
            Log.e("Error Message:", httpresponse.body().string());
            return httpresponse.body().string();
        }
    }

    protected static String getReceiptURL(String response) {
        String receiptURL="";
        try {
            JSONObject responseObject = new JSONObject(response);
            JSONObject chargeObject = new JSONObject(responseObject.get("charge").toString());
            receiptURL = chargeObject.get("receipt_url").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receiptURL;
    }
}
