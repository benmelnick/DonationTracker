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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.ArrayList;

/**
 * activity for viewing items in a location's database
 * for location employees, there will also be a button to add items to the inventory
 */
public class InventoryListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Location mLocation; //reference to the current location
    private final ArrayList<Item> mItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        //define the location being referenced
        int locationId = getIntent().getIntExtra("id", 0);
        mLocation = Model.INSTANCE.findLocationById(locationId);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //read all the inventory from the database - add to array list for displaying
        mDatabase.child("locations").child(mLocation.getName()).child("inventory").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String timeStamp = ds.child("timeStamp").getValue().toString();
                    String shortDescription = ds.child("shortDescription").getValue().toString();
                    String fullDescription = ds.child("fullDescription").getValue().toString();
                    double value = Double.valueOf(ds.child("value").getValue().toString());
                    String category = ds.child("category").getValue().toString();
                    Item item = new Item(timeStamp, shortDescription, fullDescription, value, category);

                    mItems.add(item);
                    adapter.notifyDataSetChanged();
                }

                if (mItems.size() == 0) {
                    Toast.makeText(InventoryListActivity.this, "This location does not have any items in its inventory!",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(InventoryListActivity.this, "The inventory data could not be loaded at this time!",
                        Toast.LENGTH_LONG).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(mItems);
        mRecyclerView.setAdapter(adapter);
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
            return new MyViewHolder(v);
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
