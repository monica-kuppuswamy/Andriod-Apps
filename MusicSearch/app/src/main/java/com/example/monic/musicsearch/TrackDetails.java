package com.example.monic.musicsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrackDetails extends AppCompatActivity implements GetSimilarTracksAsyncTask.IData, TracksAdapter.ICallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        String url = getIntent().getExtras().getString("trackUrl");
        String trackName = getIntent().getExtras().getString("name");
        String artist = getIntent().getExtras().getString("artist");
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        String similarTracks = getIntent().getExtras().getString("similarTrackUrl");

        TextView name = (TextView) findViewById(R.id.name);
        TextView artistName = (TextView) findViewById(R.id.artist);
        TextView trackUrl = (TextView) findViewById(R.id.url);
        ImageView trackImage = (ImageView) findViewById(R.id.trackImage);

        name.setText(trackName);
        artistName.setText(artist);
        trackUrl.setText(url);
        Picasso.with(TrackDetails.this).load(imageUrl).into(trackImage);

        new GetSimilarTracksAsyncTask(TrackDetails.this).execute(similarTracks);
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
                Intent i = new Intent(TrackDetails.this,MainActivity.class);
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
        ListView listView = (ListView) findViewById(R.id.similarTracks);
        TracksAdapter adapter = new TracksAdapter(this, R.layout.row_item, s);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(false);
    }

    @Override
    public void refreshFavItems() {

    }
}
