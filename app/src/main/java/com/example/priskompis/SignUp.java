package com.example.priskompis;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SignUp extends AppCompatActivity {
private EditText email;
private EditText pass,repass;
private Button btnSignup;
private FirebaseAuth mAuth;
private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();

        mDialog=new ProgressDialog(this);

        email=findViewById(R.id.btn_emailId);
        pass=findViewById(R.id.btn_pwd);
        btnSignup = findViewById(R.id.btn_reg);


            btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
                {

                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();
                String mRepass = repass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail))
                    {
                    email.setError("Required Field..");
                    return;
                    }
                if (TextUtils.isEmpty(mPass))
                    {
                    pass.setError("Required Field..");
                    return;
                    }

                if (TextUtils.isEmpty(mRepass))
                    {
                    repass.setError("Required Field");
                    return;
                    }

                if (!mPass.equals(mRepass))
                    {Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT);
                return;
                }



            mDialog.setMessage("Processing..");
            mDialog.show();

            mAuth.createUserWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                    {
                    if (task.isSuccessful())
                        {
                        mDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), BudgetSet.class));
                        Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();

                        }
                    else
                        {
                        Toast.makeText(getApplicationContext(), "Problem", Toast.LENGTH_SHORT).show();
                        }
                    }
                });





            }
        });
    }


}
