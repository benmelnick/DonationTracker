package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is contained in {@link LocationDetailActivity}.
 */
public class LocationDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LOCATION = "location";

    /**
     * The dummy content this fragment is presenting.
     */
    private Location mLocation;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            mLocation = Model.INSTANCE.findLocationById(item_id);
        }

        /*
        // Use if we aren't required to use Model
        // This loads the Location object directly without looking it up from a list

        if (getArguments().containsKey(ARG_LOCATION)) {
            mLocation = (Location) getArguments().getSerializable(ARG_LOCATION);
        }
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_location_detail, container, false);

        if (mLocation != null) {
            String coordinates = mLocation.getLongitude() + ", " + mLocation.getLatitude();
            ((TextView) rootView.findViewById(R.id.name)).setText(mLocation.getName());
            ((TextView) rootView.findViewById(R.id.type)).setText("Type:\n" + mLocation.getType());
            ((TextView) rootView.findViewById(R.id.coordinates)).setText("Coordinates:\n" + coordinates);
            ((TextView) rootView.findViewById(R.id.address)).setText("Address:\n" + mLocation.printFullAddress());
            ((TextView) rootView.findViewById(R.id.phone)).setText("Phone Number:\n" + mLocation.getPhoneNumber());


            mAuth = FirebaseAuth.getInstance();
            mDatabase = FirebaseDatabase.getInstance().getReference();

            final Button inventoryBttn = (Button) rootView.findViewById(R.id.view_items);
            final Button addBttn = (Button) rootView.findViewById(R.id.add_item);

            String id = mAuth.getCurrentUser().getUid();
            mDatabase.child("users").child(id).child("accountType").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String value = (String) dataSnapshot.getValue();

                    if (value.equals("Location Employee")) {
                        addBttn.setVisibility(View.VISIBLE);
                        inventoryBttn.setVisibility(View.VISIBLE);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            inventoryBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), InventoryListActivity.class);
                    intent.putExtra("id", mLocation.getId());
                    startActivity(intent);
                }
            });

            addBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddItemActivity.class);
                    intent.putExtra("id", mLocation.getId());
                    startActivity(intent);
                }
            });
        }

        return rootView;
    }
}
