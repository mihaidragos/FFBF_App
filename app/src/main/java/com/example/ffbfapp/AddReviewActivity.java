package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffbfapp.model.FoodVenueType;
import com.example.ffbfapp.model.Review;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddReviewActivity extends AppCompatActivity {
    private TextView mPageTitle;
    private EditText mReviewSubject, mReviewContent;
    private Spinner mReviewRating;
    private Button mReviewBtn;
    private Bundle extras;

    private DatabaseReference mRef;
    private FoodVenueType foodVenueType;

    private String pageTitle, subject, content, uid, user,newKey;
    private int rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        extras = getIntent().getExtras();
        foodVenueType   = (FoodVenueType) extras.get("foodVenueType");
        uid             = (String) extras.get("uid");
        mRef = FirebaseDatabase.getInstance().getReference("reviews").child(foodVenueType.getLabel()).child(uid);
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        newKey = mRef.push().getKey();

        getPageElements();

        mReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReview();
            }
        });
    }


    private void getPageElements(){
        mPageTitle          = findViewById(R.id.tvARA_pageTitle);
        mReviewSubject      = findViewById(R.id.etARA_reviewTitle);
        mReviewContent      = findViewById(R.id.etARA_reviewContent);
        mReviewRating       = findViewById(R.id.spnARA_reviewRating);
        mReviewBtn          = findViewById(R.id.btnARA_submit);
    }

    private boolean inputsValid(){
        subject     = mReviewSubject.getText().toString().trim();
        content     = mReviewContent.getText().toString().trim();
        rating      = Integer.parseInt(mReviewRating.getSelectedItem().toString());


        if(subject.length() < 3){
            mReviewSubject.setError("The review subject is a required field and should have at least 3 characters");
            mReviewSubject.requestFocus();
            return false;
        }

        if(rating < 1 && rating > 5){
            Toast.makeText(AddReviewActivity.this, "Please select a rating for the venue", Toast.LENGTH_LONG).show();
            mReviewRating.requestFocus();
            return false;
        }

        return true;
    }

    private void addReview(){
        if(inputsValid()){
            Review review = new Review(
                    user,
                    subject,
                    content,
                    rating
            );

            mRef.child(newKey).setValue(review)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(AddReviewActivity.this, ReviewsListActivity.class);
                                i.putExtra("uid", uid);
                                startActivity(i);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddReviewActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}