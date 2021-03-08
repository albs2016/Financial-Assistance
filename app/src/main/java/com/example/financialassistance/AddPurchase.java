package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.TimeZone;

import static java.lang.Integer.parseInt;

public class AddPurchase extends AppCompatActivity {

    private int amount;
    private String desc;
    private String account;
    public EditText amountId;
    public EditText descId;
    private int month;
    private int day;
    private int year;
    private int minute;
    private int second;
    private int hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
        amountId = findViewById(R.id.amountId);
        descId = findViewById(R.id.descId);


        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        day = localCalendar.get(Calendar.DATE);
        month = localCalendar.get(Calendar.MONTH) + 1;
        year = localCalendar.get(Calendar.YEAR);
        minute = localCalendar.get(Calendar.MINUTE);
        hour = localCalendar.get(Calendar.HOUR);
        second = localCalendar.get(Calendar.SECOND);
        desc= "";

        day = getIntent().getIntExtra("day", day);
        year = getIntent().getIntExtra("year", year);
        month = getIntent().getIntExtra("month", month);
        hour = getIntent().getIntExtra("hour", hour);
        second = getIntent().getIntExtra("second", second);
        minute = getIntent().getIntExtra("minute", minute);
        amount = getIntent().getIntExtra("amount", amount);
        desc = getIntent().getStringExtra("desc");
        descId.setText(desc);
        account = getIntent().getStringExtra("account");

        if (amount != 0)
            amountId.setText(amount + "");

    }

    public void saveEvent(View view) {
        amountId = findViewById(R.id.amountId);
        descId = findViewById(R.id.descId);

        amount = parseInt(amountId.getText().toString());
        desc = descId.getText().toString();

        Purchase Save = new Purchase(account ,amount, desc,second, minute,hour, day, month, year);
        Toast.makeText(getApplicationContext(), Save.toString(), Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Save.pathString());

       // DatabaseReference deleteEvents = database.getReference(account + "/" + "Event" + "/" +"Amount -"
       //         + amount);

        myRef.setValue(Save)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        BackToBudget();
                    }
                });

    }

    public void BackToBudget()
    {
        Intent intent = new Intent(this, BudgetActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }
}
