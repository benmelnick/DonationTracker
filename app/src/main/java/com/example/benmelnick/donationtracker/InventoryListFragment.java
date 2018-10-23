package com.example.benmelnick.donationtracker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.navigation.Navigation;


public class InventoryListFragment extends ActionBarFragment {
    public static final String ARG_LOCATION = "location";

    private DatabaseReference mDatabase;
    private Location mLocation;
    private final ArrayList<Item> mItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;

    public InventoryListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param location The location.
     * @return A new instance of fragment InventoryListFragment.
     */
    public static InventoryListFragment newInstance(Location location) {
        InventoryListFragment fragment = new InventoryListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION, location);
        fragment.setArguments(args);
        return fragment;
    }

    private Item makeItem(DataSnapshot dataSnapshot) {
        String timeStamp = dataSnapshot.child("timeStamp").getValue().toString();
        String shortDescription = dataSnapshot.child("shortDescription").getValue().toString();
        String fullDescription = dataSnapshot.child("fullDescription").getValue().toString();
        double value = Double.valueOf(dataSnapshot.child("value").getValue().toString());
        String category = dataSnapshot.child("category").getValue().toString();
        return new Item(timeStamp, shortDescription, fullDescription, value, category);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLocation = (Location) getArguments().getSerializable(ARG_LOCATION);

            adapter = new ItemAdapter(mItems);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            mDatabase.child("locations").child(mLocation.getName()).child("inventory").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Item item = makeItem(dataSnapshot);
                    mItems.add(item);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemInserted(mItems.size());
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Item item = makeItem(dataSnapshot);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemChanged(mItems.indexOf(item));
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    Item item = makeItem(dataSnapshot);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemRemoved(mItems.indexOf(item));
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Item item = makeItem(dataSnapshot);
                    //adapter.notifyDataSetChanged();
                    adapter.notifyItemRemoved(mItems.indexOf(item));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(this.getClass().getSimpleName(), "getLocations:onCancelled", databaseError.toException());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_inventory_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AddItemFragment.ARG_LOCATION, mLocation);

                Navigation.findNavController(view).navigate(R.id.action_inventoryListFragment_to_addItemFragment, bundle);
            }
        });

        setActionBarTitle(mLocation.getName());
        setActionBarSubtitle("Inventory");

        return rootView;
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        private List<Item> mItems;
        // Provide a suitable constructor (depends on the kind of dataset)
        public ItemAdapter(List<Item> values) {
            mItems = values;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventory_list_item, parent, false);
            return new ItemAdapter.MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ItemAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mItem = mItems.get(position);
            holder.mContentView.setText(mItems.get(position).getShortDescription());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ItemDetailFragment.ARG_ITEM, holder.mItem);
                    Navigation.findNavController(view).navigate(R.id.action_inventoryListFragment_to_itemDetailFrag, bundle);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mItems.size();
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public final TextView mContentView;
            public Item mItem;

            public MyViewHolder(View v) {
                super(v);
                mView = v;
                mContentView = v.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return mContentView.getText() + "'";
            }
        }
    }
}
