package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AssignCriticActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText searchTextBox;
    private Button searchUsersButton;
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    private String searchText;
    private LinearLayoutManager mLayoutManager;
    private UsersRecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_critic);
        mRef = FirebaseDatabase.getInstance().getReference();

        ArrayList<SingleUserItem> searchResultsList = new ArrayList<>();
        //grab the visual elements
        searchTextBox = findViewById(R.id.etAC_searchBox);
        searchUsersButton = findViewById(R.id.btnAC_search);
        recyclerView = findViewById(R.id.lvAC_list);

        // setup the RecyclerView and pass the data to the Adapter
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new UsersRecyclerAdapter(searchResultsList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        searchUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchTextBox.getText().toString().trim();

                mRef.child("Users")
                    .orderByChild("email")
                    .startAt(searchText)
                    .endAt(searchText+"\uf8ff")
                    .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        searchResultsList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            SingleUserItem user = new SingleUserItem();
                            String uid = dataSnapshot.getKey().toString();
                            String name = dataSnapshot.child("name").getValue().toString();
                            String lastName = dataSnapshot.child("lastName").getValue().toString();
                            String email = dataSnapshot.child("email").getValue().toString();
                            boolean critic = (boolean) dataSnapshot.child("critic").getValue();
                            String fullName = name + " " + lastName;

                            user.setUserEmail(email);
                            user.setUserName(fullName);
                            user.setUid(uid);
                            user.setCritic(critic);

                            Toast.makeText(AssignCriticActivity.this,uid, Toast.LENGTH_LONG).show();
                            searchResultsList.add(user);
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //TODO:
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}