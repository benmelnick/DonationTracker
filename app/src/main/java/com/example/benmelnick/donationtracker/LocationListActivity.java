package com.example.benmelnick.donationtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {
    private static final String TAG = "LocationListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
        LocationAdapter adapter = new LocationAdapter(Model.INSTANCE.getLocations());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() == 0) {
            readFile();
        }

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Donation Centers");
    }

    /**
     * reads in data from CSV file and creates locations
     * adds each location to model
     */
    private void readFile() {
        Model model = Model.INSTANCE;
        try {
            //Open a stream on the raw file
            InputStream is = getResources().openRawResource(R.raw.locationdata);
            //From here we probably should call a model method and pass the InputStream
            //Wrap it in a BufferedReader so that we get the readLine() method
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            br.readLine(); //get rid of header line
            while ((line = br.readLine()) != null) {
                Log.d(TAG, line);
                String[] tokens = line.split(",");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                double lat = Double.parseDouble(tokens[2]);
                double lon = Double.parseDouble(tokens[3]);
                String address = tokens[4];
                String city = tokens[5];
                String state = tokens[6];
                String zip = tokens[7];
                String type = tokens[8];
                String phone = tokens[9];
                String web = tokens[10];
                model.addLocation(new Location(id, name, lat, lon, address, city, state, zip, type, phone, web));
            }
            br.close();
        } catch (IOException e) {
            Log.e(TAG, "error reading assets", e);
        }
    }

    public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
        private List<Location> mLocations;
        // Provide a suitable constructor (depends on the kind of dataset)
        public LocationAdapter(List<Location> values) {
            mLocations = values;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.location_list_item, parent, false);
            return new MyViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mLocation = mLocations.get(position);
            holder.mContentView.setText(mLocations.get(position).getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, LocationDetailActivity.class);
                    intent.putExtra(LocationDetailFragment.ARG_ITEM_ID, holder.mLocation.getId());
                    intent.putExtra(LocationDetailFragment.ARG_LOCATION, holder.mLocation);
                    context.startActivity(intent);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mLocations.size();
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public View mView;
            public final TextView mContentView;
            public Location mLocation;

            public MyViewHolder(View v) {
                super(v);
                mView = v;
                mContentView = v.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
