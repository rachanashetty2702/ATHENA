package com.example.athena.common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.athena.R;
import com.example.athena.user.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ImageView backbtn;
    Button signup,login;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    TextInputLayout email,password;
    TextInputEditText e_m,p_s;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), UserDashboard.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);

        backbtn = findViewById(R.id.login_back_btn);
        signup = findViewById(R.id.create_account);
        login = findViewById(R.id.login_btn);

        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);


        e_m=findViewById(R.id.email);
        p_s=findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String mail,pswd;
                mail= String.valueOf(e_m.getText());
                pswd=String.valueOf(p_s.getText());

                mAuth.signInWithEmailAndPassword(mail, pswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Sucessful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getApplicationContext(), UserDashboard.class);
                                    startActivity(intent);
                                    finish();

                                } else {

                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
    }
    public void back(View view){

        startActivity(new Intent(this, Welcome.class));
        finish();
    }
    public void signUp(View view){

        startActivity(new Intent(this, SignUp.class));
        finish();
    }
    public void dashBoard(View view){

        startActivity(new Intent(this, UserDashboard.class));
        finish();
    }
}