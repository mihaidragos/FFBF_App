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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ffbfapp.model.Cuisine;
import com.example.ffbfapp.model.FoodVenue;
import com.example.ffbfapp.model.FoodVenueType;
import com.example.ffbfapp.model.PriceTag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

public class AddFoodVenueActivity extends AppCompatActivity {

    private EditText uid, nameInput, descriptionInput, emailInput, streetInput, cityInput, countyInput, postcodeInput, contactNoInput, venueType;
    private TextView pageTitle;
    private Spinner ratingInputSpinner, priceTagInputSpinner;
    private Button submitButton;
    private ImageButton chooseImage;
    private ProgressBar progressBar;
    private DatabaseReference mDatabaseRef,mAddressRef;
    private StorageReference mStorageRef;

    private Bundle extras;
    private String  foodVenueID,
                    name,
                    description,
                    street,
                    city,
                    county,
                    postcode,
                    contactNo,
                    emailAddress,
                    foodMenuReference,
                    reservationsReference,
                    reviewsListReference,
                    imageResourceReference,
                    passedFoodVenueType;
    private double rating;

    Random rand = new Random(); //instance of random class
    int upperbound = 100;
    //generate random values from 0-24
//    private final int REQUEST = rand.nextInt(upperbound);
    private final int REQUEST = 1;

    private Uri mImageUri;
    private HashMap<String,String> address;
    private FoodVenue foodVenue;

    // Enums
    private FoodVenueType foodVenueType;
    private PriceTag priceTagValue;
    private Cuisine cuisine;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_venue);
        // Get the type of Food Venue in order to display the right text and save to the right child in the DB
        extras = getIntent().getExtras();
        foodVenueType = (FoodVenueType) extras.get("foodVenueType");

        setupLayout();

        // setting the database reference depending on the Food Venue Type (Street Food vs Restaurant)
        mDatabaseRef            = FirebaseDatabase.getInstance().getReference(foodVenueType.getLabel());
        mAddressRef             = FirebaseDatabase.getInstance().getReference(foodVenueType.getLabel() + "_address");
        mStorageRef             = FirebaseStorage.getInstance().getReference(foodVenueType.getLabel());


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // add click listener on the button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputsValid()){
                    progressBar.setVisibility(View.VISIBLE);
                    foodVenueID = mDatabaseRef.push().getKey();
                    uploadImageAndData();
//                    final StorageReference filePath = mStorageRef.child(foodVenueType.getLabel()).child(uid);

// Tendai's version

//===============================================
//                    submitButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("_person_");
//                            String pk = dbref.push().getKey();
//                            StorageReference reference = sref.child(pk + "." +getExtension(image_path));
//                            reference.putFile(image_path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            String url = uri.toString();
//                                            Intent i= new Intent(Upload.this, MainActivity.class);
//                                            i.putExtra("PK",pk);
//                                            i.putExtra("Url", url);
//                                            startActivity(i);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            reference.delete();
//                                        }
//                                    });
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                }
//                            });
//                        }
//                    });


