package com.example.monic.moviesearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        String titleName = getIntent().getExtras().getString("title");
        String mOverview = getIntent().getExtras().getString("overview");
        String relDate = getIntent().getExtras().getString("releaseDate");
        String imageUrl = getIntent().getExtras().getString("imageUrl");
        String mRating = getIntent().getExtras().getString("rating") + "/ 10";

        TextView title = (TextView) findViewById(R.id.titleValue);
        TextView overview = (TextView) findViewById(R.id.name);
        TextView rDate = (TextView) findViewById(R.id.date);
        TextView rating = (TextView) findViewById(R.id.rating);
        ImageView trackImage = (ImageView) findViewById(R.id.trackImage);

        title.setText(titleName);
        overview.setText(mOverview);
        rDate.setText(relDate);
        rating.setText(mRating);
        Picasso.with(MovieDetails.this).load(imageUrl).into(trackImage);
    }
}
