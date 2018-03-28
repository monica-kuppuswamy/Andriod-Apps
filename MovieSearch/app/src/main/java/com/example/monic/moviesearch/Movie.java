package com.example.monic.moviesearch;

import android.support.annotation.NonNull;

/**
 * Created by monic on 10/16/2017.
 */

public class Movie implements Comparable{
    String title;
    String overview;
    String rDate;
    Double rating;
    Double popularity;
    String imgUrl;

    public Movie() {

    }
    public Movie(String title, String overview, String rDate, Double rating, Double popularity, String imgUrl) {
        this.title = title;
        this.overview = overview;
        this.rDate = rDate;
        this.rating = rating;
        this.popularity = popularity;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getrDate() {
        return rDate;
    }

    public void setrDate(String rDate) {
        this.rDate = rDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
