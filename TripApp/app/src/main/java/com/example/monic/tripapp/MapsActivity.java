package com.example.monic.tripapp;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Trips> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if(getIntent().getExtras() != null) {
            list = (List<Trips>) getIntent().getSerializableExtra("tripplaces");
        }

        for(Trips tp : list) {
            LatLng nearplace = new LatLng(Double.parseDouble(tp.getLat()), Double.parseDouble(tp.getLng()));
            builder.include(nearplace);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(tp.getLat()), Double.parseDouble(tp.getLng())))
                    .title(tp.getTripPlace()));
        }

        // Drawing map bounds
        int zoomWidth = getResources().getDisplayMetrics().widthPixels;
        int zoomHeight = getResources().getDisplayMetrics().heightPixels;
        int zoomPadding = (int) (zoomWidth * 0.10);

        LatLngBounds bounds = builder.build();
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,zoomWidth,zoomHeight,zoomPadding));
    }
}
