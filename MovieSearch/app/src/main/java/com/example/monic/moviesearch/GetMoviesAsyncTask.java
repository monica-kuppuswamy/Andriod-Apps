package com.example.monic.moviesearch;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by monic on 10/16/2017.
 */

public class GetMoviesAsyncTask extends AsyncTask<String, Integer, ArrayList<Movie>> {
    IData activity;

    ProgressBar progressBar;
    Context mContect;

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    public GetMoviesAsyncTask(IData activity) {
        this.activity = activity;
        mContect = (Context) activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressBar(mContect);
        progressBar.setMax(100);
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        StringBuilder sb = new StringBuilder();
        int count=0;
        try {

            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = reader.readLine();
            publishProgress(25);
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            publishProgress(75);
            return ParseMovies.MovieJSONParser.parseMovies(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Movie> s) {
        super.onPostExecute(s);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.INVISIBLE);
        activity.setupData(s, false, false, false);
    }

    public interface IData {
        void setupData(ArrayList<Movie> s, boolean sortByRating, boolean sortByPopularity, boolean showFav);
    }
}
