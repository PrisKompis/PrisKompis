package com.example.priskompis;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
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
        budgetInput.setOnKeyListener(new View.OnKeyListener()
            {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                switch (keyCode)
                    {
                    case KeyEvent.KEYCODE_ENTER:
                        setBudgetEnter();
                        return true;

                        default:
                            return false;
                    }
                }
            });
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
    case KeyEvent.KEYCODE_ENTER:

        setBudgetEnter();
        return true;

    case KeyEvent.KEYCODE_NUMPAD_ENTER:
        setBudgetEnter();
        return true;

    default:
        return super.onKeyDown(keyCode, event);
    }


    }
    public void setBudgetEnter()
        {
        budget=Integer.parseInt(budgetInput.getText().toString().trim());
        Intent intent = new Intent (getApplicationContext(),InShop.class);
        intent.putExtra("budget",budget);
        startActivity(intent);
        }

    }
