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

import com.example.ffbfapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    // public static String Msg2Send = "name";
    private EditText nameInput, lastNameInput, emailInput, passwordInput,confirmPasswordInput;
    private Button submitBtn;
    private ProgressBar progressBar;
    private TextView banner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Get the Firebase DB instance
        mAuth = FirebaseAuth.getInstance();
        // declare variables
        submitBtn               = findViewById(R.id.registerSubmitBtn);
        nameInput               = findViewById(R.id.nameInput);
        lastNameInput           = findViewById(R.id.lastNameInput);
        emailInput              = findViewById(R.id.emailInput);
        passwordInput           = findViewById(R.id.passwordInput);
        confirmPasswordInput    = findViewById(R.id.confirmPasswordInput);
        progressBar             = findViewById(R.id.progressBar);
        banner                  = findViewById(R.id.registerBanner);
        // register the click listener on the submit button
        submitBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerBanner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerSubmitBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String name = nameInput.getText().toString().trim();
        String lastName = lastNameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if(name.isEmpty()){
            nameInput.setError("Name is a required field");
            nameInput.requestFocus();
            return;
        }

        if(email.isEmpty()){
            emailInput.setError("Email is a required field");
            emailInput.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Please provide a valid email address");
            emailInput.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordInput.setError("A Password is required");
            passwordInput.requestFocus();
            return;
        }

        if(password.length() < 8){
            passwordInput.setError("The password should have at least 8 characters");
            passwordInput.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            confirmPasswordInput.setError("Please confirm your password");
            confirmPasswordInput.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            confirmPasswordInput.setError("Please confirm your password");
            confirmPasswordInput.requestFocus();
            return;
        }

        if(!confirmPassword.equals(password)){
            confirmPasswordInput.setError("The passwords don't match");
            confirmPasswordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                     if(task.isSuccessful()){
                         User user = new User(name, lastName, email);

                         FirebaseDatabase.getInstance().getReference("Users")
                                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                 .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()) {
                                     Toast.makeText(RegisterActivity.this, "Registration successful. Please verify your email before login", Toast.LENGTH_LONG).show();


                                     progressBar.setVisibility(View.GONE);

                                     // Redirect to login
                                     startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                 } else {
                                     Toast.makeText(RegisterActivity.this,"Failed to register", Toast.LENGTH_LONG);
                                     progressBar.setVisibility(View.GONE);
                                 }
                             }
                         });

                     }
                 }
             });

    }
}