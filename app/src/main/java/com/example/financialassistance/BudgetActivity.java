package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        private String account = "Connor";
        public List<Purchase> purchases = new ArrayList<>();
        public ListView purchaseId;
        public EditText incomeId;
        public income income = new income();
        public int moneySpent;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_budget);


            getIncome();
          getPurchaseData();

        }




        public void getPurchaseData()
        {
            Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
           int day = localCalendar.get(Calendar.DATE);
           int month = localCalendar.get(Calendar.MONTH) + 1;
           int year = localCalendar.get(Calendar.YEAR);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(account +  "/" +"Budget" + "/" +"Year -" + year+ "/" +"Month -"+ month);

            moneySpent= 0;
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    purchases.clear();
                    //noEvents.clear();
                    if(dataSnapshot.getValue(Purchase.class)!= null)
                       for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
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

        public void getIncome()
        {
            Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
            int month = localCalendar.get(Calendar.MONTH) + 1;
            int year = localCalendar.get(Calendar.YEAR);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference(account + "/" +"Income" +"/" +"Year -" + "2021" + "/" +"Month -"+"3");


            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.getValue(income.class)!= null) {
                        income = dataSnapshot.getValue(income.class);
                        Toast.makeText(getApplicationContext(), income.toString(), Toast.LENGTH_LONG).show();
                        EditText editText = (EditText)findViewById(R.id.incomeId);
                        editText.setText(income.toString(), TextView.BufferType.EDITABLE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }


        public void displayPurchases()
        {
         //  displayHeader();
            purchaseId = findViewById(R.id.purchaseId);

            if (!purchases.isEmpty()) {
                ArrayAdapter<Purchase> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, purchases);

                purchaseId.setAdapter(adapter);

            }
      //      else {
               // noEvents.add("No Events on This Date.");
        //        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
      ///                  android.R.layout.simple_list_item_1, android.R.id.text1, noEvents);
      //          eventId.setAdapter(adapter);

       //     }
        }

        public void AddPurchaseButton(View view)
        {
            Intent intent = new Intent(this, AddPurchase.class);
            startActivity(intent);
        }

        public void setIncome(View view) {
            incomeId = findViewById(R.id.incomeId);
            int new_income = parseInt(incomeId.getText().toString());
            income Save = new income(new_income);

            Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
            int month = localCalendar.get(Calendar.MONTH) + 1;
            int year = localCalendar.get(Calendar.YEAR);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( account + "/" +"Income" +"/" +"Year -" + year + "/" +"Month -"+month);
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
           TextView moneySpentView = (TextView)findViewById(R.id.moneySpentId);
            moneySpentView.setText("You have spent $" + moneySpent + " this month.");

            TextView moneyLeftView = (TextView)findViewById(R.id.moneyLeftId);
            moneyLeftView.setText("You have $" + moneyLeft + " remaining this month.");

        }


    }
