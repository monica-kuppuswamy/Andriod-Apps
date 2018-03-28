package com.example.monic.tripapp;

import android.*;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AddTrips extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;

    Button addTrip;
    Button cancelTrip;
    ImageView search;
    EditText tripName;
    EditText tripDate;
    ImageView datePicker;
    ImageView addPhoto;
    EditText placeName;

    ArrayList<String> selectedPlaceInfo = new ArrayList<>();
    String currentLat;
    String currentLng;
    List<City> cities = new ArrayList<City>();

    private final OkHttpClient client = new OkHttpClient();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mNoteRef = mRootRef.child("Trips");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trips);

        addTrip = (Button) findViewById(R.id.addTripButton);
        cancelTrip = (Button) findViewById(R.id.cancelButton);
        placeName = (EditText) findViewById(R.id.place);
        tripName = (EditText) findViewById(R.id.tripname);
        search = (ImageView) findViewById(R.id.search_place);
        tripDate = (EditText) findViewById(R.id.tripdate);
        datePicker = (ImageView) findViewById(R.id.datepicker);
        addPhoto = (ImageView) findViewById(R.id.addimage);

        // Code for date picker
        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tripDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        final DatePickerDialog dpdialog = new DatePickerDialog(AddTrips.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        dpdialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddTrips.this, "Datepicker", Toast.LENGTH_SHORT).show();
                dpdialog.show();
            }
        });

        cancelTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddTrips.this, android.Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(AddTrips.this,
                            new String[]{android.Manifest.permission.CAMERA},
                            1001);
                }
                else {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(AddTrips.this.getPackageManager()) != null)
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placeSearch = placeName.getText().toString();
                getGeoCoordinates(placeSearch);
            }
        });

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTrip();
            }
        });
    }

    private void getGeoCoordinates(final String placeId) {
        final Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/geocode/json?address=" + placeId + "&key=AIzaSyAgXYlIiVuM8rn_9obcZCwWRtemwSnLQzo")
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
                        currentLat = root.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lat");
                        currentLng = root.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(AddTrips.this, "Geo coordinates found.", Toast.LENGTH_SHORT).show();
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addTrip() {
        String id = mNoteRef.push().getKey();

        mNoteRef.child(id).child("tripid").setValue(id);
        mNoteRef.child(id).child("tripname").setValue(tripName.getText().toString());
        mNoteRef.child(id).child("placename").setValue(placeName.getText().toString());
        mNoteRef.child(id).child("latitude").setValue(currentLat);
        mNoteRef.child(id).child("longitude").setValue(currentLng);
        placeName.setText(" ");
        tripName.setText(" ");
        Toast.makeText(AddTrips.this, "Trip Album is created.", Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void sendPlaceId(ArrayList<String> info) {
//        selectedPlaceInfo = info;
//        placeName.setText(selectedPlaceInfo.get(0));
//    }
}
