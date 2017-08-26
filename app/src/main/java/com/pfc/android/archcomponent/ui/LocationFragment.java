package com.pfc.android.archcomponent.ui;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
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

import java.util.ArrayList;
import java.util.List;
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
    private LatLng mLocation = null;
    private DefaultLocation defaultLocation = null;
    LiveData<DefaultLocation> liveData = null;
    //To show in the map the markers for the list of stops location near me
    protected DataAdapter mAdapter = new DataAdapter(getContext());

    //to show a list of markers in the map and adjust the zoom for the list of markers
    ArrayList<Marker> markers = new ArrayList<Marker>();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//    private ListLocationsViewModel mViewModel;
//    private AddFavouriteViewModel afViewModel;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        //user and password
        String app_id=getString(R.string.api_transport_id);
        String app_key=getString(R.string.api_transport_key);

        LocationViewModel lViewModel =  ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        //To show in the map the markers for the list of stops location near me
        ListLocationsViewModel mViewModel = ViewModelProviders.of(getActivity()).get(ListLocationsViewModel.class);
        //AddFavouriteViewModel afViewModel = ViewModelProviders.of(getActivity()).get(AddFavouriteViewModel.class);

        liveData = lViewModel.getLocation(context);
        liveData.observe(this,new Observer <DefaultLocation>(){
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation){
                updateLocation(defaultLocation, true);
                mViewModel.loadStopInformation(app_id,app_key,defaultLocation.getLatitude(),defaultLocation.getLongitude(), (int) defaultLocation.getAccuracy());
                //mViewModel.loadStopInformation(app_id,app_key,51.509865,-0.118092,200);

            }
        });

        // Handle changes emitted by LiveData
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                Log.v(TAG, "handleResponse++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++apiResponse.error ");
                handleError(apiResponse.getError());
            } else {
                Log.v(TAG, "handleResponse++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++apiResponse.getStopLocation ");
                handleResponse(apiResponse.getStopLocation());
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

    private void handleResponse(List<StopPointsEntity> stoppoints) {
        if (stoppoints != null && stoppoints.size()>0) {
            Log.v(TAG, "handleResponse++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+stoppoints.size() );
            if(mAdapter!=null) {
                mAdapter.addStopInformation(stoppoints);
                    Log.v(TAG, "stoppoints++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+stoppoints );
                    if(stoppoints!=null & stoppoints.size()>0) {
                        for (int i = 0; i < stoppoints.size(); i++) {
                            Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  stoppoints. " + stoppoints.get(i).getNaptanId());
                            updateLocation(new DefaultLocation(Double.parseDouble(stoppoints.get(i).getLat()), Double.parseDouble(stoppoints.get(i).getLon()), 200),false);
                        }
                    }
                if(markers!=null) {
                    for (int j = 0; j < markers.size(); j++) {
                        builder.include(markers.get(j).getPosition());
                    }
                }
                gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));
            }
        } else {
            mAdapter.clearStopInformation();
            Toast.makeText(
                    getContext(),
                    "No stop information found for the searched repository.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        mAdapter.clearStopInformation();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(getContext(), "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMapReady(final GoogleMap googleMap){
        gMap = googleMap;
        if(defaultLocation!=null) {
            updateLocation(defaultLocation, true);
            if(markers!=null) {
                for (int i = 0; i < markers.size(); i++) {
                    builder.include(markers.get(i).getPosition());
                }
            }
            gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(),50));
        }
    }


    @Override
    public void updateLocation(DefaultLocation defaultLocation, boolean mylocation) {
        Log.v(TAG, "++++++++++++++++++++++++++++++defaultLocation++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+defaultLocation );
        if(defaultLocation!=null) {
            if(mylocation == true) {
                //this is my current location
                mDefaultLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
                Log.v(TAG, "mDefaultLocation++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+mDefaultLocation );
                mDefault = gMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("I am here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mDefault.setTag(0);
            }else{
                //other markers that are not my location
                mLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
                mDefault = gMap.addMarker(new MarkerOptions().position(mLocation));
                mDefault.setTag(0);
            }
            markers.add(mDefault);
        }
    }
}
