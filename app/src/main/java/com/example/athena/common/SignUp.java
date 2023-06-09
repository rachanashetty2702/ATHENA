package com.example.athena.common;
import com.example.athena.HelperClasses.User;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

    TextInputLayout fullname,username,email,password;
    TextInputEditText f_n,u_n,e_m,p_s;
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

        f_n=findViewById(R.id.full_name);
        u_n=findViewById(R.id.username);
        e_m=findViewById(R.id.email);
        p_s=findViewById(R.id.passwoed);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String fn, un,mail,pswd;
                fn=String.valueOf(f_n.getText());
                un=String.valueOf(u_n.getText());
                mail= String.valueOf(e_m.getText());
                pswd=String.valueOf(p_s.getText());
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

                                    saveUserData(userId, fn, mail, un);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
    private void saveUserData(String userId, String fullName, String email, String username) {
        User user = new User(userId, fullName, email, username);

        // Save user data to the database
        usersRef.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Data saved successfully
                            Toast.makeText(SignUp.this, "User data saved successfully.", Toast.LENGTH_SHORT).show();

                        } else {
                            // Data save failed
                            Toast.makeText(SignUp.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void login(View view){

        if(!validateFullName()| !validateUserName() | !validateEmail() | !validatePassword()){
            return;
        }

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
            username.setError("Username is too large");
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
        }else {
            email.setError(null);
            email.setErrorEnabled(false);
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
            password.setError("Password should contain 4 characters");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }
}