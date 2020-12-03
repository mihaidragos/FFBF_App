package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ffbfapp.model.User;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID,msg;

    private Button logout, saveChanges, changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        saveChanges = findViewById(R.id.profilePageSaveBtn);
        logout = findViewById(R.id.profilePageLogoutBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);

        // register the click listeners for the buttons
        saveChanges.setOnClickListener(this);
        logout.setOnClickListener(this);
        changePasswordBtn.setOnClickListener(this);


        // get the current logged in user
        user = FirebaseAuth.getInstance().getCurrentUser();
        // getting the reference for the "Users" collection
        reference = FirebaseDatabase.getInstance().getReference("Users");
        // get the current user ID
        userID = user.getUid();

        // get the XML reference of the view / visual element
        final EditText profilePageName = (EditText) findViewById(R.id.profilePageName);
        final EditText profilePageEmail = (EditText) findViewById(R.id.profilePageEmail);

        // Check if any email address is passed from the previous Activity in order to auto-complete the email field
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                msg = "";
            } else {
                msg = extras.getString("msg");
            }

            Toast.makeText(UserProfileActivity.this, msg,Toast.LENGTH_LONG).show();
        }

        // get the data from the DB and replace the text content of the profile page with the current users' details
        // we use the `reference` of the DB for current user ( .child(userID) ) to get data from DB
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User userProfile = dataSnapshot.getValue(User.class);

                if(userProfile != null){
                    String profileName  = userProfile.name;
                    String profileEmail = userProfile.email;

                    profilePageName.setText(profileName);
                    profilePageEmail.setText(profileEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }

    // define what the click listeners will do for each button
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.changePasswordBtn:
                String userEmail = user.getEmail();
                i = new Intent(UserProfileActivity.this, LoginActivity.class);
                i.putExtra("email", userEmail);
                i.putExtra("CHANGE_PASS", true);
                startActivity(i);
                break;
            case R.id.profilePageLogoutBtn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserProfileActivity.this, LoginActivity.class));
                break;
            case R.id.adminPanelButton:
                System.out.println("merge");
                i = new Intent(UserProfileActivity.this, AdminPanelActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}