package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffbfapp.model.Cuisine;
import com.example.ffbfapp.model.FoodVenue;
import com.example.ffbfapp.model.FoodVenueType;
import com.example.ffbfapp.model.PriceTag;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


// in order to be more efficient in code we have implemented the View.OnClickListener interface and assigned each button/element a click listener to which `this` (the FoodVenueListActivity class that is) has been passed. An onClick method from the interface needed to be overridden and inside this method we used a `switch` statement which will identify what action to fire by the `R.id` of the item that is clicked and has a click listener.
public class FoodVenueListActivity extends AppCompatActivity implements View.OnClickListener {
    // grab the XML elements
    private Button insertBtn, removeBtn, addStreetFoodBtn;
    private EditText etInsert, etRemove;
    private TextView tvPageTitle;
    // create an ArrayList variable that can be accessed outside of the onCreate method
    ArrayList<FoodVenue> foodVenueArrayList;
    // this will contain recreated RecyclerView
    private RecyclerView mRecyclerView;
    // the adapter is the bridge between our data and our RecyclerView (it basically feeds data into the RecyclerView only as many as needed to be displayed to the user)
    private FoodVenuesListRecyclerAdapter mAdapter;
    // the LayoutManager will be responsible with aligning the items in the list
    // We will use a LinearLayout
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem addFoodVenueMenuItem;

