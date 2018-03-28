package com.example.monic.musicsearch;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by monic on 10/14/2017.
 */

public class RequestParam {
    String baseUrl;
    String methodName;
    String trackName;
    String artistName;
    String apiKey = "126e23ae1007e4d569a0be31bd5fcaf7";
    String limit;

    public RequestParam() {
    }

    public RequestParam(String baseUrl, String methodName, String trackName, String artistName) {
        this.baseUrl = baseUrl;
        this.methodName = methodName;
        this.trackName = trackName;
        this.artistName = artistName;
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();
        try {
            if (methodName.equals("track.search") & artistName.equals("")) {
                limit = "20";
                sb.append("&method=" + URLEncoder.encode(methodName, "UTF-8"));
                sb.append("&track=" + URLEncoder.encode(trackName, "UTF-8"));
                sb.append("&api_key=" + URLEncoder.encode(apiKey, "UTF-8"));
                sb.append("&limit=" + URLEncoder.encode(limit, "UTF-8"));
            } else {
                limit = "10";
                sb.append("&method=" + URLEncoder.encode(methodName, "UTF-8"));
                sb.append("&artist=" + URLEncoder.encode(artistName, "UTF-8"));
                sb.append("&track=" + URLEncoder.encode(trackName, "UTF-8"));
                sb.append("&api_key=" + URLEncoder.encode(apiKey, "UTF-8"));
                sb.append("&limit=" + URLEncoder.encode(limit, "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getEncodedUrl() {
        return baseUrl + getEncodedParams();
    }
}