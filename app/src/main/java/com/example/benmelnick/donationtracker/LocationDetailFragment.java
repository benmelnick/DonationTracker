package com.example.benmelnick.donationtracker;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

    /**
     * The dummy content this fragment is presenting.
     */
    private Location mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LocationDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            int item_id = getArguments().getInt(ARG_ITEM_ID);
            mItem = Model.INSTANCE.findItemById(item_id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_location_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.name)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.type)).setText("Type:\n" + mItem.getType());
            ((TextView) rootView.findViewById(R.id.longitude)).setText("Longitude:\n" + String.valueOf(mItem.getLongitude()));
            ((TextView) rootView.findViewById(R.id.latitude)).setText("Latitude:\n" + String.valueOf(mItem.getLatitude()));
            ((TextView) rootView.findViewById(R.id.address)).setText("Address:\n" + mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.phone)).setText("Phone Number:\n" + mItem.getPhoneNumber());
        }

        return rootView;
    }
}
