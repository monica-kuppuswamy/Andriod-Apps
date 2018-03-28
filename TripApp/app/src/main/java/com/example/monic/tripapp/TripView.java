package com.example.monic.tripapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class TripView extends AppCompatActivity {

    TextView tripDetails;
    Button tripMap;
    Button close;
    ArrayList<Trips> tripsList = new ArrayList<Trips>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_view);

        tripDetails = (TextView) findViewById(R.id.view_trip_name);
        tripMap = (Button) findViewById(R.id.view_viewmap);
        close = (Button) findViewById(R.id.view_close);

        if(getIntent().getExtras()!= null) {
            Trips t = (Trips) getIntent().getSerializableExtra("tripobject");
            tripDetails.setText(t.getTripPlace());
            tripsList.add(t);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tripMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TripView.this, MapsActivity.class);
                i.putExtra("tripplaces", (Serializable) tripsList);
                startActivity(i);
            }
        });


    }
}
