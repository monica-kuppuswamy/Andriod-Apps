package com.example.monic.tripapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monic on 12/9/2017.
 */

public class TripPlaceAdapter extends RecyclerView.Adapter<TripPlaceAdapter.ViewHolder>{
    List<TripPlace> mData = new ArrayList<TripPlace>();

    private static int counter = 0;
    static MainActivity mainActivity;

    public TripPlaceAdapter(MainActivity mainActivity, List<TripPlace> mData) {
        this.mData = mData;
        this.mainActivity = mainActivity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView placeName;
        ImageView deletePlace;
        TripPlace c;

        public ViewHolder(View v) {
            super(v);
            placeName = v.findViewById(R.id.trip_place_name);
            deletePlace = v.findViewById(R.id.trip_place_delete);
            deletePlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_places_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TripPlace p = mData.get(position);
        holder.c = p;
        holder.placeName.setText(p.getPlaceName());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}