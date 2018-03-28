package com.example.monic.tripapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView add;
    Button travelmap;
    Button exit;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Trips");
    ArrayList<Trips> tripsList = new ArrayList<Trips>();
    Trips newTrip;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (ImageView) findViewById(R.id.addButton);
        travelmap = (Button) findViewById(R.id.travel_map);
        exit = (Button) findViewById(R.id.button_exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
        travelmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                i.putExtra("tripplaces", (Serializable) tripsList);
                startActivity(i);
            }
        });
        
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddTrips.class);
                startActivity(i);
            }
        });

        mNoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tripsList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    final DatabaseReference mRef = mNoteRef.child(postSnapshot.getKey().toString());
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            newTrip = new Trips();
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                if(postSnapshot.getKey().toString().equals("latitude")) {
                                    newTrip.setLat(postSnapshot.getValue().toString());
                                } else if(postSnapshot.getKey().toString().equals("longitude")) {
                                    newTrip.setLng(postSnapshot.getValue().toString());
                                } else if((postSnapshot.getKey().toString().equals("placename"))) {
                                    newTrip.setTripPlace(postSnapshot.getValue().toString());
                                } else if((postSnapshot.getKey().toString().equals("tripname"))){
                                    newTrip.setTripName(postSnapshot.getValue().toString());
                                } else if(postSnapshot.getKey().toString().equals("tripid")){
                                    newTrip.setTripId(postSnapshot.getValue().toString());
                                }
                            }
                            tripsList.add(newTrip);
                            mRecyclerView = (RecyclerView) findViewById(R.id.trips_view);
                            mAdapter = new TripAdapter(MainActivity.this, tripsList);
                            mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
