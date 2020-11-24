package com.example.ffbfapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ffbfapp.model.FoodVenue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AddFoodVenueActivity extends AppCompatActivity {

    private EditText uid, nameInput, descriptionInput, streetInput, cityInput, countyInput, postcodeInput, contactNoInput, venueType;
    private Spinner ratingInput;
    private Button submitButton;
    private ImageButton uploadImage;
    private ProgressBar progressBar;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private String name;
    private String description;
    private String street;
    private String city;
    private String county;
    private String postcode;
    private String contactNo;
    private String foodVenueType;
    private Integer imageResourceReference;
    private int rating;
    private final int REQUEST = 1;
    private Uri url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_street_food);

        // get DB reference
        mDatabase = FirebaseDatabase.getInstance();
        mRef      = mDatabase.getReference("Restaurants");

        // grab the XML elements
        nameInput = findViewById(R.id.etAddStreetFoodVenueName);
        descriptionInput = findViewById(R.id.etAddStreetFoodDescription);
        streetInput = findViewById(R.id.etAddStreetFoodAddressStreet);
        cityInput = findViewById(R.id.etAddStreetFoodAddressCity);
        countyInput = findViewById(R.id.etAddStreetFoodAddressCounty);
        postcodeInput = findViewById(R.id.etAddStreetFoodAddressPostcode);
        contactNoInput = findViewById(R.id.etAddStreetFoodPhone);
        ratingInput = findViewById(R.id.ratingSpinner);
        uploadImage = findViewById(R.id.pickImageButton);
        submitButton = findViewById(R.id.btnAddStreetFoodSubmit);
        progressBar = findViewById(R.id.pbAddStreetFood);


        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                // only show images
                i.setType("image/*");
                i.hasCategory(Intent.CATEGORY_OPENABLE);
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, REQUEST);
            }
        });

        // add click listener on the button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputsValid()){
                    progressBar.setVisibility(View.VISIBLE);

                    String uid = mRef.push().getKey();
                    FoodVenue foodVenue = new FoodVenue(uid, rating, name, description, street, city, county, postcode, contactNo, foodVenueType, imageResourceReference);
                    mRef.child(uid).setValue(foodVenue)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(AddFoodVenueActivity.this, RestaurantsListActivity.class));
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(AddFoodVenueActivity.this, "Oops, something went wrong. Please try again", Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST && resultCode == RESULT_OK && data.getData() != null){
            url = data.getData();
            Picasso.get().load(url).into(uploadImage);
        }
    }

    private String getExt(Uri uri){
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

    private boolean inputsValid(){
        name = nameInput.getText().toString().trim();
        description = descriptionInput.getText().toString().trim();
        street = streetInput.getText().toString().trim();
        city = cityInput.getText().toString().trim();
        county = countyInput.getText().toString().trim();
        postcode = postcodeInput.getText().toString().trim();
        contactNo = contactNoInput.getText().toString().trim();
        rating = Integer.parseInt(ratingInput.getSelectedItem().toString());
        String foodVenueType = "R";
        String imageResourceReference = "Un link la imagine";

        if(name.length() < 3){
            nameInput.setError("Name is a required field and should have at least 3 characters");
            nameInput.requestFocus();
            return false;
        }

        if(street.length() < 3){
            streetInput.setError("A Street address is required and should have at least 3 characters");
            streetInput.requestFocus();
            return false;
        }

        if(city.length() < 3){
            cityInput.setError("The city is required and should have at least 3 characters");
            cityInput.requestFocus();
            return false;
        }

         if(county.length() < 3){
            countyInput.setError("The county is required and should have at least 3 characters");
            countyInput.requestFocus();
            return false;
        }

         if(postcode.length() < 5){
            postcodeInput.setError("The postcode is required and should have at least 5 characters");
            postcodeInput.requestFocus();
            return false;
        }

         if(contactNo.length() < 8){
            contactNoInput.setError("The contact number should have at least 8 characters");
            contactNoInput.requestFocus();
            return false;
        }

        if( rating < 0 || rating > 5 ){
            ratingInput.setPrompt("Please choose a rating");
            ratingInput.requestFocus();
            return false;
        }

        if(description.isEmpty()){
            descriptionInput.setError("Description is required");
            descriptionInput.requestFocus();
            return false;
        }

        if(description.length() < 50){
            descriptionInput.setError("The description should have at least 50 characters");
            descriptionInput.requestFocus();
        }

        return true;
    }
}