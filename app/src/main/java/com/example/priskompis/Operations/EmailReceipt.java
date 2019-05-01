package com.example.priskompis.Operations;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.priskompis.LoginActivity;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class EmailReceipt extends AsyncTask<String, Void, String> {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String receiptURL;
    private final String emailto;

    public EmailReceipt(String receiptURL, String emailto) {
        this.receiptURL = receiptURL;
        this.emailto = emailto;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        try {
            response = sendEmail(receiptURL, emailto);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    protected String sendEmail (String receiptURL, String emailto) throws JSONException, IOException {

        URL url = new URL("https://us-central1-pris-kompis.cloudfunctions.net/sendMail");

        JSONObject json = new JSONObject();
        json.put("title","Your Digital Receipt from Pris Kompis");
        json.put("receipt_url",receiptURL);
        json.put("emailTo", emailto);

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(30, TimeUnit.SECONDS);
        client.setWriteTimeout(30, TimeUnit.SECONDS);

        RequestBody body = RequestBody.create(JSON, json.toString());
        System.out.println("Send Email to :" +body.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        System.out.println("This is request" + request.toString());
        Response httpresponse = client.newCall(request).execute();

        return httpresponse.toString();
    }

}
