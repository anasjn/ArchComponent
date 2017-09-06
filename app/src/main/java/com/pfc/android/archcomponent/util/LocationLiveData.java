package com.pfc.android.archcomponent.util;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.pm.PackageManager;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.pfc.android.archcomponent.model.DefaultLocation;

import java.util.Random;


/**
 * LocationLiveData extends MutableLiveData<DefaultLocation>
 * <p>
 * This class allow us to use the location of the user or the use a method in order to have a radom location in London.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class LocationLiveData extends MutableLiveData<DefaultLocation> {

    private final Context context;
    private FusedLocationProviderClient fusedLocationProviderClient = null;
    private DefaultLocation cachedLocation = null;

    public LocationLiveData(Context context) {
        this.context = context;
    }

    /**
     * This method review of the user has enough permissions in order to use the location.
     * <p>
     */
    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FusedLocationProviderClient locationProviderClient = getFusedLocationProviderClient();
        LocationRequest locationRequest = LocationRequest.create();
        Looper looper = Looper.myLooper();
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, looper);
    }

    /**
     * This method get the last location known.
     * <p>
     *
     * @return FusedLocationProviderClient
     */
    @NonNull
    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
        return fusedLocationProviderClient;
    }


    /**
     * This method remove LocationUpdates
     * <p>
     */
    @Override
    protected void onInactive() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    /**
     * This method set the location cached.
     * <p>
     *
     * @return LocationCallback
     */
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            cachedLocation = new DefaultLocation(locationResult.getLastLocation().getLatitude(),
                    locationResult.getLastLocation().getLongitude(), "I'm here");
            setValue(cachedLocation);
        }
    };

    /**
     * This method should be used then there is no gps enabled in the device and we need to test that all is working by
     * locating an user arround London.
     * <p>
     *
     */
    public void setRandomLocation() {
        if (cachedLocation == null) {
            setValue(randomGeolocation());
        }
    }

    /**
     * Method to get the latitude and longitude of two corners of London Map (one superior-right corner and inferior-left corner).
     * With these 2 points we calculate a random locations.
     * <p>
     *
     * @return  a DefaultLocation
     */
    private DefaultLocation randomGeolocation(){
        Random r = new Random();
        DefaultLocation location = null;
        Double lonMin = -0.4016876220703125 ;
        Double latMin = 51.406701891531576;
        Double latMax = 51.54943078;
        Double lonMax = 0.006694793;
        double randomLatValue = latMin + (latMax - latMin) * r.nextDouble();
        double randomLonValue = lonMin + (lonMax - lonMin) * r.nextDouble();
//        randomLatValue =51.43529544591915;
//        randomLonValue=-0.035237572972349696;
//        Log.v (TAG, "----------------------------------------MY POSITION "+randomLatValue+","+randomLonValue);
        return location = new DefaultLocation(randomLatValue, randomLonValue, "I'm here");
    }
}
