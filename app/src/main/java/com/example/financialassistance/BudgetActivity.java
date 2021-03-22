package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

public class BudgetActivity extends AppCompatActivity {

    private String account;
    public List<Purchase> purchases = new ArrayList<>();
    public List<String> noPurchases = new ArrayList<>();
    public ListView purchaseId;
    public EditText incomeId;
    public income income = new income();
    public int moneySpent;
    private int month;
    private int day;
    private int year;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        day = localCalendar.get(Calendar.DATE);
        month = localCalendar.get(Calendar.MONTH) + 1;
        year = localCalendar.get(Calendar.YEAR);
        minute = localCalendar.get(Calendar.MINUTE);

        getIncome();
        getPurchaseData();
        showMonth();

        account = getIntent().getStringExtra("account");
        purchaseId = findViewById(R.id.purchaseId);
        purchaseId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                if (!purchases.isEmpty()) {
                    Purchase check = new Purchase();
                    check = purchases.get(position);
                    Toast.makeText(getApplicationContext(), check.toString(), Toast.LENGTH_LONG).show();
                    editPurchase(check);
                }
            }
        });
    }

    public void getPurchaseData() {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        account = getIntent().getStringExtra("account");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(account + "/" + "Budget" + "/" + "Year -" + year + "/" + "Month -" + month);

        moneySpent = 0;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                purchases.clear();
                noPurchases.clear();
                if (dataSnapshot.getValue(Purchase.class) != null)
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        purchases.add((postSnapshot.getValue(Purchase.class)));
                        moneySpent += postSnapshot.getValue(Purchase.class).getAmount();
                    }

                displayPurchases();
                showMoneyLeft();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getIncome() {
        account = getIntent().getStringExtra("account");
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(account + "/" + "Income" + "/" + "Year -" + year + "/" + "Month -" + month);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue(income.class) != null) {
                    income = dataSnapshot.getValue(income.class);
                    EditText editText = findViewById(R.id.incomeId);
                    editText.setText(income.toString(), TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


    public void displayPurchases() {
        purchaseId = findViewById(R.id.purchaseId);

        if (!purchases.isEmpty()) {
            ArrayAdapter<Purchase> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, purchases);

            purchaseId.setAdapter(adapter);

        } else {
            noPurchases.add("No purchases yet this month.");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, noPurchases);
            purchaseId.setAdapter(adapter);
        }
    }

    public void AddPurchaseButton(View view) {
        Intent intent = new Intent(this, AddPurchase.class);
        intent.putExtra("account", account);
        startActivity(intent);
    }


    public void setIncome(View view) {
        incomeId = findViewById(R.id.incomeId);
        int new_income = parseInt(incomeId.getText().toString());
        income Save = new income(new_income);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(account + "/" + "Income" + "/" + "Year -" + year + "/" + "Month -" + month);
        myRef.setValue(Save)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Income Set", Toast.LENGTH_LONG).show();
                        showMoneyLeft();
                        return;
                    }
                });
    }



    public void showMoneyLeft() {

        int moneyLeft = income.getIncome() - moneySpent;
       TextView moneySpentView = findViewById(R.id.moneySpentId);
        moneySpentView.setText("You have spent $" + moneySpent + " this month.");

        TextView moneyLeftView = findViewById(R.id.moneyLeftId);
        moneyLeftView.setText("You have $" + moneyLeft + " remaining this month.");

    }

    public void DebtButton(View view)
    {
        Intent intent = new Intent(this, DebtActivity.class);
        startActivity(intent);
    }

    public void editPurchase(Purchase check)
    {
        Toast.makeText(getApplicationContext(), check.getDescription(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AddPurchase.class);

        intent.putExtra("day",check.getDay());
        intent.putExtra("month",check.getMonth());
        intent.putExtra("year",check.getYear());
        intent.putExtra("minute",check.getMinute());
        intent.putExtra("hour",check.getHour());
        intent.putExtra("second",check.getSecond());
        intent.putExtra("amount",check.getAmount());
        intent.putExtra("desc",check.getDescription());
        intent.putExtra("account",check.getAccount());
        startActivity(intent);
    }

    public void InvestmentButton(View view) {
        Intent intent = new Intent(this, InvestmentCalculatorActivity.class);
        startActivity(intent);
    }

    public void showMonth() {

        String MonthString = "NO MONTH";
        if (month == 1)
            MonthString = "January";
        if (month == 2)
            MonthString = "February";
        if (month == 3)
            MonthString = "March";
        if (month == 4)
            MonthString = "April";
        if (month == 5)
            MonthString = "May";
        if (month == 6)
            MonthString = "June";
        if (month == 7)
            MonthString = "July";
        if (month == 8)
            MonthString = "August";
        if (month == 9)
            MonthString = "September";
        if (month == 10)
            MonthString = "October";
        if (month == 11)
            MonthString = "November";
        if (month == 12)
            MonthString = "December";

        TextView monthView = findViewById(R.id.monthId);
        monthView.setText(MonthString + " Budget");
    }

}
