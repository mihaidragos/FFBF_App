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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText    forgotEmailInput;
    private Button      forgotPasswordBtn;
    private ProgressBar forgotProgressBar;
    private String      emailIntent;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        forgotEmailInput   = (EditText) findViewById(R.id.forgotEmailInput);
        forgotPasswordBtn  = (Button) findViewById(R.id.forgotPasswordBtn);

        mAuth = FirebaseAuth.getInstance();

        // Check if any email address is passed from the previous Activity in order to auto-complete the email field
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                emailIntent = "";
            } else {
                emailIntent = extras.getString("email");
            }

        } else {
            emailIntent = (String) savedInstanceState.getSerializable("email");
        }

        // update the text in the email input view
        forgotEmailInput.setText(emailIntent);

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });


    }
    private void resetPassword() {
        String email = forgotEmailInput.getText().toString().trim();

        if(email.isEmpty()){
            forgotEmailInput.setError("Email is required!");
            forgotEmailInput.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            forgotEmailInput.setError("Please provide valid email address");
            forgotEmailInput.requestFocus();
            return;
        }

        forgotProgressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Intent i = new Intent(ForgotActivity.this, LoginActivity.class);
                    i.putExtra("msg", "Check your email for the password reset link");
                    startActivity(i);
                } else {
                    Toast.makeText(ForgotActivity.this, "Oops, something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}