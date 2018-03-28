package com.example.monic.tripapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddPlaces extends AppCompatActivity implements PlaceAdapter.PlaceInterface{

    String tripLat;
    String tripLng;
    String tripid;

    ArrayList<TripPlace> placesList = new ArrayList<TripPlace>();

    private final OkHttpClient client = new OkHttpClient();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Trips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        if (getIntent().getExtras() != null) {
            tripLat = getIntent().getExtras().getString("latitude");
            tripLng = getIntent().getExtras().getString("longitude");
            tripid = getIntent().getExtras().getString("tripid");
            getPlaces();
        }
    }

    private void getPlaces() {
        final Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyAgXYlIiVuM8rn_9obcZCwWRtemwSnLQzo&location=" + tripLat + "," +
                        tripLng + "&radius=1000")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("demo", "fail");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                try {
                    final JSONObject root = new JSONObject(res);
                    if (root.getString("status").equals("OK")) {
                        for(int i = 0; i < root.getJSONArray("results").length(); i++) {
                            TripPlace tp = new TripPlace();
                            tp.setPlaceLat(root.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                            tp.setPlaceLng(root.getJSONArray("results").getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                            tp.setPlaceName(root.getJSONArray("results").getJSONObject(i).getString("name"));
                            tp.setTripId(tripid);
                            placesList.add(tp);
                        }
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mRecyclerView = (RecyclerView) findViewById(R.id.near_by_places);
                            mLayoutManager = new LinearLayoutManager(AddPlaces.this);
                            mRecyclerView.setLayoutManager(mLayoutManager);

                            mAdapter = new PlaceAdapter(AddPlaces.this, placesList);
                            mRecyclerView.setAdapter(mAdapter);
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void sendPlaceInfo(TripPlace tp) {
        DatabaseReference mRef = mNoteRef.child(tp.getTripId()).child("Places");
        String id = mRef.push().getKey();
        mRef.child(id).setValue(tp);
        finish();
    }
}
