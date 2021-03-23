package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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
    private int month2;
    private int day2;
    private int year2;
    private int minute2;
    private int second2;
    private int hour2;
    private EditText dateId;

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

        day2 = localCalendar.get(Calendar.DATE);
        month2 = localCalendar.get(Calendar.MONTH) + 1;
        year2 = localCalendar.get(Calendar.YEAR);
        minute2 = localCalendar.get(Calendar.MINUTE);
        hour2 = localCalendar.get(Calendar.HOUR);
        second2 = localCalendar.get(Calendar.SECOND);
        desc= "";

        day = getIntent().getIntExtra("day", day);
        year = getIntent().getIntExtra("year", year);
        month = getIntent().getIntExtra("month", month);
        hour = getIntent().getIntExtra("hour", hour);
        second = getIntent().getIntExtra("second", second);
        minute = getIntent().getIntExtra("minute", minute);


        day2 = getIntent().getIntExtra("day", day);
        year2 = getIntent().getIntExtra("year", year);
        month2 = getIntent().getIntExtra("month", month);
        hour2 = getIntent().getIntExtra("hour", hour);
        second2 = getIntent().getIntExtra("second", second);
        minute2 = getIntent().getIntExtra("minute", minute);

        amount = getIntent().getIntExtra("amount", amount);
        desc = getIntent().getStringExtra("desc");
        descId.setText(desc);
        account = getIntent().getStringExtra("account");

        if (amount != 0)
            amountId.setText(amount + "");

        dateId = (EditText) findViewById(R.id.dateId);
        dateId.setText(autofillDate());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int Year, int monthOfYear,
                                  int dayOfMonth) {

                year = Year;
                month =  1 + monthOfYear;
                day = dayOfMonth;
                dateId.setText(autofillDate());
            }

        };

        dateId.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog (AddPurchase.this,date, year, month - 1,
                        day).show();
            }
        });

    }


    // function to fill in the date with the correct layout
    public String autofillDate()
    {
        String date = month + "/" + day + "/" + year;
        return date;
    }

    public void saveEvent(View view) {
        amountId = findViewById(R.id.amountId);
        descId = findViewById(R.id.descId);

        amount = parseInt(amountId.getText().toString());
        desc = descId.getText().toString();

        if(hour != hour2 || day != day2 || month != month2){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            Purchase delete = new Purchase(account ,amount, desc,second2, minute2,hour2, day2, month2, year2);

            DatabaseReference deleteEvents = database.getReference(delete.pathString());
            deleteEvents.removeValue();
        }

        Purchase Save = new Purchase(account ,amount, desc,second, minute,hour, day, month, year);
        Toast.makeText(getApplicationContext(), Save.toString(), Toast.LENGTH_LONG).show();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Save.pathString());


        myRef.setValue(Save)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        BackToBudget();
                    }
                });

    }

    public void deleteEvent(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Purchase delete = new Purchase(account ,amount, desc,second2, minute2,hour2, day2, month2, year2);

        DatabaseReference deleteEvents = database.getReference(delete.pathString());
            deleteEvents.removeValue();

        BackToBudget();
    }
    public void BackToBudget()
    {
        Intent intent = new Intent(this, BudgetActivity.class);
        intent.putExtra("account",account);
        startActivity(intent);
    }
}
