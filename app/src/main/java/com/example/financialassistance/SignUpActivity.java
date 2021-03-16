package com.example.financialassistance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {

        public EditText userId;
        public String username;
        public EditText passwordId;
        public String password;
        public EditText confirmId;
        public String confirm;

        public String account;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_up);

            userId = findViewById(R.id.username);
            passwordId = findViewById(R.id.password);
            confirmId = findViewById(R.id.confirm_password);

        }


        //saves a user account checks if info is valid
        public void SaveBtn(View view) {
            account = userId.getText().toString();
            password = passwordId.getText().toString();
            confirm = confirmId.getText().toString();

            if (password.equals(confirm)&& !account.equals("") && !password.equals(""))
            {
                hashPassword();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference(account);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null && !account.equals("")) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference(account + "/password/");
                            myRef.setValue(password);


                            Toast.makeText(getApplicationContext(), account +" Account Saved Successfully", Toast.LENGTH_LONG).show();
                            startMain();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Please Enter Matching Passwords or a Unique Username", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        }

        //starts the main activity.
        public void startMain()
        {
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("account",account);
            startActivity(intent);
        }

        public void hashPassword(){
            try {
                // Create MessageDigest instance for MD5
                MessageDigest md = MessageDigest.getInstance("MD5");
                //Add password bytes to digest
                md.update(password.getBytes());
                //Get the hash's bytes
                byte[] bytes = md.digest();
                //This bytes[] has bytes in decimal format;
                //Convert it to hexadecimal format
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                //Get complete hashed password in hex format
                password = sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }
    }

