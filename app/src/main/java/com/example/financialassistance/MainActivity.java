package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int number = 1;


    }

    public void DebtButton(View view)
    {
        Intent intent = new Intent(this, DebtActivity.class);
        startActivity(intent);
    }

    public void BudgetButton(View view)
    {
        Intent intent = new Intent(this, BudgetActivity.class);
        startActivity(intent);
    }
}