    private FirebaseUser user;
    private FoodVenueType foodVenueType;
    private Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_venue_list);
        extras = getIntent().getExtras();
        foodVenueType = (FoodVenueType) extras.get("foodVenueType");


        // get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        addFoodVenueMenuItem = findViewById(R.id.add_food_venue_menu_item);
        invalidateOptionsMenu();
        setPageContentText();
        getFoodVenueList();
        buildRecyclerView();
        setButtons();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        addFoodVenueMenuItem = menu.findItem(R.id.add_food_venue_menu_item);
        if(user != null){
            addFoodVenueMenuItem.setTitle("Add New " + foodVenueType.getLabel());
        } else {
            invalidateOptionsMenu();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void getFoodVenueList(){
        foodVenueArrayList = new ArrayList<>();
//        foodVenueArrayList = buildTestList();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(foodVenueType.getLabel());
        reference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodVenueArrayList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uid                          = snapshot.child("uid").getValue(String.class);
                    String imageResourceReference       = snapshot.child("imageResourceReference").getValue(String.class);
                    String name                         = snapshot.child("name").getValue(String.class);
                    String description                  = snapshot.child("description").getValue(String.class);
                    HashMap<String,String> address      = new HashMap<String, String>();
                            address.put("street", snapshot.child("address").child("street").getValue(String.class));
                            address.put("city", snapshot.child("address").child("city").getValue(String.class));
                            address.put("county", snapshot.child("address").child("county").getValue(String.class));
                            address.put("postcode", snapshot.child("address").child("postcode").getValue(String.class));
                            address.put("contactNo", snapshot.child("address").child("contactNo").getValue(String.class));
                            address.put("emailAddress", snapshot.child("address").child("emailAddress").getValue(String.class));

                    FoodVenueType foodVenueType         = snapshot.child("foodVenueType").getValue(FoodVenueType.class);
                    Cuisine cuisine                     = snapshot.child("cuisine").getValue(Cuisine.class);
                    PriceTag priceTagValue              = snapshot.child("priceTagValue").getValue(PriceTag.class);
                    String foodMenuReference            = snapshot.child("foodMenuReference").getValue(String.class);
                    int rating                          = snapshot.child("rating").getValue(Integer.class);

                    System.out.println(address.toString());
                    //TODO:
//                    int rating = snapshot.child("rating").getValue(Integer.class);

                    FoodVenue foodVenue = new FoodVenue(
                            uid,
                            imageResourceReference,
                            rating,
                            name,
                            description,
                            "The reviewsListReference",
                            foodMenuReference,
                            "The reservationsReference",
                            address,
                            foodVenueType,
                            cuisine,
                            priceTagValue
                                );
                    foodVenueArrayList.add(foodVenue);
                    System.out.println("lista de restaurante este: " + foodVenueArrayList);
                }
//                Collections.sort(restaurantsList);
                System.out.println(foodVenueArrayList.toString());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO:
                Toast.makeText(FoodVenueListActivity.this, "ceva nu a mers. mai incearca", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buildRecyclerView(){
        // grab visual element
        mRecyclerView = findViewById(R.id.recyclerView);
        // set the dimension of the recycler view as fixed so it doesn't expand if more addFoodVenueMenuItems are in the adaptor list
        mRecyclerView.setHasFixedSize(true);
        // Using a general LinearLayout in order to display items in a list
        mLayoutManager = new LinearLayoutManager(this);
        // creating the adapter for the RecyclerView
        mAdapter = new FoodVenuesListRecyclerAdapter(foodVenueArrayList);

        // set the RecyclerAdaptor and LayoutManager
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FoodVenuesListRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                openItemDetailActivity(position, "CLICKED");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void setButtons(){
        // grab the visual elements
        addStreetFoodBtn = findViewById(R.id.btn_add_street_food);
        // link the views with the OnClick interface by passing the listener object on click method
        addStreetFoodBtn.setOnClickListener(this);
    }

    public void removeItem(int position){
        foodVenueArrayList.remove(position);
        // Notify the adapter that changes have been made
        mAdapter.notifyItemRemoved(position);
    }

    public void openItemDetailActivity(int position, String text){
        Intent i = new Intent(FoodVenueListActivity.this, FoodVenueDetailActivity.class);

        FoodVenue foodVenue = foodVenueArrayList.get(position);
        String uid = foodVenue.getUid();
        String name = foodVenue.getName();
        String description = foodVenue.getDescription();
        HashMap<String,String> address = foodVenue.getAddress();
        FoodVenueType foodVenueType = foodVenue.getFoodVenueType();
        String imageResourceReference = foodVenue.getImageResourceReference();
        double rating = foodVenue.getRating();

        i.putExtra("uid", uid);
        i.putExtra("name", name);
        i.putExtra("description", description);
        i.putExtra("street", address.get("street"));
        i.putExtra("city", address.get("city"));
        i.putExtra("county", address.get("county"));
        i.putExtra("postcode", address.get("postcode"));
        i.putExtra("contactNo", address.get("contactNo"));
        i.putExtra("emailAddress", address.get("emailAddress"));
        i.putExtra("foodVenueType", foodVenueType);
        i.putExtra("imageResourceReference", imageResourceReference);
        i.putExtra("rating", rating);

        startActivity(i);
    }

    public void setPageContentText(){
        if(foodVenueType != null){
            tvPageTitle = findViewById(R.id.tvFVL_foodVenueListTitle);
            tvPageTitle.setText(foodVenueType.getLabel() + " Venues");
        } else {
            System.out.println("foodVenueType e null");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in order to see the restaurants in the filtered list we need to inflate the menu so that it covers the list
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurants_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.restaurants_list_bar_search);
        MenuItem addFoodVenueItem = menu.findItem(R.id.add_food_venue_menu_item);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        addFoodVenueItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(FoodVenueListActivity.this,AddFoodVenueActivity.class);
                i.putExtra("foodVenueType", foodVenueType);
                startActivity(i);
                return false;
            }
        });
        return true;
    }

    /* A future version of the Android Gradle Plugin will generate R classes with non-constant IDs in order to improve
    the performance of incremental compilation.
    So I used the @SuppressLint("NonConstantResourceId") annotation to ignore the linter warning */
    @SuppressLint(value = "NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()){
            case R.id.btn_add_street_food:
                Intent i = new Intent(FoodVenueListActivity.this, AddFoodVenueActivity.class);
                i.putExtra("foodVenueType", FoodVenueType.R);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}