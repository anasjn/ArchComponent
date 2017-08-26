package com.pfc.android.archcomponent.util;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
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

import java.util.Random;


/**
 * Created by dr3amsit on 31/07/17.
 */

public class LocationLiveData extends MutableLiveData<DefaultLocation> {

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
        LocationRequest locationRequest = LocationRequest.create();
        Looper looper = Looper.myLooper();
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, looper);
    }

    @NonNull
    private FusedLocationProviderClient getFusedLocationProviderClient() {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
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
            int accuracy = (int) newLocation.getAccuracy();
//            DefaultLocation location = new DefaultLocation(latitude, longitude, accuracy);
            DefaultLocation location = randomgeolocation();
            Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ LocationCallback location " +location );
            if(location!=null) {
                setValue(location);
            }
        }
    };

    //Getting latitude and longitude of two corners of London Map (one superior-right corner and inferior-left corner). With these 2 points we calculeta a random locations.
    private DefaultLocation randomgeolocation(){
        Random r = new Random();
        DefaultLocation location = null;
        Double lonMin = -0.4016876220703125 ;
        Double latMin = 51.406701891531576;
        Double latMax = 51.54943078;
        Double lonMax = 0.006694793;
        double randomLatValue = latMin + (latMax - latMin) * r.nextDouble();
        double randomLonValue = lonMin + (lonMax - lonMin) * r.nextDouble();
        Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++randomLatValue " +randomLatValue );
        Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++randomLonValue " +randomLonValue );

        randomLatValue=51.50083830777585;
        randomLonValue=-0.09180195654082074;
        return location = new DefaultLocation(randomLatValue, randomLonValue, 200);
    }
}