//============================================
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            imageResourceReference = mImageUri.toString().trim();
            int height = chooseImage.getHeight();
            int width = chooseImage.getWidth();
            Picasso.get().load(this.mImageUri).resize(width, height).into(chooseImage);
        }
    }

    private void openFileChooser(){
        Intent i = new Intent();

        i.setType("image/*"); // only show images in the File Chooser
        i.hasCategory(Intent.CATEGORY_OPENABLE);
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, REQUEST);
    }

    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private boolean inputsValid(){
        name = nameInput.getText().toString().trim();
        description = descriptionInput.getText().toString().trim();
        street = streetInput.getText().toString().trim();
        city = cityInput.getText().toString().trim();
        county = countyInput.getText().toString().trim();
        postcode = postcodeInput.getText().toString().trim();
        contactNo = contactNoInput.getText().toString().trim();
        emailAddress = emailInput.getText().toString().trim();
        rating = Double.parseDouble(ratingInputSpinner.getSelectedItem().toString());
        priceTagValue = (PriceTag) priceTagInputSpinner.getSelectedItem();

//        foodVenueType = (FoodVenueType) foodVenueType;

        address = new HashMap<>();
        address.put("street", street);
        address.put("city", city);
        address.put("county", county);
        address.put("postcode", postcode);
        address.put("contactNo", contactNo);
        address.put("emailAddress", emailAddress);

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

//        if(street2.length() < 3){
//            streetInputTwo.setError("A Street address is required and should have at least 3 characters");
//            streetInputTwo.requestFocus();
//            return false;
//        }

        if(city.length() < 3){
            cityInput.setError("The city is required and should have at least 3 characters");
            cityInput.requestFocus();
            return false;
        }

//         if(county.length() < 3){
//            countyInput.setError("The county is required and should have at least 3 characters");
//            countyInput.requestFocus();
//            return false;
//        }

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
            ratingInputSpinner.setPrompt("Please choose a rating");
            ratingInputSpinner.requestFocus();
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

    private void setupLayout(){
        String titleText = "Add New " + foodVenueType.getLabel();
        String buttonText = "Add " + foodVenueType.getLabel();

        // grab the XML elements
        pageTitle               = findViewById(R.id.tvAddStreetFoodPageTitle);
        nameInput               = findViewById(R.id.etAFV_inputName);
        descriptionInput        = findViewById(R.id.etAFV_inputDescription);
        streetInput             = findViewById(R.id.etAFV_inputStreet);
        cityInput               = findViewById(R.id.etAFV_inputCity);
        countyInput             = findViewById(R.id.etAFV_inputCounty);
        postcodeInput           = findViewById(R.id.etAFV_inputPostcode);
        contactNoInput          = findViewById(R.id.etAFV_inputPhone);
        emailInput              = findViewById(R.id.etAFV_inputEmail);
        ratingInputSpinner      = findViewById(R.id.etAFV_inputRatingSpinner);
        priceTagInputSpinner    = findViewById(R.id.etAFV_inputPriceTagSpinner);
//        cuisineInput            = findViewById(R.id.etAFV_inputCuisine);
        chooseImage = findViewById(R.id.pickImageButton);
        submitButton            = findViewById(R.id.btnAddStreetFoodSubmit);
        progressBar             = findViewById(R.id.pbAddStreetFood);

        // Set the values in PriceTag ENUM to the spinner through an Adapter
        priceTagInputSpinner.setAdapter(new ArrayAdapter<PriceTag>(this,R.layout.support_simple_spinner_dropdown_item,PriceTag.values()));

        // Change the text on page depending on what type of FoodVenue is added
        pageTitle.setText(titleText);
        submitButton.setText(buttonText);
    }

    private void uploadImageAndData(){
        if(mImageUri != null){
            StorageReference reference = mStorageRef.child(foodVenueID + "." + getExt(mImageUri));
            reference.putFile(mImageUri)
                     .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageResourceReference = uri.toString();
                                    foodVenue = new FoodVenue(
                                            foodVenueID,
                                            imageResourceReference,
                                            rating,
                                            name,
                                            description,
                                            "reviewsListReference as a child of Reviews",
                                            "foodMenuReference as a FirebaseStorage",
                                            "reservationsReference",
                                            address,
                                            foodVenueType,
                                            Cuisine.MEDITERRANEAN,
                                            priceTagValue
                                    );
                                    uploadData(foodVenueID, foodVenue);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddFoodVenueActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                     })
                     .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddFoodVenueActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                     });



//            ======================================================

//                mStorageRef.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                    @Override
//                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
//                    {
//                        if (!task.isSuccessful())
//                        {
//                            throw task.getException();
//                        }
//                        return mStorageRef.getDownloadUrl();
//                    }
//                })
//                    .addOnCompleteListener(new OnCompleteListener<Uri>(){
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task)
//                    {
//                        if (task.isSuccessful())
//                        {
//                            mImageUri = task.getResult();
////                          // set the
//                            imageResourceReference = mImageUri.toString();
//
//
//                            Upload upload = new Upload(nameInput.getText().toString().trim(),
//                                    mImageUri.toString());
//
//                            mDatabaseRef.child(foodVenueID).setValue(upload);
//                        } else
//                        {
//                            Toast.makeText(AddFoodVenueActivity.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });



//            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getExt(mImageUri));
//
//            fileReference.putFile(mImageUri)
//                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        Task<Uri> result = task.getResult().getUploadSessionUri()
//                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    progressBar.setProgress(0);
//                                }
//                            }, 3000);
//
//                            Toast.makeText(AddFoodVenueActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
//                            Upload upload = new Upload(nameInput.getText().toString().trim(), task.getMetadata().getReference().getDownloadUrl().toString());
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(AddFoodVenueActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                            progressBar.setProgress((int) progress);
//                        }
//                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }
    }

    private void uploadData(String id, FoodVenue fv){
        mDatabaseRef.child(id).setValue(fv)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(AddFoodVenueActivity.this, FoodVenueListActivity.class);
                            i.putExtra("foodVenueType", foodVenueType);
                            startActivity(i);
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