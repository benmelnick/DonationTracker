package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_LOCATION_ID = "location_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Item mItem;
    private String mItemName;
    private Location mLocation;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_LOCATION_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_LOCATION_ID);
            mLocation = Model.INSTANCE.findLocationById(item_id);
        }

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItemName = getArguments().getString(ARG_ITEM_ID);
        }

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //read all the inventory from the database - add to array list for displaying

        mDatabase.child("locations").child(mLocation.getName()).child("inventory").child(mItemName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String timeStamp = dataSnapshot.child("timeStamp").getValue().toString();
                String shortDescription = dataSnapshot.child("shortDescription").getValue().toString();
                String fullDescription = dataSnapshot.child("fullDescription").getValue().toString();
                double value = Double.valueOf(dataSnapshot.child("value").getValue().toString());
                String category = dataSnapshot.child("category").getValue().toString();
                mItem = new Item(timeStamp, shortDescription, fullDescription, value, category);
                System.out.println("looking at " + mItem.getShortDescription() + "^^^^^^^^^^^^^^");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_item_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.timestamp)).setText(mItem.getTimeStamp());
            ((TextView) rootView.findViewById(R.id.short_description)).setText(mItem.getShortDescription());
            ((TextView) rootView.findViewById(R.id.full_description)).setText("Description:\n" + mItem.getFullDescription());
            ((TextView) rootView.findViewById(R.id.value)).setText("Price:\n$" + mItem.getValue());
            ((TextView) rootView.findViewById(R.id.category)).setText("Category:\n" + mItem.getCategory());
        }

        return rootView;
    }
}