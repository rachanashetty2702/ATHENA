package com.example.athena.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.athena.HelperClasses.User;
import com.example.athena.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity {
    private TextInputEditText nameEditText, usernameEditText, emailEditText, bdayEditText, heightEditText, weighteditText;
    private Button saveButton;
    private DatabaseReference userRef;
    private FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        nameEditText = findViewById(R.id.name);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.mail);
        bdayEditText = findViewById(R.id.bday);
        weighteditText = findViewById(R.id.weight);
        heightEditText = findViewById(R.id.height);
        saveButton = findViewById(R.id.button);

        // Get reference to the "users" node in the Firebase Realtime Database
        userRef = FirebaseDatabase.getInstance().getReference().child("users");

        // Get the current authenticated user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set a click listener for the save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        // Display the user data if available
        if (currentUser != null) {
            displayUserData();
        }
    }

    private void saveUserData() {
        // Get the entered data
        String name = nameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String bday = bdayEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        String weight = weighteditText.getText().toString().trim();

        // Create a new user object with the entered data
        User user = new User(name, username, email, bday, height, weight);

        // Save the user data to the Firebase Realtime Database using the user's ID
        if (currentUser != null) {
            userRef.child(currentUser.getUid()).updateChildren(user.toMap())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(MyAccount.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MyAccount.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


    private void displayUserData() {
        // Retrieve the user data from the Firebase Realtime Database using the user's ID
        if (currentUser != null) {
            userRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        User user = dataSnapshot.getValue(User.class);

                        // Display the user data in the respective fields
                        if (user != null) {
                            nameEditText.setText(user.getName());
                            usernameEditText.setText(user.getUsername());
                            emailEditText.setText(user.getEmail());
                            bdayEditText.setText(user.getBday());
                            heightEditText.setText(user.getHeight());
                            weighteditText.setText(user.getWeight());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(MyAccount.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void back(View view) {
        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }
}