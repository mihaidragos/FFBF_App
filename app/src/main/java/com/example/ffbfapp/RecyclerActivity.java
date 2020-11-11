package com.example.ffbfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ffbfapp.model.Restaurant;

import java.util.ArrayList;


// in order to be more efficient in code we have implemented the View.OnClickListener interface and assigned each button/element a click listener to which `this` (the RecyclerActivity class that is) has been passed. An onClick method from the interface needed to be overridden and inside this method we used a `switch` statement which will identify what action to fire by the `R.id` of the item that is clicked and has a click listener.
public class RecyclerActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insertBtn, removeBtn;
    private EditText etInsert, etRemove;

    // create a variable that can be accessed outside of the onCreate method
    ArrayList<SingleItem> restaurantsList;

    // this will contain recreated RecyclerView
    private RecyclerView mRecyclerView;
    // the adapter is the bridge between our data and our RecyclerView (it basically feeds data into the RecyclerView only as many as needed to be displayed to the user)
    private RecyclerAdapter mAdapter;
    // the LayoutManager will be responsible with aligning the items in the list
    // We will use a LinearLayout
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        createRestaurantsList();
        buildRecyclerView();
        setButtons();

    }

    public void createRestaurantsList(){
        restaurantsList = new ArrayList<>();
        restaurantsList.add(new SingleItem(R.drawable.ic_launcher_background, "Line1", "Line2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_dark, "sdfdfgh rtre", "Linswertygfrdeswere2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_light, "Lindwadawes1", "LineasderfgtafSC 2 rq etrfa2"));
        restaurantsList.add(new SingleItem(R.drawable.ic_launcher_background, "Line1", "Line2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_dark, "sdfdfgh rtre", "Linswertygfrdeswere2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_light, "Lindwadawes1", "LineasderfgtafSC 2 rq etrfa2"));
        restaurantsList.add(new SingleItem(R.drawable.ic_launcher_background, "Line1", "Line2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_dark, "sdfdfgh rtre", "Linswertygfrdeswere2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_light, "Lindwadawes1", "LineasderfgtafSC 2 rq etrfa2"));
        restaurantsList.add(new SingleItem(R.drawable.ic_launcher_background, "Line1", "Line2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_dark, "sdfdfgh rtre", "Linswertygfrdeswere2"));
        restaurantsList.add(new SingleItem(R.drawable.common_google_signin_btn_icon_light, "Lindwadawes1", "LineasderfgtafSC 2 rq etrfa2"));
    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerAdapter(restaurantsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                changeItemText(position, "CLICKED");
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }

    public void setButtons(){
        // grab the visual elements
        insertBtn   = findViewById(R.id.btn_insert);
        removeBtn   = findViewById(R.id.btn_remove);
        etInsert    = findViewById(R.id.et_insert);
        etRemove    = findViewById(R.id.et_remove);

        //
        insertBtn.setOnClickListener(this);
        removeBtn.setOnClickListener(this);
    }

    public void insertItem(int position){
        restaurantsList.add(position, new SingleItem(R.drawable.ic_launcher_background, "New item at position " + position, "Thanks for replying, my problem is that i can't add the Fragment to the Activity, the fragmentA contains a textview"));
        // Notify the adapter that changes have been made
        // mAdapter.notifyDataSetChanged(); // this will instantly refresh the list without the chance to add animations
        mAdapter.notifyItemInserted(position); // this will allow for animations and transitions
    }

    public void removeItem(int position){
        restaurantsList.remove(position);
        // Notify the adapter that changes have been made
        mAdapter.notifyItemRemoved(position);
    }

    public void changeItemText(int position, String text){
        restaurantsList.get(position).changeTitleText(text);
        mAdapter.notifyItemChanged(position);
    }

    /* A future version of the Android Gradle Plugin will generate R classes with non-constant IDs in order to improve
    the performance of incremental compilation.
    So I used the @SuppressLint("NonConstantResourceId") annotation */
    @SuppressLint(value = "NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()){
            case R.id.btn_insert:
                position = Integer.parseInt(etInsert.getText().toString());
                insertItem(position);
                break;
            case R.id.btn_remove:
                position = Integer.parseInt(etRemove.getText().toString());
                removeItem(position);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in order to see the restaurants in the filtered list we need to inflate the menu so that it covers
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.restaurants_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.restaurants_list_bar_search);
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
        return true;
    }
}