package com.pfc.android.archcomponent.model;

/**
 * Created by dr3amsit on 31/07/17.
 */

public class DefaultLocation {
    private final double longitude;
    private final double latitude;
    private final float accuracy;

    public DefaultLocation(double longitude, double latitude, float accuracy) {
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
