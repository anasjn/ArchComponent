package com.pfc.android.archcomponent.util;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.pfc.android.archcomponent.model.DefaultLocation;


/**
 * Created by dr3amsit on 31/07/17.
 */

public class LocationLiveData extends LiveData<DefaultLocation> {

    private final String TAG = LocationLiveData.class.getName();

    private final Context context;

    private FusedLocationProviderClient fusedLocationProviderClient = null;

    public LocationLiveData(Context context) {

        this.context = context;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();
        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FusedLocationProviderClient "+ locationProviderClient);
        LocationRequest locationRequest = LocationRequest.create();
        Looper looper = Looper.myLooper();
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, looper);
    }

    @NonNull
    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
            Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++getFusedLocationProviderClient "+ fusedLocationProviderClient);
        }
        return fusedLocationProviderClient;
    }

    @Override
    protected void onInactive() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location newLocation = locationResult.getLastLocation();
            double latitude = newLocation.getLatitude();
            double longitude = newLocation.getLongitude();
            float accuracy = newLocation.getAccuracy();
            DefaultLocation location = new DefaultLocation(latitude, longitude, accuracy);
            Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++location "+ location);
            setValue(location);
        }
    };
}
