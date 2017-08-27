package com.pfc.android.archcomponent.ui;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.ui.BubbleIconFactory;
import com.google.maps.android.ui.IconGenerator;
import com.pfc.android.archcomponent.R;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.lifecycle.LiveData;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.model.LocationListener;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;
import com.pfc.android.archcomponent.viewmodel.ListLocationsViewModel;
import com.pfc.android.archcomponent.viewmodel.LocationViewModel;

import com.google.android.gms.maps.SupportMapFragment;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by dr3amsit on 31/07/17.
 */
public class LocationFragment extends LifecycleFragment implements LocationListener, OnMapReadyCallback {

    public static final String TAG = LocationFragment.class.getName();

    private static final String FRACTIONAL_FORMAT = "%.4f";
    private static final String ACCURACY_FORMAT = "%.1fm";

    private GoogleMap gMap = null;
    private Marker mDefault;
    private LatLng mDefaultLocation = null;
    private LatLng mLocation = null;
    private DefaultLocation defaultLocation = null;
    LiveData<DefaultLocation> liveData = null;
    //To show in the map the markers for the list of stops location near me
    protected DataAdapter mAdapter = new DataAdapter(getContext());

    //to show a list of markers in the map and adjust the zoom for the list of markers
    ArrayList<Marker> markers =  new ArrayList<Marker>();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();

    private ListLocationsViewModel mViewModel;
    private LocationViewModel lViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Log.v(TAG, "onViewCreated++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ Async ");
        mapFragment.getMapAsync(this);
        return view;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap){
        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++onMapReady");
        gMap = googleMap;
        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++markers"+markers);
        lViewModel =  ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        //To show in the map the markers for the list of stops location near me
        mViewModel = ViewModelProviders.of(getActivity()).get(ListLocationsViewModel.class);

        liveData = lViewModel.getLocation(getContext());
        liveData.observe(this,new Observer <DefaultLocation>(){
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation){
                updateLocation(defaultLocation, true);
            }
        });

        // Handle changes emitted by LiveData
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getStopLocation());
            }
        });


    }

    private void handleResponse(List<StopPointsEntity> stoppoints) {
        markers =  new ArrayList<Marker>();
        builder = new LatLngBounds.Builder();
        Log.v(TAG, "handleResponse stoppoints "+stoppoints);
        //Log.v(TAG, "handleResponse markers "+markers.size());
        if (stoppoints != null && stoppoints.size()>0) {
            Log.v(TAG, "handleResponse++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+stoppoints.size() );
            if(mAdapter!=null) {
                mAdapter.addStopInformation(stoppoints);
                if(stoppoints!=null & stoppoints.size()>0) {
                    for (int i = 0; i < stoppoints.size(); i++) {
                        Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  stoppoints. " + stoppoints.get(i).getNaptanId());
                        updateLocation(new DefaultLocation(Double.parseDouble(stoppoints.get(i).getLat()), Double.parseDouble(stoppoints.get(i).getLon()), 200),false);
                    }
                }
            }
        } else {
            mAdapter.clearStopInformation();
            Toast.makeText(
                    getContext(),
                    "No stop information found for the searched repository.",
                    Toast.LENGTH_SHORT
            ).show();
        }
        if(markers!=null & markers.size()>0) {
            for (int j = 0; j < markers.size(); j++) {
                Log.v(TAG," markers positions handleResponse" +markers.get(j).getPosition());
                builder.include(markers.get(j).getPosition());
            }
            Log.v(TAG, "moveCamera++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ ");
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));
        }
    }

    private void handleError(Throwable error) {
        mAdapter.clearStopInformation();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(getContext(), "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateLocation(DefaultLocation defaultLocation, boolean mylocation) {
        Log.v(TAG, "updateLocation defaultLocation "+defaultLocation);
        if(defaultLocation!=null) {
            IconGenerator iconFactory = new IconGenerator(getContext());
            if(mylocation == true) {
                //this is my current location
                mDefaultLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
                iconFactory.setColor(Color.GREEN);
                Log.v(TAG, "gMap if "+gMap);
                if(gMap!=null) {
                    mDefault = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon("I'm here"))).position(mDefaultLocation));
                    mDefault.setTag(0);
                    if(mDefault!=null) {
                        markers.add(mDefault);
                    }
                    Log.v(TAG," markers if" +markers.size());
                }
            }else{
                //other markers that are not my location
                //iconFactory.setColor(Color);
                mLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
                Log.v(TAG, "gMap else "+gMap);
                if(gMap!=null) {
                    mDefault = gMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(""+new DecimalFormat("#.###").format(defaultLocation.getLatitude())+"&"+new DecimalFormat("#.###").format(defaultLocation.getLongitude())))).position(mLocation));
                    mDefault.setTag(0);
                    if(mDefault!=null) {
                        markers.add(mDefault);
                    }
                    Log.v(TAG," markers else " +markers.size());
                }
            }
        }
    }
}
