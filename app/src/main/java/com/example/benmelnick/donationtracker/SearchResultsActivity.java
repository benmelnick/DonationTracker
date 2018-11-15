package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The activity which shows results from a search request
 */
public class SearchResultsActivity extends AppCompatActivity {

    private DatabaseReference locsRef;

    private String locationName; //location we are searching
    //one of these guaranteed to exist
    private String item; //item we are searching
    private String category; //category we are searching

    private ItemAdapter adapter;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        this.locsRef = FirebaseHelper.INSTANCE.getDatabaseReference("locations");

        Intent intent = getIntent();
        locationName = intent.getStringExtra("location");
        item = intent.getStringExtra("item");
        category = intent.getStringExtra("category");

        description = findViewById(R.id.description);

        //populate mItems
        getResults();

        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter();
        mRecyclerView.setAdapter(adapter);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Inventory");
        }
    }

    /**
     * searches through the database for items that match the search
     * if selected location is "All locations", search the entire locations database
     * otherwise, search the children of the specific location's inventory database
     */
    private void getResults() {
        if ("Choose a category".equals(category)) {
            //only an item is entered
            String newText = "Results for item '" + item + "' in " + locationName;
            description.setText(newText);
            searchItemOnly();
        } else if ("".equals(item)) {
            //only a category is entered
            String newText = "Results for category '" + category + "' in " + locationName;
            description.setText(newText);
            searchCategoryOnly();
        } else {
            //both are entered
            String newText = "Results for item '" + item + "' in category '" + category
                    + "' in " + locationName;
            description.setText(newText);
            searchItemAndCategory();
        }
    }

    /**
     * searches through the database checking only for the name of the item
     * for this case, the category is left blank, and only the item is checked
     */
    private void searchItemOnly() {
        if ("All locations".equals(locationName)) {
            //search across all locations
            locsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //now looking at a specific location's data
                        DataSnapshot nameSnap = ds.child("name");
                        String name = nameSnap.getValue(String.class);

                        DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                                "locations", name, "inventory");
                        invRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    checkCurrentInventoryItem(ds, "shortDescription", item);
                                }

                                checkEmptyList();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                    "locations", locationName, "inventory");

            //search specific location
            invRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        checkCurrentInventoryItem(ds, "shortDescription", item);
                    }

                    checkEmptyList();
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
        if ("All locations".equals(locationName)) {
            //search across all locations
            locsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //now looking at a specific location's data
                        DataSnapshot nameSnap = ds.child("name");
                        String name = nameSnap.getValue(String.class);

                        DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                                "locations", name, "inventory");

                        invRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    checkCurrentInventoryItem(ds, "category", category);
                                }

                                checkEmptyList();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //search specific location
            DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                    "locations", locationName, "inventory");

            invRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        checkCurrentInventoryItem(ds, "category", category);
                    }

                    checkEmptyList();
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
        if ("All locations".equals(locationName)) {
            //search across all locations
            locsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //now looking at a specific location's data
                        DataSnapshot nameSnap = ds.child("name");
                        String name = nameSnap.getValue(String.class);

                        DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                                "locations", name, "inventory");

                        invRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    DataSnapshot shortSnap = ds.child("shortDescription");
                                    String shortDescription = shortSnap.getValue(String.class);

                                    DataSnapshot catSnap = ds.child("category");
                                    String category = catSnap.getValue(String.class);

                                    if ((shortDescription != null) && shortDescription.equals(item)
                                            && (category != null)) {
                                        //found the item - add to the list
                                        DataSnapshot timeSnap = ds.child("timeStamp");
                                        String timeStamp = timeSnap.getValue(String.class);

                                        DataSnapshot fullSnap = ds.child("fullDescription");
                                        String fullDescription = fullSnap.getValue(String.class);

                                        DataSnapshot valueSnap = ds.child("value");
                                        Double value = valueSnap.getValue(Double.class);

                                        DataSnapshot locSnap = ds.child("location");
                                        String location = locSnap.getValue(String.class);

                                        if (value != null) {
                                            Item item = new Item(timeStamp, shortDescription,
                                                    fullDescription, value, category, location);
                                            adapter.addItem(item);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            //search specific location
            DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                    "locations", locationName, "inventory");
            invRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        DataSnapshot shortSnap = ds.child("shortDescription");
                        String shortDescription = shortSnap.getValue(String.class);

                        DataSnapshot catSnap = ds.child("category");
                        String category = catSnap.getValue(String.class);

                        if ((shortDescription != null) && shortDescription.equals(item)
                                && (category != null)) {
                            //found the item - add to the list

                            DataSnapshot timeSnap = ds.child("timeStamp");
                            String timeStamp = timeSnap.getValue(String.class);

                            DataSnapshot fullSnap = ds.child("fullDescription");
                            String fullDescription = fullSnap.getValue(String.class);

                            DataSnapshot valueSnap = ds.child("value");
                            Double value = valueSnap.getValue(Double.class);

                            DataSnapshot locSnap = ds.child("location");
                            String location = locSnap.getValue(String.class);

                            if (value != null) {
                                Item item = new Item(timeStamp, shortDescription,
                                        fullDescription, value, category, location);
                                adapter.addItem(item);
                            }
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
     * private helper method for examining the current inventory item
     * @param ds datasnapshot representing the inventory item
     * @param field represents the filter being applied either shortDescription or category
     * @param value represents the actual value of the filer for ds
     */
    private void checkCurrentInventoryItem(DataSnapshot ds, String field, String value) {
        DataSnapshot fieldSnap = ds.child(field);
        String fieldValue = fieldSnap.getValue(String.class);

        if (fieldValue != null) {
            if (fieldValue.equals(value)) {
                //found the item - add to the list
                DataSnapshot timeSnap = ds.child("timeStamp");
                String timeStamp = timeSnap.getValue(String.class);

                DataSnapshot shortSnap = ds.child("shortDescription");
                String shortDescription = shortSnap.getValue(String.class);

                DataSnapshot fullSnap = ds.child("fullDescription");
                String fullDescription = fullSnap.getValue(String.class);

                DataSnapshot valueSnap = ds.child("value");
                Double itemVal = valueSnap.getValue(Double.class);

                DataSnapshot categorySnap = ds.child("category");
                String category = categorySnap.getValue(String.class);

                DataSnapshot locSnap = ds.child("location");
                String location = locSnap.getValue(String.class);

                if (itemVal != null) {
                    Item item = new Item(timeStamp, shortDescription, fullDescription,
                            itemVal, category, location);
                    adapter.addItem(item);
                }
            }
        }
    }


    private void checkEmptyList() {
        if (adapter.mItems.isEmpty()) {
            Toast toast = Toast.makeText(SearchResultsActivity.this,
                    "No items matching the search fields were found.",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }


    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        private final List<Item> mItems;
        // Provide a suitable constructor (depends on the kind of dataset)
        ItemAdapter() {
            mItems = new ArrayList<>();
        }

        /**
         * Adds a new item to the adapter
         * @param newItem The item to add
         */
        void addItem(Item newItem) {
            mItems.add(newItem);
            this.notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // create a new view
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(
                    R.layout.inventory_list_item, parent, false);
            return new ItemAdapter.MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mItem = mItems.get(position);

            Item foundItem = mItems.get(position);

            holder.mContentView.setText(foundItem.getShortDescription());

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
            final View mView;
            final TextView mContentView;
            private Item mItem;

            MyViewHolder(View v) {
                super(v);
                mView = v;
                mContentView = v.findViewById(R.id.content);
            }

            @NonNull
            @Override
            public String toString() {
                return mContentView.getText() + "'";
            }
        }
    }
}

