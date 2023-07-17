package com.example.athena.common;
import com.example.athena.HelperClasses.User;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.athena.HelperClasses.User;
import com.example.athena.R;
import com.example.athena.user.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ImageView backbtn;
    Button next,login;

    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference usersRef;

    TextInputLayout fullname,username,email,password,phoneno;
    TextInputEditText f_n,u_n,e_m,p_s;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);

        usersRef = FirebaseDatabase.getInstance().getReference().child("users");


        backbtn = findViewById(R.id.signup_back_btn);
        next = findViewById(R.id.signup_button);
        login = findViewById(R.id.login_btn);

        fullname = findViewById(R.id.full_name1);
        email = findViewById(R.id.email1);
        username = findViewById(R.id.username1);
        password = findViewById(R.id.passwoed1);
        phoneno = findViewById(R.id.phone_number1);

        f_n=findViewById(R.id.full_name);
        u_n=findViewById(R.id.username);
        e_m=findViewById(R.id.email);
        p_s=findViewById(R.id.passwoed);

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                email.setError(null);
                email.setErrorEnabled(false);
            }
        });
        fullname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                fullname.setError(null);
                fullname.setErrorEnabled(false);
            }
        });
        username.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                username.setError(null);
                username.setErrorEnabled(false);
            }
        });
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                password.setError(null);
                password.setErrorEnabled(false);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String fn, un,mail,pswd;
                fn=String.valueOf(f_n.getText());
                un=String.valueOf(u_n.getText());
                mail= String.valueOf(e_m.getText());
                pswd=String.valueOf(p_s.getText());

                if (!validateFullName() || !validateUserName() || !validateEmail() || !validatePassword() || !validatePhoneNumber()) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mail,pswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                    // Get the user ID of the newly created user
                                    String userId = mAuth.getCurrentUser().getUid();
                                    // Start the login activity
                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                    finish(); // Finish the current activity

                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    if (errorMessage.contains("email address")) {
                                        validateEmail();
                                    } else if (errorMessage.contains("password")) {
                                        validatePassword();
                                    } else if (errorMessage.contains("username")) {
                                        validateUserName();
                                    }else if (errorMessage.contains("phoneno")) {
                                        validatePhoneNumber();
                                    }else {
                                        Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    public void login(View view){

        if(!validateFullName() || !validateUserName() || !validateEmail() || !validatePassword() || !validatePhoneNumber()){
            return;
        }

        startActivity(new Intent(this, Login.class));
        finish();
    }
    public void LOGIN(View view){

        startActivity(new Intent(this, Login.class));
        finish();
    }

    private boolean validateFullName(){
        String val=fullname.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            fullname.setError("Field Cannot Be Empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val=username.getEditText().getText().toString().trim();
        String checkspaces ="\\A\\w{1,20}\\z";

        if(val.isEmpty()){
            username.setError("Field Cannot Be Empty");
            return false;
        } else if(val.length()>20){
            username.setError("Username is too large(max 20 char)");
            return false;
        } else if (!val.matches(checkspaces)) {
            username.setError("No White Spaces are allowed");
            return false;
        }else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val=email.getEditText().getText().toString().trim();
        String checkEmail ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            email.setError("Field Cannot Be Empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePhoneNumber() {
        String val = phoneno.getEditText().getText().toString().trim();
        String checkPhoneNumber = "\\d{10}"; // Matches 10 digits

        if (val.isEmpty()) {
            phoneno.setError("Field Cannot Be Empty");
            return false;
        } else if (!val.matches(checkPhoneNumber)) {
            phoneno.setError("Invalid Phone Number");
            return false;
        } else {
            phoneno.setError(null);
            phoneno.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String val=password.getEditText().getText().toString().trim();
        String checkpassword ="^"+
                //"(?=.*[0-9])"+  //at least 1 digit
                //"(?=.*[a-z])"+  //at least 1 lower case letter
                //"(?=.*[A-Z])"+  //at least 1 uppercase letter
                "(?=.*[a-zA-Z])"+  //any letter
                "(?=.*[@#$%^&+=])"+  //at least 1 special charecter
                "(?=\\S+$)"+  //no white spaces
                ".{4,}"+  //at least 4 characters
                "$" ;

        if(val.isEmpty()){
            password.setError("Field Cannot Be Empty");
            return false;
        } else if (!val.matches(checkpassword)) {
            password.setError("Password should contain 6 characters, no white space and at least 1 special charecter");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}