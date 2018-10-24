package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String locationName; //location we are searching
    private String item; //item we are searching
    private String category; //category we are searching
    private final ArrayList<Item> mItems = new ArrayList<>(); //list of items that match the search

    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //TODO: initialize the chosen category
        locationName = getIntent().getStringExtra("location");
        item = getIntent().getStringExtra("item");
        category = getIntent().getStringExtra("category");

        description = (TextView)findViewById(R.id.description);

        //populate mItems
        getResults();
        if (mItems.size() == 0) {
            //TODO : implement toast if nothing was found
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(mItems);
        mRecyclerView.setAdapter(adapter);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Inventory");
    }

    /**
     * searches through the database for items that match the search
     * if selected location is "All locations", search the entire locations database
     * otherwise, search the children of the specific location's inventory database
     */
    private void getResults() {
        if (category.equals("Choose a category")) {
            //only an item is entered
            description.setText("Results for item '" + item + "' in " + locationName);
            searchItemOnly();
        } else if (item.equals("")) {
            //only a category is entered
            description.setText("Results for category '" + category + "' in " + locationName);
            searchCategoryOnly();
        } else {
            //both are entered
            description.setText("Results for item '" + item + "' in category '" + category + "' in " + locationName);
        }

    }

    /**
     * searches through the database checking only for the name of the item
     * for this case, the category is left blank, and only the item is checked
     */
    private void searchItemOnly() {
        if (locationName.equals("All locations")) {
            //search across all locations
            mDatabase.child("locations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //go into location's data - iterate through inventory
                        //TODO: implement item search for all locations
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //search specific location
            mDatabase.child("locations").child(locationName).child("inventory").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("shortDescription").getValue().toString().equals(item)) {
                            //found the item - add to the list
                            String timeStamp = ds.child("timeStamp").getValue().toString();
                            String shortDescription = ds.child("shortDescription").getValue().toString();
                            String fullDescription = ds.child("fullDescription").getValue().toString();
                            double value = Double.valueOf(ds.child("value").getValue().toString());
                            String category = ds.child("category").getValue().toString();
                            Item item = new Item(timeStamp, shortDescription, fullDescription, value, category);
                            System.out.println("found " + item.getShortDescription() + "^^^^^^^^^^^^^^^");
                            mItems.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * searches through the database checking only for the chosen category
     * for this case, the item is left blank, and only the category is checked
     */
    private void searchCategoryOnly() {
        if (locationName.equals("All locations")) {
            //search across all locations
            mDatabase.child("locations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //go into location's data - iterate through inventory
                        //TODO: implement category search for all locations
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //search specific location
            mDatabase.child("locations").child(locationName).child("inventory").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("category").getValue().toString().equals(category)) {
                            //found the item - add to the list
                            String timeStamp = ds.child("timeStamp").getValue().toString();
                            String shortDescription = ds.child("shortDescription").getValue().toString();
                            String fullDescription = ds.child("fullDescription").getValue().toString();
                            double value = Double.valueOf(ds.child("value").getValue().toString());
                            String category = ds.child("category").getValue().toString();
                            Item item = new Item(timeStamp, shortDescription, fullDescription, value, category);
                            System.out.println("found " + item.getShortDescription() + "^^^^^^^^^^^^^^^");
                            mItems.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    /**
     * searches through the database checking for both fields
     */
    private void searchItemAndCategory() {
        if (locationName.equals("All locations")) {
            //search across all locations
            mDatabase.child("locations").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //go into location's data - iterate through inventory
                        //TODO: implement category and item search for all locations
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //search specific location
            mDatabase.child("locations").child(locationName).child("inventory").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (ds.child("shortDescription").getValue().toString().equals(item)
                                && ds.child("category").getValue().toString().equals(category)) {
                            //found the item - add to the list
                            //TODO: debug this case - not working
                            String timeStamp = ds.child("timeStamp").getValue().toString();
                            String shortDescription = ds.child("shortDescription").getValue().toString();
                            String fullDescription = ds.child("fullDescription").getValue().toString();
                            double value = Double.valueOf(ds.child("value").getValue().toString());
                            String category = ds.child("category").getValue().toString();
                            Item item = new Item(timeStamp, shortDescription, fullDescription, value, category);
                            System.out.println("found " + item.getShortDescription() + "^^^^^^^^^^^^^^^");
                            mItems.add(item);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        private List<Item> mItems;
        // Provide a suitable constructor (depends on the kind of dataset)
        public ItemAdapter(List<Item> values) {
            mItems = values;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventory_list_item, parent, false);
            return new ItemAdapter.MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mItem = mItems.get(position);
            holder.mContentView.setText(mItems.get(position).getShortDescription());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM, holder.mItem);
                    context.startActivity(intent);
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

