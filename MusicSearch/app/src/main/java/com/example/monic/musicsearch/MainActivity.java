package com.example.monic.musicsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TracksAdapter.ICallBack{

    final static String baseUrl = "http://ws.audioscrobbler.com/2.0/?format=json";
    ArrayList<Track> tList = new ArrayList<Track>();
    TracksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.favList);
        adapter = new TracksAdapter(this, R.layout.row_item, tList);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        adapter.setNotifyOnChange(true);

        final EditText track = (EditText) findViewById(R.id.editText);
        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String trackName = track.getText().toString();
                if (trackName.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter a track to search", Toast.LENGTH_LONG).show();
                } else {
                    String methodName = "track.search";
                    RequestParam request = new RequestParam(baseUrl, methodName, trackName, "");
                    Intent i = new Intent(MainActivity.this, SearchResults.class);
                    i.putExtra("url", request.getEncodedUrl());
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BuildListView();
    }

    private void BuildListView(){

        final SharedPreferences myPrefs = getSharedPreferences("com.example.monic.musicsearch", MODE_PRIVATE);
        String favListString = myPrefs.getString("music_favorites","");

        final Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Track>>(){}.getType();
        ArrayList<Track> tmp = gson.fromJson(favListString, type);

        tmp = tmp==null ? new ArrayList<Track>() : tmp;

        tList.clear();
        if(tList.size()<20)
            tList.addAll(tmp);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list , menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // User chose the "Settings" item, show the app settings UI...
                Intent i = new Intent(MainActivity.this,MainActivity.class);
                startActivity(i);
                return true;

            case R.id.quit:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                tList.clear();
                finishAffinity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void refreshFavItems() {
        BuildListView();
    }
}
