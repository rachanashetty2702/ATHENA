package com.example.athena.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.athena.MoodMusic;
import com.example.athena.R;

public class UserDashboard extends AppCompatActivity {
    CardView moodmusic;
    CardView myaccount;
    CardView periodtracker;
    CardView safety;
    CardView healthdic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        moodmusic = findViewById(R.id.moodmusic);
        moodmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, MoodMusic.class);
                startActivity(intent);
            }
        });
        myaccount = findViewById(R.id.myaccount);
        myaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, MyAccount.class);
                startActivity(intent);
            }
        });
        periodtracker = findViewById(R.id.periodtracker);
        periodtracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, PeriodsTracker.class);
                startActivity(intent);
            }
        });
        safety = findViewById(R.id.safety);
        safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, SafetySOS.class);
                startActivity(intent);
            }
        });
        healthdic = findViewById(R.id.healthdic);
        healthdic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDashboard.this, HealthDictionary.class);
                startActivity(intent);
            }
        });
    }
}