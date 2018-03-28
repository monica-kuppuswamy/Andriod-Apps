package com.example.monic.tripplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Home extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getAllCities();
    }

    public void getAllCities() {
        final Request request = new Request.Builder()
                .url("https://maps.googleapis.com/maps/api/place/autocomplete/json?key=AIzaSyD0XBGKsqWbQrwWVG-nWz4L4QV33G1wq-o&type=(cities)&input=charotte")
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
                        Log.d("demo", "Data is available");
                        for(int i = 0; i < root.getJSONArray("predictions").length(); i++) {
                            Log.d("demo", root.getJSONArray("predictions").getJSONObject(i).getString("place_id"));
                        }
                    }
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
