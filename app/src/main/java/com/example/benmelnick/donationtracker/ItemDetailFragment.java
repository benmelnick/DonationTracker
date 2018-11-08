package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

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
    @SuppressWarnings("RedundantNoArgConstructor")
    public ItemDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();

        if ((arguments != null) && arguments.containsKey(ARG_ITEM)) {
            mItem = (Item) arguments.getSerializable(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_item_detail, container, false);

        if (mItem != null) {
            DecimalFormat df = new DecimalFormat("#.00");
            String shortD = mItem.getShortDescription();
            String full = "Description:\n" + mItem.getFullDescription();
            String added = "Added:\n" + mItem.getTimeStamp();
            String price = "Price:\n$" + df.format(mItem.getValue());
            String category = "Category:\n" + mItem.getCategory();
            String location = "Can Be Found At:\n" + mItem.getLocation();

            ((TextView) rootView.findViewById(R.id.short_description)).setText(shortD);
            ((TextView) rootView.findViewById(R.id.full_description)).setText(full);
            ((TextView) rootView.findViewById(R.id.timestamp)).setText(added);
            ((TextView) rootView.findViewById(R.id.value)).setText(price);
            ((TextView) rootView.findViewById(R.id.category)).setText(category);
            ((TextView) rootView.findViewById(R.id.location)).setText(location);
        } else {
            Toast toast = Toast.makeText(getContext(),
                    "The item's information could not be loaded at this time!",
                    Toast.LENGTH_LONG);
            toast.show();
        }

        return rootView;
    }
}