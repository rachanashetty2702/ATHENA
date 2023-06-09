package com.example.athena.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.athena.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private TextView name, user_name, mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        user_name= findViewById(R.id.username);

        String uid = currentUser.getUid();
        usersRef.child(uid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fullName = dataSnapshot.child("fullName").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String username = dataSnapshot.child("username").getValue(String.class);


                    name.setText(fullName);
                    mail.setText(email);
                    user_name.setText(username);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Throwable databaseError = null;
                Log.e("DatabaseError", "Error: " + databaseError.getMessage());
            }


        });


    }
    public void back(View view){

        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }
}