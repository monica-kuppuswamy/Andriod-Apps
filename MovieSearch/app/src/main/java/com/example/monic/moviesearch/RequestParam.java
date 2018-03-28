package com.example.monic.moviesearch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by monic on 10/14/2017.
 */

public class RequestParam {
    String baseUrl;
    String movieName;
    String apiKey = "b0873710cc5d381a3b6e0e0134314c53";
    String page = "1";

    public RequestParam() {
    }

    public RequestParam(String baseUrl, String movieName) {
        this.baseUrl = baseUrl;
        this.movieName = movieName;
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();
        try {
            if (movieName.contains("")) {
                movieName.replace("", "+");
            }
            sb.append(URLEncoder.encode(movieName, "UTF-8"));
            sb.append("&api_key=" + URLEncoder.encode(apiKey, "UTF-8"));
            sb.append("&page=" + URLEncoder.encode(page, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getEncodedUrl() {
        return baseUrl + getEncodedParams();
    }
}