package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddItemFragment extends ActionBarFragment {
    public static final String ARG_LOCATION = "location";

    private TextInputEditText mShort;
    private TextInputEditText mFull;
    private TextInputEditText mValue;
    private Spinner mCategory;
    private Location mLocation;

    private DatabaseReference mDatabase;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param location The location
     * @return A new instance of fragment AddItemFragment.
     */
    public static AddItemFragment newInstance(Location location) {
        AddItemFragment fragment = new AddItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLocation = (Location) getArguments().getSerializable(ARG_LOCATION);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);

        mShort = (TextInputEditText) rootView.findViewById(R.id.short_description);
        mFull = (TextInputEditText) rootView.findViewById(R.id.full_description);
        mValue = (TextInputEditText) rootView.findViewById(R.id.value);
        mCategory = (Spinner) rootView.findViewById(R.id.category);

        Button submit = (Button) rootView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });

        setActionBarTitle(mLocation.getName());
        setActionBarSubtitle("Add New Item");

        return rootView;
    }

    public void validateForm() {
        //validates the inputs in the form
        mShort.setError(null);
        mFull.setError(null);
        mValue.setError(null);

        boolean cancel = false;
        View focusView = null;

        String shortDescription = mShort.getText().toString();
        String fullDescription = mFull.getText().toString();
        double value = 0;

        //check for valid inputs
        if (TextUtils.isEmpty(shortDescription)) {
            mShort.setError("This field is required.");
            cancel = true;
            focusView = mShort;
        }

        if (TextUtils.isEmpty(fullDescription)) {
            mFull.setError("This field is required.");
            cancel = true;
            focusView = mFull;
        }

        if (TextUtils.isEmpty(mValue.getText().toString())) {
            mValue.setError("This field is required.");
            cancel = true;
            focusView = mValue;
        }

        try {
            value = Double.parseDouble(mValue.getText().toString());
        } catch (NumberFormatException e) {
            mValue.setError("The value must be a number.");
            cancel = true;
            focusView = mValue;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            addNewItem();
            Toast.makeText(getContext(), "New item added!",
                    Toast.LENGTH_SHORT).show();

            if (getFragmentManager() != null && getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            }
        }
    }

    /**
     * creates new item and adds to database
     */
    private void addNewItem() {
        String shortDescription = mShort.getText().toString();
        String fullDescription = mFull.getText().toString();
        double value = Double.parseDouble(mValue.getText().toString());
        String category = mCategory.getSelectedItem().toString();
        SimpleDateFormat dateFormat;

        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            String currentDate = dateFormat.format(new Date());

            //add new item to database
            Item item = new Item(currentDate, shortDescription, fullDescription, value, category);

            //updates info for location
            //adds new item to location's sub-database of items
            mDatabase.child("locations").child(mLocation.getName()).child("inventory").child(shortDescription).setValue(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
