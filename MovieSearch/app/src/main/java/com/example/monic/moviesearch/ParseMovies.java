package com.example.monic.moviesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by monic on 10/16/2017.
 */

public class ParseMovies {
    static public class MovieJSONParser
    {
        static ArrayList<Movie> parseMovies(String in) throws JSONException, UnsupportedEncodingException {

            String imgBaseUrl = "http://image.tmdb.org/t/p/" + URLEncoder.encode("w154", "UTF-8");
            ArrayList<Movie> movies = new ArrayList<Movie>();

            JSONObject root = new JSONObject(in);
            JSONArray trackJsonArray = root.getJSONArray("results");
            for(int i = 0; i < trackJsonArray.length(); i++)
            {
                JSONObject movieJsonobject = trackJsonArray.getJSONObject(i);
                Movie t = new Movie();
                t.setTitle(movieJsonobject.getString("original_title"));
                t.setOverview(movieJsonobject.getString("overview"));
                t.setrDate(movieJsonobject.getString("release_date"));
                t.setRating(Double.parseDouble(movieJsonobject.getString("vote_average")));
                t.setPopularity(Double.parseDouble(movieJsonobject.getString("popularity")));
                t.setImgUrl(imgBaseUrl + movieJsonobject.getString("poster_path"));
                movies.add(t);
            }
            return movies;
        }
    }
}
