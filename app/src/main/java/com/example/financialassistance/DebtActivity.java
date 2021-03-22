package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.Math;

import static java.lang.Float.parseFloat;

public class DebtActivity extends AppCompatActivity {

    public EditText debtAmountId;
    public EditText interestId;
    public EditText paymentId;
    public TextView  payoffId;
    public float debt;
    public float yearlyInterest;
    public float payment;
    public int numberOfPayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);


        debt = 0;
        yearlyInterest = 0;
        payment = 0;

        debtAmountId = findViewById(R.id.initialAmountId);
        interestId = findViewById(R.id.interestId);
        paymentId = findViewById(R.id.lengthId);
        payoffId = findViewById(R.id.finalAmountId);
    }

    public void CalculateButton(View view)
    {
        String debtString = debtAmountId.getText().toString();
        String interestString = interestId.getText().toString();
        String paymentString= paymentId.getText().toString();

        if(!"".equals(debtString)){
            debt=parseFloat(debtString);
        }

        if(!"".equals(interestString)){
            yearlyInterest=parseFloat(interestString);
        }

        if(!"".equals(paymentString)){
            payment=parseFloat(paymentString);
        }

      CalculateNumberOfPayments();
      payoffId.setText("You should pay off your debt in " + numberOfPayments + " months.");
    }

    public void CalculateNumberOfPayments()
    {
        float monthlyInterest = yearlyInterest/100/12;
        numberOfPayments= (int)Math.round((Math.log((payment/monthlyInterest)/((payment/monthlyInterest)-debt))/Math.log(1+monthlyInterest)));

    }

}
