package com.pfc.android.archcomponent.ui;

import com.pfc.android.archcomponent.R;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.lifecycle.LiveData;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.model.LocationListener;
import com.pfc.android.archcomponent.viewmodel.LocationViewModel;

import com.google.android.gms.maps.SupportMapFragment;

import java.util.Locale;


/**
 * Created by dr3amsit on 31/07/17.
 */
public class LocationFragment extends LifecycleFragment implements LocationListener,OnMapReadyCallback {

    public static final String TAG = LocationFragment.class.getName();

    private static final String FRACTIONAL_FORMAT = "%.4f";
    private static final String ACCURACY_FORMAT = "%.1fm";

    private GoogleMap gMap;
    private Marker mDefault;
    private LatLng mDefaultLocation = null;
    private DefaultLocation defaultLocation = null;
    LiveData<DefaultLocation> liveData = null;


    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        LocationViewModel lViewModel =  ViewModelProviders.of(this).get(LocationViewModel.class);
        liveData = lViewModel.getLocation(context);
        liveData.observe(this,new Observer <DefaultLocation>(){
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation){
                updateLocation(defaultLocation);
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
         return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap){
        gMap = googleMap;
        if(defaultLocation == null){
            defaultLocation = new DefaultLocation(-0.118092, 51.509865,200);
        }
        updateLocation(defaultLocation);

    }


    private void addMarkers(DefaultLocation defaultLocation){
        if(defaultLocation!=null) {
            mDefaultLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
            mDefault = gMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("I am here"));
            mDefault.setTag(0);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(mDefaultLocation));
        }

    }

    @Override
    public void updateLocation(DefaultLocation defaultLocation) {
        String latitudeString = createFractionString(defaultLocation.getLatitude());
        String longitudeString = createFractionString(defaultLocation.getLongitude());
        String accuracyString = createAccuracyString(defaultLocation.getAccuracy());
        //just to have more information in the map
        accuracyString = "200";
        defaultLocation = new DefaultLocation(Double.parseDouble(latitudeString),Double.parseDouble(longitudeString),Integer.parseInt(accuracyString));
        addMarkers(defaultLocation);
    }

    private String createFractionString(double fraction) {
        return String.format(Locale.getDefault(), FRACTIONAL_FORMAT, fraction);
    }

    private String createAccuracyString(float accuracy) {
        return String.format(Locale.getDefault(), ACCURACY_FORMAT, accuracy);
    }
}
