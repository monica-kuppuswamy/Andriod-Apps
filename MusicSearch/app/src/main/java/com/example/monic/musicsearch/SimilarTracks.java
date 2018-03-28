package com.example.monic.musicsearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by monic on 10/14/2017.
 */

public class SimilarTracks {
    static public class TrackJSONParser
    {
        static ArrayList<Track> parseTracks(String in) throws JSONException {

            ArrayList<Track> tracks = new ArrayList<Track>();

            JSONObject root = new JSONObject(in);
            JSONArray trackJsonArray = root.getJSONObject("similartracks").getJSONArray("track");
            for(int i = 0; i < trackJsonArray.length(); i++)
            {
                JSONObject trackJsonobject = trackJsonArray.getJSONObject(i);
                Track t = new Track();
                t.setName(trackJsonobject.getString("name"));
                t.setArtist(trackJsonobject.getJSONObject("artist").getString("name"));
                t.setSiteUrl(trackJsonobject.getJSONObject("artist").getString("url"));
                t.setImgUrl(trackJsonobject.getJSONArray("image").getJSONObject(1).getString("#text"));
                tracks.add(t);
            }
            return tracks;
        }
    }
}
