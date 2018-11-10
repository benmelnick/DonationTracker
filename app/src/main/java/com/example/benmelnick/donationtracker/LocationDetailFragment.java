package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    @SuppressWarnings("RedundantNoArgConstructor")
    public LocationDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(ARG_ITEM_ID)) {
                int item_id = bundle.getInt(ARG_ITEM_ID);
                mLocation = Model.INSTANCE.findLocationById(item_id);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_location_detail, container, false);

        if (mLocation != null) {
            String name = mLocation.getName();
            String type = "Type:\n" + mLocation.getType();
            String coordinates = "Coordinates:\n" + mLocation.getLongitude() + ", "
                    + mLocation.getLatitude();
            String address = "Address:\n" + mLocation.printFullAddress();
            String phone = "Phone Number:\n" + mLocation.getPhoneNumber();

            ((TextView) rootView.findViewById(R.id.name)).setText(name);
            ((TextView) rootView.findViewById(R.id.type)).setText(type);
            ((TextView) rootView.findViewById(R.id.coordinates)).setText(coordinates);
            ((TextView) rootView.findViewById(R.id.address)).setText(address);
            ((TextView) rootView.findViewById(R.id.phone)).setText(phone);

            final Button inventoryBttn = rootView.findViewById(R.id.view_items);
            final Button addBttn = rootView.findViewById(R.id.add_item);

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

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            if (user != null) {
                String id = user.getUid();
                DatabaseReference accTypeRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                        "users", id, "accountType");
                accTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value = (String) dataSnapshot.getValue();

                        if ("Location Employee".equals(value)) {
                            addBttn.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

        return rootView;
    }
}
