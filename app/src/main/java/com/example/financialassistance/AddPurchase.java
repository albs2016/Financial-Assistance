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

import static java.lang.Integer.parseInt;

public class AddPurchase extends AppCompatActivity {

    private int amount;
    private String desc;
    private String account = "Connor";
    public EditText amountId;
    public EditText descId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_purchase);
    }

    public void saveEvent(View view) {
        amountId = findViewById(R.id.amountId);
        descId = findViewById(R.id.descId);

        amount = parseInt(amountId.getText().toString());
        desc = descId.getText().toString();

        Purchase Save = new Purchase("Connor",amount, desc);
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
