package com.example.ffbfapp.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.ffbfapp.R;

import java.text.DateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // We get the current date from Calendar to pass it as the default date to be shown
        final Calendar c = Calendar.getInstance();
        int year    = c.get(Calendar.YEAR);
        int month   = c.get(Calendar.MONTH);
        int day     = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(
            getActivity(),      // The context, which is our activity
            (DatePickerDialog.OnDateSetListener) getActivity(), // A listener. In this case the listener needs to send information to FoodVenueDetailActivity so instead of passing *this* (aka DatePickerDialog)
            year, month, day    // Passing the initial date that the picker will show
        );
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // This is overridden in FoodVenueDetailActivity
    }
}

