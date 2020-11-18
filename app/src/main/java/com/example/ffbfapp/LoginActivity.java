package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView forgotPassword,registerLink;
    private EditText emailInput,passwordInput;
    private Button loginBtn;
    private String emailIntent,msg;
    private Boolean CHANGE_PASS;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // declare all variables
        emailInput      = findViewById(R.id.loginEmail);
        passwordInput   = findViewById(R.id.loginPassword);
        loginBtn        = findViewById(R.id.loginBtn);
        forgotPassword  = findViewById(R.id.forgotPassword);
        registerLink    = findViewById(R.id.registerLink);
        progressBar     = findViewById(R.id.progressBarLogin);
        forgotPassword  = findViewById(R.id.forgotPassword);

        msg = "";

        // register the click listener on the register link
        registerLink.setOnClickListener(this);
        // register the click listener on the login button
        loginBtn.setOnClickListener(this);
        // register the click listener on the Forgot Password TextView
        forgotPassword.setOnClickListener(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Check if any email address is passed from the previous Activity in order to auto-complete the email field
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                emailIntent = "";
                CHANGE_PASS = false;
                msg = "";
            } else {
                emailIntent = extras.getString("email");
                CHANGE_PASS = extras.getBoolean("CHANGE_PASS");
            }
            if(!msg.matches("")){
                Toast.makeText(LoginActivity.this, msg,Toast.LENGTH_LONG).show();
            }
        } else {
            emailIntent = (String) savedInstanceState.getSerializable("email");
            CHANGE_PASS = savedInstanceState.getBoolean("CHANGE_PASS");
        }

        // update the text in the email input view
        emailInput.setText(emailIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerLink:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.forgotPassword:
                // for the Forgot Password page the text from email input on login page is passed through to forgot password page ofr better UX
                String email    = emailInput.getText().toString().trim();
                Intent i = new Intent(this, ForgotActivity.class);
                i.putExtra("email", email);
                startActivity(i);
        }
    }

    private void userLogin() {
        String email    = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // check email is not empty
        if(email.isEmpty()){
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }
        // check the email is a valid email address format
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Please provide a valid email address");
            emailInput.requestFocus();
            return;
        }
        // check if the password is not empty
        if(password.isEmpty()){
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return;
        }
        // check if the password is the required length
        if(password.length() < 8){
            passwordInput.setError("Passwords should contain at least 8 characters");
            passwordInput.requestFocus();
            return;
        }

        // set the progress bar to visible if all validations are passed
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    if(user.isEmailVerified()){
                        // redirect to password change page if `CHANGE_PASS` extra is passed from my account page
                        if(CHANGE_PASS == true){
                            // redirect to Change Password page if login is successful
                            startActivity(new Intent(LoginActivity.this, ChangePassActivity.class));
                        } else {
                            // redirect to Homepage if login is successful
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }

                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Please verify your email address first", Toast.LENGTH_LONG).show();
                        // hide the progress bar while user verifies his email and hits login button again
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    // Display failure message if login fails
                    Toast.makeText(LoginActivity.this, "Failed to login. Please try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }
}