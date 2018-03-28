package com.example.monic.tripapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 12/9/2017.
 */

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

    List<Trips> mData = new ArrayList<Trips>();
    List<TripPlace> tripPlaceList = new ArrayList<TripPlace>();

    private static int counter = 0;
    static MainActivity mainActivity;

    public TripAdapter(MainActivity mainActivity, List<Trips> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView placeName;
        ImageView placePhoto;
        ImageView delete;
        List<TripPlace> tpList;
        Trips c;

        public ViewHolder(View v) {
            super(v);
            placeName = v.findViewById(R.id.trip_names);
            placePhoto = v.findViewById(R.id.place_photo);
            delete = v.findViewById(R.id.delete_item);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mainActivity, TripView.class);
                    i.putExtra("tripobject", c);
                    mainActivity.startActivity(i);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Trips p = mData.get(position);
        holder.c = p;
        holder.placeName.setText(p.getTripPlace());

//        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference mNoteRef = mRootRef.child("Trips");
//        DatabaseReference mRef = mNoteRef.child(p.getTripId());
//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    if (postSnapshot.getKey().toString().equals("Places")){
//                        for(DataSnapshot snapshot : postSnapshot.getChildren()) {
//                            TripPlace tp = snapshot.getValue(TripPlace.class);
//                            tripPlaceList.add(tp);
//                        }
//                    }
//                }
//                List<TripPlace> newList = new ArrayList<TripPlace>();
//                for(TripPlace tp : tripPlaceList) {
//                    if(tp.getTripId().equals(p.getTripId())) {
//                       newList.add(tp);
//                    }
//                }
//                holder.tpList = newList;
//                RecyclerView.Adapter mAdapter = new TripPlaceAdapter(mainActivity, newList);
//                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mainActivity);
//                holder.mRecyclerView.setLayoutManager(mLayoutManager);
//                holder.mRecyclerView.setAdapter(mAdapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}