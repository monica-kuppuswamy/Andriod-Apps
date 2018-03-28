package com.example.monic.musicsearch;

/**
 * Created by monic on 10/14/2017.
 */

public class Track {
    String name;
    String artist;
    String siteUrl;
    String imgUrl;

    public Track() {

    }

    public Track(String name, String artist, String siteUrl, String imgUrl) {
        this.name = name;
        this.artist = artist;
        this.siteUrl = siteUrl;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Track{" +
                "name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
