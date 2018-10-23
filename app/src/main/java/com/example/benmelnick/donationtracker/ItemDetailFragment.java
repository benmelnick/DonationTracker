package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class ItemDetailFragment extends ActionBarFragment {
    public static final String ARG_ITEM = "item";

    private Item mItem;

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item The Item object
     * @return A new instance of fragment ItemDetailFragment.
     */
    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mItem = (Item) getArguments().getSerializable(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        if (mItem != null) {
            DecimalFormat df = new DecimalFormat("#.00");
            String price = df.format(mItem.getValue());

            ((TextView) rootView.findViewById(R.id.full_description)).setText("Description:\n" + mItem.getFullDescription());
            ((TextView) rootView.findViewById(R.id.timestamp)).setText("Added:\n" + mItem.getTimeStamp());
            ((TextView) rootView.findViewById(R.id.value)).setText("Price:\n$" + price);
            ((TextView) rootView.findViewById(R.id.category)).setText("Category:\n" + mItem.getCategory());

            setActionBarTitle(mItem.getShortDescription());
            setActionBarSubtitle("");
        } else {
            Toast.makeText(getContext(), "The item's information could not be loaded at this time!",
                    Toast.LENGTH_LONG).show();
        }

        return rootView;
    }
}
