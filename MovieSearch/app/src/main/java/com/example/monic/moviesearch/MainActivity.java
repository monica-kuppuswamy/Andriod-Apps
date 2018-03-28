package com.example.monic.moviesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetMoviesAsyncTask.IData {

    final static String baseUrl = "https://api.themoviedb.org/3/search/movie?query=";
    EditText movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieName = (EditText) findViewById(R.id.edit1input);
        Button searchButton = (Button) findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieName.equals("") || movieName.equals(null)) {
                    Toast.makeText(MainActivity.this, "Enter movie to search", Toast.LENGTH_LONG).show();
                } else {
                    RequestParam requestParam = new RequestParam(baseUrl, movieName.getText().toString());
                    new GetMoviesAsyncTask(MainActivity.this).execute(requestParam.getEncodedUrl());
                }
            }
        });
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
            case R.id.sortByRating:
                // User chose the "Settings" item, show the app settings UI...
                RequestParam requestParam = new RequestParam(baseUrl, movieName.getText().toString());
                new GetMoviesAsyncTask(MainActivity.this).execute(requestParam.getEncodedUrl());
                return true;

            case R.id.sortByPop:
                RequestParam requestParam1 = new RequestParam(baseUrl, movieName.getText().toString());
                new GetMoviesAsyncTask(MainActivity.this).execute(requestParam1.getEncodedUrl());
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

    //sort by rating
    public static Comparator<Movie> movieComparator = new Comparator<Movie>() {

        public int compare(Movie s1, Movie s2) {
            Double r1 = s1.getRating();
            Double r2 = s2.getRating();

            //ascending order
            return r1.compareTo(r2);
        }};

    @Override
    public void setupData(ArrayList<Movie> s, boolean sortByRating, boolean sortByPopularity, boolean showFav) {

        if(sortByRating) {
            sortByPopularity = false;
            showFav = false;
            Collections.sort(s);
            ListView listView = (ListView) findViewById(R.id.movielist);
            MovieAdapter adapter = new MovieAdapter(this, R.layout.row_item, s, false);
            listView.setAdapter(adapter);
//            ArrayList<Movie> m = Arrays.asList(Arrays.sort(s.toArray()))
        } else {
            try {
                if(s.size() == 0) {
                    Toast.makeText(MainActivity.this, "No Movies to Display", Toast.LENGTH_LONG).show();
                } else {
                    final ArrayList<Movie> tList = s;
                    ListView listView = (ListView) findViewById(R.id.movielist);
                    MovieAdapter adapter = new MovieAdapter(this, R.layout.row_item, s, false);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent i = new Intent(MainActivity.this, MovieDetails.class);
                            i.putExtra("title", tList.get(position).getTitle());
                            i.putExtra("overview", tList.get(position).getOverview());
                            i.putExtra("releaseDate", tList.get(position).getrDate());
                            i.putExtra("imageUrl", tList.get(position).getImgUrl());
                            i.putExtra("rating", tList.get(position).getRating().toString());
                            startActivity(i);
                        }
                    });
                }
            } catch(Exception e) {
                Toast.makeText(MainActivity.this, "No Movies to Display", Toast.LENGTH_LONG).show();
            }
        }

    }
}
