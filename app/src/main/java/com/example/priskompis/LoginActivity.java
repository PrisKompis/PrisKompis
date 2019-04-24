package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = findViewById(R.id.btnsignup_reg);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
            }
        });
    }


    public void loginToApp(View view) {

        // DB Code here to validate user Id and password

        // If login is successful
        Intent intent = new Intent(LoginActivity.this,ProductSelection.class);
        startActivity(intent);

        // If login failed
        Toast.makeText(LoginActivity.this, "Login Failed. Verify email Id and password and try again", Toast.LENGTH_LONG).show();

    }
}
