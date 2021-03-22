package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class InvestmentCalculatorActivity extends AppCompatActivity {

    public EditText initialAmountId;
    public EditText interestId;
    public EditText lengthId;
    public TextView finalAmountId;
    public float initialAmount;
    public float yearlyInterest;
    public int length;
    public double finalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment_calculator);

        initialAmount = 0;
        yearlyInterest = 0;
        length = 0;

        initialAmountId = findViewById(R.id.initialAmountId);
        interestId = findViewById(R.id.interestId);
        lengthId = findViewById(R.id.lengthId);
        finalAmountId = findViewById(R.id.finalAmountId);
    }

    public void CalculateButton(View view) {
        String initialAmountString = initialAmountId.getText().toString();
        String interestString = interestId.getText().toString();
        String lengthString = lengthId.getText().toString();

        if (!"".equals(initialAmountString)) {
            initialAmount = parseFloat(initialAmountString);
        }

        if (!"".equals(interestString)) {
            yearlyInterest = parseFloat(interestString);
        }

        if (!"".equals(lengthString)) {
            length = parseInt(lengthString);
        }

        CalculateFinalAmount();
        DecimalFormat df = new DecimalFormat("0.00");
        finalAmountId.setText("You will have $" + df.format(finalAmount) + " after " + length + " months.");
    }

    public void CalculateFinalAmount() {
        float monthlyInterest = yearlyInterest / 100 / 12;
        finalAmount = initialAmount * Math.pow(((1 + monthlyInterest)), length);
    }
}
