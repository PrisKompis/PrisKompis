package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class BudgetSet extends AppCompatActivity
    {
    private int budget;
    private EditText budgetInput;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_set);
        budgetInput=findViewById(R.id.editTextBudget);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.icon36);
        }

    @Override
    public void onBackPressed()
        {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

        }

    public void setBudget(View view)
        {
        budget=Integer.parseInt(budgetInput.getText().toString().trim());
        Intent intent = new Intent (getApplicationContext(),InShop.class);
        intent.putExtra("budget",budget);
        startActivity(intent);

        }

    public void clear(View view)
        {
        budgetInput.getText().clear();
        }
    }
