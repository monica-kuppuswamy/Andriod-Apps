package com.example.monic.tripapp;

import java.io.Serializable;

/**
 * Created by monic on 12/9/2017.
 */

public class Trips implements Serializable{
    String tripId;
    String tripName;
    String tripPlace;
    String tripPlaceId;
    String lat;
    String lng;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripPlace() {
        return tripPlace;
    }

    public void setTripPlace(String tripPlace) {
        this.tripPlace = tripPlace;
    }

    public String getTripPlaceId() {
        return tripPlaceId;
    }

    public void setTripPlaceId(String tripPlaceId) {
        this.tripPlaceId = tripPlaceId;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
