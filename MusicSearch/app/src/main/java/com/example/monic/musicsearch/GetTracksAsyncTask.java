package com.example.monic.musicsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
 * Created by monic on 10/14/2017.
 */

public class GetTracksAsyncTask extends AsyncTask<String, Integer, ArrayList<Track>> {
    IData activity;

    ProgressBar progressBar;
    Context mContect;

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0]);
    }

    public GetTracksAsyncTask(IData activity) {
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
    protected ArrayList<Track> doInBackground(String... params) {
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
            return ParseTracks.TrackJSONParser.parseTracks(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<Track> s) {
        super.onPostExecute(s);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.INVISIBLE);
        activity.setupData(s);
    }

    public interface IData {
        void setupData(ArrayList<Track> s);
    }
}