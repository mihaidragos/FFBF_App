package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassActivity extends AppCompatActivity {

    private Button changePass;
    private String newPass, confirmNewPass;
    private EditText newPassInput, confirmNewPassInput;
    private ProgressBar changePassPB;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        newPassInput = findViewById(R.id.newPassInput);
        confirmNewPassInput = findViewById(R.id.confirmNewPassInput);
        changePassPB = findViewById(R.id.changePassPB);
        changePass = findViewById(R.id.changePassBtn);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = FirebaseAuth.getInstance().getCurrentUser();

                newPass = newPassInput.getText().toString().trim();
                confirmNewPass = confirmNewPassInput.getText().toString().trim();

                if(newPass.isEmpty()){
                    newPassInput.setError("A Password is required");
                    newPassInput.requestFocus();
                    return;
                }

                if(newPass.length() < 8){
                    newPassInput.setError("The password should have at least 8 characters");
                    newPassInput.requestFocus();
                    return;
                }

                if(confirmNewPass.isEmpty()){
                    confirmNewPassInput.setError("Please confirm your password");
                    confirmNewPassInput.requestFocus();
                    return;
                }

                if(confirmNewPass.isEmpty()){
                    confirmNewPassInput.setError("Please confirm your password");
                    confirmNewPassInput.requestFocus();
                    return;
                }

                if(!confirmNewPass.equals(newPass)){
                    confirmNewPassInput.setError("The passwords don't match");
                    confirmNewPassInput.requestFocus();
                    return;
                }

                changePassPB.setVisibility(View.VISIBLE);

                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isCanceled()){
                            changePassPB.setVisibility(View.GONE);
                        } else if (task.isSuccessful()) {
                            Intent i = new Intent(ChangePassActivity.this, UserProfileActivity.class);
                            i.putExtra("msg", "Password changed successfully!");
                            startActivity(i);
                        }
                    }
                });
            }
        });

    }
}