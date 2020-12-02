package com.example.ffbfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ffbfapp.model.DatePickerFragment;
import com.example.ffbfapp.model.FoodVenueType;
import com.example.ffbfapp.model.TimePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class FoodVenueDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView tvRD_reservationChooseDate;
    private TextView tvRD_reservationChooseTime;
    private TextView tvRD_reviewsNumber;
    private TextView tvRD_foodSpecificText;
    private TextView tvRD_priceTagValue;
    private TextView tvRD_descriptionText;
    private TextView tvRD_chooseDateTime;
    private ImageView ivRD_mainImage;
    private TextView tvRD_restaurantName;
    private TextView tvRD_reviewsRatingNumber;
    private RatingBar rbRD_ratingBar;
    private Spinner sRD_reservationPartySizeSpinner;
    private Button btnRD_firstQuarterTimeslot;
    private Button btnRD_secondQuarterTimeslot;
    private Button btnRD_thirdQuarterTimeslot;
    private Button btnRD_fourthQuarterTimeslot;
    private TableRow trRD_buttonsRow;

    private DialogFragment newFragment;
    // Define variables
    private ObjectAnimator animation;
    private boolean maxLines = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        Bundle extras = getIntent().getExtras();

        displayFoodVenueDetails(extras);

//        etRD_reservationChooseDate
//        etRD_reservationChooseTime
//        sRD_reservationPartySizeSpinner
//        btnRD_firstQuarterTimeslot.setVisibility(View.GONE);
//        btnRD_secondQuarterTimeslot.setVisibility(View.GONE);
//        btnRD_thirdQuarterTimeslot.setVisibility(View.GONE);
//        btnRD_fourthQuarterTimeslot.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void displayFoodVenueDetails(Bundle extras) {
        // Grab the extras
        String uid = extras.getString("uid");
        String name = extras.getString("name");
        String description = extras.getString("description");
        String street = extras.getString("street");
        String city = extras.getString("city");
        String county = extras.getString("county");
        String postcode = extras.getString("postcode");
        String contactNo = extras.getString("contactNo");
        FoodVenueType foodVenueType = (FoodVenueType) extras.get("foodVenueType");
        int imageResourceReference = extras.getInt("imageResourceReference");
        double rating = extras.getDouble("rating");

        // Get the visual elements
        ivRD_mainImage = findViewById(R.id.ivRD_mainImage);
        tvRD_restaurantName = findViewById(R.id.tvRD_restaurantName);
        tvRD_reviewsRatingNumber = findViewById(R.id.tvRD_reviewsRatingNumber);
        tvRD_reviewsNumber = findViewById(R.id.tvRD_reviewsNumber);
        tvRD_foodSpecificText = findViewById(R.id.tvRD_foodSpecificText);
        tvRD_priceTagValue = findViewById(R.id.tvRD_priceTagValue);
        tvRD_reservationChooseDate = findViewById(R.id.tvRD_reservationChooseDate);
        tvRD_reservationChooseTime = findViewById(R.id.tvRD_reservationChooseTime);
        rbRD_ratingBar = findViewById(R.id.rbRD_ratingBar);
        sRD_reservationPartySizeSpinner = findViewById(R.id.sRD_reservationPartySizeSpinner);
        btnRD_firstQuarterTimeslot = findViewById(R.id.btnRD_firstQuarterTimeslot);
        btnRD_secondQuarterTimeslot = findViewById(R.id.btnRD_secondQuarterTimeslot);
        btnRD_thirdQuarterTimeslot = findViewById(R.id.btnRD_thirdQuarterTimeslot);
        btnRD_fourthQuarterTimeslot = findViewById(R.id.btnRD_fourthQuarterTimeslot);
        tvRD_descriptionText = findViewById(R.id.tvRD_descriptionText);
        trRD_buttonsRow = findViewById(R.id.trRD_buttonsRow);
        tvRD_chooseDateTime = findViewById(R.id.tvRD_chooseDateTime);

        // Replace the template elements
        tvRD_restaurantName.setText(name);
        tvRD_reviewsRatingNumber.setText(Double.toString(rating));
        tvRD_descriptionText.setText(description);
        rbRD_ratingBar.setNumStars((int) rating);

        // Set onClickListener to the date and time pickers
        tvRD_reservationChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "datePicker");
            }
        });

        tvRD_reservationChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new TimePickerFragment();
                datePicker.show(getSupportFragmentManager(), "timePicker");
            }
        });

        // TODO: Add animation for the description [Read More] button
        tvRD_descriptionText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ObjectAnimatorBinding")
            @Override
            public void onClick(View v) {
                int lines = 25;
                if(!maxLines){
                    lines = 2;
                    maxLines = true;
                } else {
                    maxLines = false;
                }

                animation = ObjectAnimator.ofInt(tvRD_descriptionText, "maxLines", lines);
                animation.setDuration(350).start();
            }
        });

        //TODO:
//        tvRD_reviewsNumber.setText();
//        tvRD_foodSpecificText.
//        tvRD_priceTagValue
    }


    // Implementation of onDateSet and onTimeSet as the interfaces dictate
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());

        tvRD_reservationChooseDate.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String currentTime = hourOfDay + ":" + minute;
        tvRD_reservationChooseTime.setText(currentTime);
    }


    private void expandTextView(TextView tv){
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLines", tv.getLineCount());
        animation.setDuration(200).start();
    }

    private void collapseTextView(TextView tv, int numChars){
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animation = ObjectAnimator.ofInt(tv, "maxLength", numChars);
        animation.setDuration(200).start();
    }

}