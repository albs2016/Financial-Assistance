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
    private String account = "Connor";
    public EditText amountId;
    public EditText descId;
    private int month;
    private int day;
    private int year;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        day = localCalendar.get(Calendar.DATE);
        month = localCalendar.get(Calendar.MONTH) + 1;
        year = localCalendar.get(Calendar.YEAR);
        minute = localCalendar.get(Calendar.MINUTE);
    }

    public void saveEvent(View view) {
        amountId = findViewById(R.id.amountId);
        descId = findViewById(R.id.descId);

        amount = parseInt(amountId.getText().toString());
        desc = descId.getText().toString();

        Purchase Save = new Purchase("Connor",amount, desc, minute, day, month, year);
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
