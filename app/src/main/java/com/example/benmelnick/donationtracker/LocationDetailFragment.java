package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


public class LocationDetailFragment extends ActionBarFragment {
    public static final String ARG_LOCATION = "location";

    private Location mLocation;

    public LocationDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param location the location
     * @return A new instance of fragment LocationDetailFragment.
     */
    public static LocationDetailFragment newInstance(Location location) {
        LocationDetailFragment fragment = new LocationDetailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_location_detail, container, false);

        if (mLocation != null) {
            String coordinates = mLocation.getLongitude() + ", " + mLocation.getLatitude();

            ((TextView) rootView.findViewById(R.id.type)).setText("Type:\n" + mLocation.getType());
            ((TextView) rootView.findViewById(R.id.coordinates)).setText("Coordinates:\n" + coordinates);
            ((TextView) rootView.findViewById(R.id.address)).setText("Address:\n" + mLocation.printFullAddress());
            ((TextView) rootView.findViewById(R.id.phone)).setText("Phone Number:\n" + mLocation.getPhoneNumber());


            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            final Button inventoryBttn = (Button) rootView.findViewById(R.id.view_items);

            String id = mAuth.getCurrentUser().getUid();
            mDatabase.child("users").child(id).child("accountType").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = (String) dataSnapshot.getValue();

                    if (value.equals("Location Employee")) {
                        inventoryBttn.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            inventoryBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(InventoryListFragment.ARG_LOCATION, mLocation);

                    Navigation.findNavController(view).navigate(R.id.action_locationDetailFragment_to_inventoryListFragment, bundle);
                }
            });

            setActionBarTitle(mLocation.getName());
            setActionBarSubtitle("Information");
        }

        return rootView;
    }
}
