package com.example.benmelnick.donationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * activity for viewing items in a location's database
 * for location employees, there will also be a button to add items to the inventory
 */
public class InventoryListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Location mLocation; //reference to the current location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("users").child(id).child("accountType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String)dataSnapshot.getValue();
                if (value.equals("Location Employee")) {
                    Button add = (Button) findViewById(R.id.add_item);
                    add.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        int locationId = getIntent().getIntExtra("id", 0);
        mLocation = Model.INSTANCE.findItemById(locationId);

        Button add = (Button)findViewById(R.id.add_item);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryListActivity.this, AddItemActivity.class);
                intent.putExtra("id", mLocation.getId());
                startActivity(intent);
            }
        });

    }
}
