package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffbfapp.model.Cuisine;
import com.example.ffbfapp.model.FoodVenue;
import com.example.ffbfapp.model.FoodVenueType;
import com.example.ffbfapp.model.PriceTag;
import com.example.ffbfapp.model.Review;
import com.example.ffbfapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ReviewsListActivity extends AppCompatActivity {
    private TextView pageTitle, reviewsNumber, reviewsAverage;
    private RatingBar ratingBar;
    private Button addReviewBtn;
    private Bundle extras;
    private RecyclerView mRecyclerView;

    private FoodVenueType foodVenueType;
    private FoodVenue foodVenue;
    private ArrayList<Review> reviewsList;
    private ReviewsListAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem addReviewMenuItem;
    private User user;
    private DatabaseReference mRef;

    private String uid, venueName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_list);
        extras = getIntent().getExtras();

        uid = (String) extras.get("uid");
        venueName = (String) extras.get("venueName");
        foodVenueType = (FoodVenueType) extras.get("foodVenueType");
        // get current user critic status
        DatabaseReference critic = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("critic");

        System.out.println(critic.getKey());

        setupLayout();
        buildRecyclerView();
        getReviewsList();

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReviewsListActivity.this, AddReviewActivity.class);
                i.putExtra("uid", uid);
                i.putExtra("foodVenueType", foodVenueType);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        addReviewMenuItem = menu.findItem(R.id.add_review_menu_item);
        if(user != null){

        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setupLayout(){
        pageTitle           = findViewById(R.id.tvRLA_pageTitle);
        reviewsAverage      = findViewById(R.id.tvRLA_reviewsAverage);
        reviewsNumber       = findViewById(R.id.tvRLA_reviewsNumber);
        ratingBar           = findViewById(R.id.rbRLA_ratingBar);
        addReviewBtn        = findViewById(R.id.btnRLA_addReview);

        String pageTitleValue           = "Reviews for " + venueName;
        String reviewsAverageValue      = "2.0"; //TODO:
        int reviewsNumberValue          = 27;// TODO: get the number of reviews for this restaurant

        pageTitle.setText(pageTitleValue);
        reviewsAverage.setText(reviewsAverageValue);
        reviewsNumber.setText(String.valueOf(reviewsNumberValue));
        ratingBar.setRating(Float.parseFloat(reviewsAverageValue));

        mRecyclerView = findViewById(R.id.rvRLA_recyclerView);

    }

    public void getReviewsList(){
        reviewsList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("reviews").child(foodVenueType.getLabel()).child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewsList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String username                     = snapshot.child("username").getValue(String.class);
                    String title                        = snapshot.child("title").getValue(String.class);
                    String content                      = snapshot.child("content").getValue(String.class);
                    String rating                       = snapshot.child("rating").getValue(Long.class).toString();

                    Review review = new Review(
                            username,
                            title,
                            content,
                            Integer.parseInt(rating)
                    );
                    reviewsList.add(review);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReviewsListActivity.this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buildRecyclerView(){
        // grab visual element
        mRecyclerView = findViewById(R.id.rvRLA_recyclerView);
        // set the dimension of the recycler view as fixed so it doesn't expand if more addFoodVenueMenuItems are in the adaptor list
        mRecyclerView.setHasFixedSize(true);
        // Using a general LinearLayout in order to display items in a list
        mLayoutManager = new LinearLayoutManager(this);
        // creating the adapter for the RecyclerView
        mAdapter = new ReviewsListAdapter(reviewsList);

        // set the RecyclerAdaptor and LayoutManager
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}