package com.example.ffbfapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.ffbfapp.model.FoodVenue;
import com.example.ffbfapp.model.FoodVenueType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private ListView restaurantsList;
    private Button loginBtn, registerBtn,logoutBtn,myAccountBtn;
    private String name, description, street, city, county, postcode, contactNo,review;
    private ImageView streetFoodImage, restaurantImage;
    private float rating;
    private Intent i;

    DatabaseReference mRef;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the current logged in user
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        // check if user is logged in and if it's not logged in then we send him to the login activity
        if(mUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        i = new Intent(this, AdminPanelActivity.class);
        startActivity(i);

        // getting the views references from this acivity layout
        loginBtn = findViewById(R.id.btnMA_loginBtn);
        registerBtn = findViewById(R.id.btnMA_registerBtn);
        logoutBtn       = findViewById(R.id.logoutBtn);
        myAccountBtn    = findViewById(R.id.myAccountBtn);
        streetFoodImage = findViewById(R.id.ivMA_streetFoodImage);
        restaurantImage = findViewById(R.id.ivMA_restaurantsImage);

        // create click listener for each button in order for _this_ object (the MainActivity class) to be passed to the onClick method below
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        myAccountBtn.setOnClickListener(this);
        streetFoodImage.setOnClickListener(this);
        restaurantImage.setOnClickListener(this);

        // hide  [My Account] and [Log Out] buttons if user is not logged in
        if(mUser != null){
            loginBtn.setVisibility(View.GONE);
            registerBtn.setVisibility(View.GONE);
        } else {
            myAccountBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.GONE);
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            }
        };


//        ArrayList<String> restaurants = new ArrayList<>();
//
//        // This adapter will feed the data from our array to the ListView
//        ArrayAdapter<String> restaurantsAdapter = new ArrayAdapter<>(
//                // passing this / MainActivity class as the context. All the activities are contexts themselves
//                this,
//                // here a layout needs to be created
//                //TODO: Create layout for each list item
//                android.R.layout.simple_list_item_1,
//                //
//                restaurants
//        );

//        restaurantsList.setAdapter(restaurantsAdapter);

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

//        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                restaurants.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
////                    FoodVenue restaurant = snapshot.getValue(FoodVenue.class);
//                    String restaurant = snapshot.child("name").getValue(String.class);
////                    restaurant.getName();
//                    restaurants.add(restaurant);
//                }
//                restaurantsAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//        restaurantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, restaurants.get(position) + " Selected", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    // Button functionality
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMA_loginBtn:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.btnMA_registerBtn:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.logoutBtn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.myAccountBtn:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                break;
            case R.id.ivMA_restaurantsImage:
                i = new Intent(MainActivity.this, FoodVenueListActivity.class);
                i.putExtra("foodVenueType", FoodVenueType.R);
                startActivity(i);
                break;
            case R.id.ivMA_streetFoodImage:
                i = new Intent(MainActivity.this, FoodVenueListActivity.class);
                i.putExtra("foodVenueType", FoodVenueType.SF);
                startActivity(i);
                break;
            default:
                break;

        }
    }


    // function to add FoodVenues to Firebase
    public void addFoodVenue(FoodVenue foodVenue, DatabaseReference reference) {
        String uid = reference.push().getKey();
        foodVenue.setUid(uid);
        reference.child(uid).setValue(foodVenue);
    }
}
