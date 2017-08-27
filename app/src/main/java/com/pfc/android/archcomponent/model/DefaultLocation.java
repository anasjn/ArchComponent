package com.pfc.android.archcomponent.model;

/**
 * Created by dr3amsit on 31/07/17.
 */

public class DefaultLocation {
    private final double longitude;
    private final double latitude;
    private final String name;


    public DefaultLocation(double latitude, double longitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }
    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getName() {
        return name;
    }

}
