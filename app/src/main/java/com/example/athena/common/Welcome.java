package com.example.athena.common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.athena.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }
    public void login(View view){

        startActivity(new Intent(this, Login.class));
        finish();
    }
    public void signUp(View view){

        startActivity(new Intent(this, SignUp.class));
        finish();
    }
}