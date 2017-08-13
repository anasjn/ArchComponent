package com.pfc.android.archcomponent.model;

/**
 * Created by dr3amsit on 31/07/17.
 */

public class DefaultLocation {
    private final double longitude;
    private final double latitude;
    private final int accuracy;

    public DefaultLocation(double latitude, double longitude, int accuracy) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.accuracy = accuracy;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

}
