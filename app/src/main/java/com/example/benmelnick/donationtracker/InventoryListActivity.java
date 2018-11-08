package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;

/**
 * activity for viewing items in a location's database
 * for location employees, there will also be a button to add items to the inventory
 */
public class InventoryListActivity extends AppCompatActivity {

    private final ArrayList<Item> mItems = new ArrayList<>();

    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        //define the location being referenced
        Intent intent = getIntent();
        int locationId = intent.getIntExtra("id", 0);
        Location mLocation = Model.INSTANCE.findLocationById(locationId);

        // FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        String locName = null;
        if (mLocation != null) {
            //noinspection LawOfDemeter
            locName = mLocation.getName();
        }

        //read all the inventory from the database - add to array list for displaying
        DatabaseReference invRef = FirebaseHelper.INSTANCE.getDatabaseReference(
                "locations", locName, "inventory");
        invRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataSnapshot timeSnap = ds.child("timeStamp");
                    String timeStamp = timeSnap.getValue(String.class);

                    DataSnapshot shortSnap = ds.child("shortDescription");
                    String shortDescription = shortSnap.getValue(String.class);

                    DataSnapshot fullSnap = ds.child("fullDescription");
                    String fullDescription = fullSnap.getValue(String.class);

                    DataSnapshot valueSnap = ds.child("value");
                    Double value = valueSnap.getValue(Double.class);

                    DataSnapshot catSnap = ds.child("category");
                    String category = catSnap.getValue(String.class);

                    DataSnapshot locSnap = ds.child("location");
                    String location = locSnap.getValue(String.class);

                    if (value != null) {
                        Item item = new Item(timeStamp, shortDescription,
                                fullDescription, value, category, location);

                        mItems.add(item);
                        adapter.notifyDataSetChanged();
                    }
                }

                if (mItems.isEmpty()) {
                    Toast toast = Toast.makeText(InventoryListActivity.this,
                            "This location does not have any items in its inventory!",
                            Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(mItems);
        mRecyclerView.setAdapter(adapter);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Inventory");
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
        private final List<Item> mItems;
        // Provide a suitable constructor (depends on the kind of dataset)
        ItemAdapter(List<Item> values) {
            mItems = values;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                           int viewType) {
            // create a new view
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View v = layoutInflater.inflate(
                    R.layout.inventory_list_item, parent, false);
            return new MyViewHolder(v);
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
