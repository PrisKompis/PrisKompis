package com.example.priskompis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnSignIn,btnSignup;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        email=findViewById(R.id.email_reg);
        password=findViewById(R.id.password_reg);

        btnSignIn=findViewById(R.id.btnsignin_reg);
        btnSignup = findViewById(R.id.btnsignup_reg);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

    }

    public void loginToApp(View view) {

        {
            final String mEmail=email.getText().toString().trim();
            final String mPass=password.getText().toString().trim();

            if (TextUtils.isEmpty(mEmail)){
                email.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(mPass)){
                password.setError("Required Field..");
                return;
            }

            mDialog.setMessage("Processing..");
            mDialog.show();

            mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        mDialog.dismiss();
                        setEmailToPreferences(mEmail);
                        startActivity(new Intent(getApplicationContext(), BudgetSet.class));
                        Toast.makeText(getApplicationContext(), "Login Complete", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Problem..", Toast.LENGTH_SHORT).show();

                    }

                }

            });


        }
    }

    public void setEmailToPreferences(String email) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("userEmail", email);
        edit.apply();

    }
}
