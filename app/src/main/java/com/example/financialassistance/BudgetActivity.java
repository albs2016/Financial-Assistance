package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class BudgetActivity extends AppCompatActivity {

    private String account = "Connor";
    public List<Purchase> purchases = new ArrayList<>();
    public ListView purchaseId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);



      getPurchaseData();
    }




    public void getPurchaseData()
    {
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
       int day = localCalendar.get(Calendar.DATE);
       int month = localCalendar.get(Calendar.MONTH) + 1;
       int year = localCalendar.get(Calendar.YEAR);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(account + "/" +"Year -" + year+ "/" +"Month -"+ month);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                purchases.clear();
                //noEvents.clear();
                if(dataSnapshot.getValue(Purchase.class)!= null)
                   for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        purchases.add((postSnapshot.getValue(Purchase.class)));//).eventString());
                       Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
                  }

                displayPurchases();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    //Diplays the days events under the calendar
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

}
