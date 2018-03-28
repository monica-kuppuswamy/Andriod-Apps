package com.example.monic.foodrecipe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by monic on 10/2/2017.
 */

public class RequestParam {
    String baseUrl;
    HashMap<String, String> params = new HashMap<String, String>();

    public RequestParam() {

    }

    public RequestParam(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    void addParams(String key, String value) {
        params.put(key, value);
    }

    public String getEncodedParams() {
        StringBuilder sb = new StringBuilder();
        String value;
        for(String key : params.keySet()) {
            try {
                value = URLEncoder.encode(params.get(key), "UTF-8");
                if(key.equals("Dish")) {
                    continue;
                } else {
                    value = URLEncoder.encode(params.get(key), "UTF-8");
                    sb.append(value);
                    if(sb.length() > 0) {
                        sb.append(",");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public String getEncodedUrl() {
        return baseUrl + getEncodedParams().substring(0, getEncodedParams().length() - 1) + "&q=" + params.get("Dish");
    }
}