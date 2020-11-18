package com.example.ffbfapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ffbfapp.model.FoodVenue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView restaurantsList;
    private Button button,button2,logoutBtn,myAccountBtn,listViewBtn;
    private String name, description, street, city, county, postcode, contactNo,review;
    private float rating;

    DatabaseReference fRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the current logged in user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // getting the views references from this acivity layout
        restaurantsList = findViewById(R.id.restaurantList);
        button          = findViewById(R.id.testingBtn);
        button2         = findViewById(R.id.testingButton);
        logoutBtn       = findViewById(R.id.logoutBtn);
        myAccountBtn    = findViewById(R.id.myAccountBtn);
        listViewBtn     = findViewById(R.id.listViewBtn);

//=================================================================
//=================================================================
//=================================================================
//=================================================================
        startActivity(new Intent(MainActivity.this, AdminPanelActivity.class));


        // create click listener for each button in order for _this_ object (the MainActivity class) to be passed to the onClick method below
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        myAccountBtn.setOnClickListener(this);
        listViewBtn.setOnClickListener(this);

        // hide  [My Account] and [Log Out] buttons if user is not logged in
        if(user == null){
            myAccountBtn.setVisibility(View.GONE);
            logoutBtn.setVisibility(View.GONE);
        }


        ArrayList<String> restaurants = new ArrayList<>();

        // This adapter will feed the data from our array to the ListView
        ArrayAdapter<String> restaurantsAdapter = new ArrayAdapter<>(
                // passing this / MainActivity class as the context. All the activities are contexts themselves
                this,
                // here a layout needs to be created
                //TODO: Create layout for each list item
                android.R.layout.simple_list_item_1,
                //
                restaurants
        );

        restaurantsList.setAdapter(restaurantsAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurants.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    FoodVenue restaurant = snapshot.getValue(FoodVenue.class);
                    String restaurant = snapshot.child("name").getValue(String.class);
//                    restaurant.getName();
                    restaurants.add(restaurant);
                }
                restaurantsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        restaurantsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, restaurants.get(position) + " Selected", Toast.LENGTH_SHORT).show();
            }
        });

    }


    // Button functionality
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.testingBtn:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.testingButton:
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
                break;
            case R.id.logoutBtn:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.myAccountBtn:
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
                break;
            case R.id.listViewBtn:
                startActivity(new Intent(MainActivity.this, RestaurantsListActivity.class));
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
