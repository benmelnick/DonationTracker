package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single DataItem detail screen.
 * This fragment is contained in {@link LocationDetailActivity}.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";

    /**
     * The dummy content this fragment is presenting.
     */
    private Item mItem;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            mItem = (Item) getArguments().getSerializable(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_item_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.short_description)).setText(mItem.getShortDescription());
            ((TextView) rootView.findViewById(R.id.full_description)).setText("Description:\n" + mItem.getFullDescription());
            ((TextView) rootView.findViewById(R.id.timestamp)).setText("Added:\n" + mItem.getTimeStamp());
            ((TextView) rootView.findViewById(R.id.value)).setText("Price:\n$" + mItem.getValue());
            ((TextView) rootView.findViewById(R.id.category)).setText("Category:\n" + mItem.getCategory());
        } else {
            Toast.makeText(getContext(), "The item's information could not be loaded at this time!",
                    Toast.LENGTH_LONG).show();
        }

        return rootView;
    }
}