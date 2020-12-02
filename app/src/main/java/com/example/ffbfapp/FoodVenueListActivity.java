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
import android.widget.Toast;

import com.example.ffbfapp.model.Address;
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

    private ArrayList<String> listaNume = new ArrayList<>();
    private HashMap<String,String> address;
    FirebaseUser user;
    FoodVenueType foodVenueType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_venue_list);
        Bundle extras = getIntent().getExtras();
        foodVenueType = (FoodVenueType) extras.get("foodVenueType");

        // get current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        addFoodVenueMenuItem = findViewById(R.id.add_food_venue_menu_item);
        invalidateOptionsMenu();

        getFoodVenueList();
        buildRecyclerView();
        setButtons();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        addFoodVenueMenuItem = menu.findItem(R.id.add_food_venue_menu_item);
        addFoodVenueMenuItem.setTitle("Add New " + foodVenueType.getLabel());

        return super.onPrepareOptionsMenu(menu);
    }

    public void getFoodVenueList(){
        foodVenueArrayList = new ArrayList<>();
//        foodVenueArrayList = buildTestList();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Restaurant");
        reference.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodVenueArrayList.clear();
                for (int i = 0; i < 10; i++) {
                    System.out.println("teste");
                }
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String uid                      = snapshot.child("uid").getValue(String.class);
                    String imageResourceReference   = snapshot.child("imageResourceReference").getValue(String.class);
                    String name                     = snapshot.child("name").getValue(String.class);
                    String description              = snapshot.child("description").getValue(String.class);
                    Address addressClass            = snapshot.child("address").getValue(Address.class);
                    FoodVenueType foodVenueType     = snapshot.child("foodVenueType").getValue(FoodVenueType.class);
                    Cuisine cuisine                 =  snapshot.child("cuisine").getValue(Cuisine.class);
                    PriceTag priceTagValue          = snapshot.child("priceTagValue").getValue(PriceTag.class);
                    String foodMenuReference        = snapshot.child("foodMenuReference").getValue(String.class);
                    int rating                      = snapshot.child("rating").getValue(Integer.class);

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
                    listaNume.add(name);
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

//    public ArrayList<FoodVenue> buildTestList(){
//        foodVenueArrayList = new ArrayList<>();
//        String uid                      = "xcdfvghjkl";
//        String imageResourceReference   = "testing";
//        String name                     = "TEsting 123";
//        String description              = "O descriere mai lunga de 150 de caractere fghdjawlkhawkjfhak fqfefef fef ef ef ef ef ef ef ef f e fe ffe";
//        Address addressClass            = new Address(
//                "Strada strazilor",
//                "city",
//                "county",
//                "postcode",
//                "contactNo",
//                "emailAddress"
//        );
//        FoodVenueType foodVenueType     = FoodVenueType.R;
//        Cuisine cuisine                 = Cuisine.MEDITERRANEAN;
//        PriceTag priceTagValue          = PriceTag.££;
//        String foodMenuReference        = "no menu for now";
//        int rating = 4;
//        address = new Address().addressToHashMap(addressClass);
//
//        FoodVenue foodVenue = new FoodVenue(
//                uid,
//                imageResourceReference,
//                rating,
//                name,
//                description,
//                "The reviewsListReference",
//                foodMenuReference,
//                "The reservationsReference",
//                address,
//                foodVenueType,
//                cuisine,
//                priceTagValue
//        );
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        foodVenueArrayList.add(foodVenue);
//        System.out.println("lista de restaurante este: " + foodVenueArrayList);
//        return foodVenueArrayList;
//    }

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

    public void insertItem(int position){
        // restaurantsList.add(position, new SingleItem(R.drawable.ic_launcher_background, "New item at position " + position, "Thanks for replying, my problem is that i can't add the Fragment to the Activity, the fragmentA contains a textview"));
        // Notify the adapter that changes have been made
        // mAdapter.notifyDataSetChanged(); // this will instantly refresh the list without the chance to add animations
        mAdapter.notifyItemInserted(position); // this will allow for animations and transitions
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
        Address address = Address.hashMapToAddress(foodVenue.getAddressHashmap());
        FoodVenueType foodVenueType = foodVenue.getFoodVenueType();
        String imageResourceReference = foodVenue.getImageResourceReference();
        double rating = foodVenue.getRating();

        i.putExtra("uid", uid);
        i.putExtra("name", name);
        i.putExtra("description", description);
        i.putExtra("street", address.getStreet());
        i.putExtra("city", address.getCity());
        i.putExtra("county", address.getCounty());
        i.putExtra("postcode", address.getPostcode());
        i.putExtra("contactNo", address.getContactNo());
        i.putExtra("emailAddress", address.getEmailAddress());
        i.putExtra("foodVenueType", foodVenueType);
        i.putExtra("imageResourceReference", imageResourceReference);
        i.putExtra("rating", rating);

        startActivity(i);
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
//            case R.id.btn_insert:
//                position = Integer.parseInt(etInsert.getText().toString());
//                insertItem(position);
//                break;
//            case R.id.btn_remove:
//                position = Integer.parseInt(etRemove.getText().toString());
//                removeItem(position);
//                break;
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