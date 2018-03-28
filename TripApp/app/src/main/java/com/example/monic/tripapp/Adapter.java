package com.example.monic.tripapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 12/8/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    List<City> mData = new ArrayList<City>();


    private static int counter = 0;
    static AddTrips mainActivity;

    DatabaseReference mNoteRef;

    public Adapter(AddTrips mainActivity, List<City> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        City c;

        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.cityname);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TripInterface listener = (TripInterface) mainActivity;
                    ArrayList<String> info = new ArrayList<String>();
                    info.add(c.getName());
                    info.add(c.getId());
                    listener.sendPlaceId(info);
                }
            });
        }
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_item, parent, false);
        Adapter.ViewHolder viewHolder = new Adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, final int position) {
        final City p = mData.get(position);
        holder.c = p;
        holder.name.setText(p.getName());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    interface TripInterface {
        void sendPlaceId(ArrayList<String> info);
    }
}


