package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPanelActivity extends AppCompatActivity implements View.OnClickListener {
    private Button addRestaurantBtn, assignCriticRoleBtn;
    private ListView criticsListView;

    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        assignCriticRoleBtn = findViewById(R.id.btnAP_assignNewCritic);
        addRestaurantBtn = findViewById(R.id.btnAP_addRestaurant);
        criticsListView = findViewById(R.id.lvAP_criticsList);

        // assign click listeners to buttons on page
        assignCriticRoleBtn.setOnClickListener(this);
        addRestaurantBtn.setOnClickListener(this);


        // get DB reference
        mRef = FirebaseDatabase.getInstance().getReference();

        // empty array to keep the names of the current critics pulled from DB
        ArrayList<String> critics = new ArrayList<>();

        // This adapter will feed the data from our array to the ListView
        ArrayAdapter<String> criticsAdapter = new ArrayAdapter<>(
                // passing this / MainActivity class as the context. All the activities are contexts themselves
                this,
                // here a layout needs to be created
                //TODO: Create layout for each list item
                android.R.layout.simple_list_item_1,
                //
                critics
        );

        criticsListView.setAdapter(criticsAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                critics.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    FoodVenue restaurant = snapshot.getValue(FoodVenue.class);
                    boolean isCritic = snapshot.child("critic").getValue(Boolean.class);
                    String criticName = snapshot.child("name").getValue(String.class);
                    String criticLastName = snapshot.child("lastName").getValue(String.class);
                    criticName = criticName + " " + criticLastName;
                    if(isCritic){
                        critics.add(criticName);
                    }
                }
                criticsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAP_assignNewCritic:
                //TODO:
                startActivity(new Intent(AdminPanelActivity.this, AssignCriticActivity.class));
                break;
            case R.id.btnAP_addRestaurant:
                //TODO:
                break;

            default:
                break;
        }
    }
}