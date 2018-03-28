package com.example.monic.musicsearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchResults extends AppCompatActivity implements GetTracksAsyncTask.IData, TracksAdapter.ICallBack{

    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    final ArrayList trackList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String url = getIntent().getExtras().getString("url");
        new GetTracksAsyncTask(SearchResults.this).execute(url);
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
                Intent i = new Intent(SearchResults.this,MainActivity.class);
                startActivity(i);
                return true;

            case R.id.quit:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                finishAffinity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void setupData(ArrayList<Track> s) {
        final ArrayList<Track> tList = s;
        ListView listView = (ListView) findViewById(R.id.tracksList);
        TracksAdapter adapter = new TracksAdapter(this, R.layout.row_item, s);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String similarTracksUrl = "http://ws.audioscrobbler.com/2.0/?format=json";
                String method = "track.getsimilar";
                Intent i = new Intent(SearchResults.this, TrackDetails.class);
                i.putExtra("name", tList.get(position).getName());
                i.putExtra("artist", tList.get(position).getArtist());
                i.putExtra("trackUrl", tList.get(position).getSiteUrl());
                i.putExtra("imageUrl", tList.get(position).getImgUrl());
                i.putExtra("similarTrackUrl", new RequestParam(similarTracksUrl, method,
                        tList.get(position).getName(), tList.get(position).getArtist()).getEncodedUrl());
                startActivity(i);
            }
        });
    }

    @Override
    public void refreshFavItems() {

    }
}
