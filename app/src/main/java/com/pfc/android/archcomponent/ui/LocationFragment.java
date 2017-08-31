package com.pfc.android.archcomponent.ui;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.ui.IconGenerator;
import com.pfc.android.archcomponent.R;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.model.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dr3amsit on 31/07/17.
 */
public class LocationFragment extends LifecycleFragment implements LocationListener, OnMapReadyCallback {

    public static final String TAG = LocationFragment.class.getName();

    private GoogleMap gMap = null;
    private Marker mDefault;
    private LatLng mDefaultLocation = null;

    //To show in the map the markers for the list of stops location near me
    protected DataAdapter mAdapter = new DataAdapter(getContext());

    //to show a list of markers in the map and adjust the zoom for the list of markers
    ArrayList<Marker> markers =  new ArrayList<Marker>();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();


    private UnifiedModelView unifiedModelView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        //set the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(final GoogleMap googleMap){
        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++onMapReady");
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setAllGesturesEnabled(true);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        //set current location
        unifiedModelView.setLmLocationLiveData(getContext());

        // Handle changes emitted by LiveDataLocation
        unifiedModelView.getLmLocationLiveData().observe(this, new Observer<DefaultLocation>() {
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation) {
                updateLocation(defaultLocation, true);
            }
        });

        // Handle changes emitted by StopPointMutableLiveData
        unifiedModelView.getmStopPointMutableLiveData().observe(this, new Observer<StopLocationEntity>() {
            @Override
            public void onChanged(@Nullable StopLocationEntity stopLocationEntity) {
                handleResponse((List<StopPointsEntity>) stopLocationEntity.getStopPoints());
            }
        });
    }



    private void handleResponse(List<StopPointsEntity> stoppoints) {
        markers =  new ArrayList<Marker>();
        builder = new LatLngBounds.Builder();
        String name;
        if (stoppoints != null && stoppoints.size()>0) {
            if(mAdapter!=null) {
                mAdapter.addStopInformation(stoppoints);
                if(stoppoints!=null & stoppoints.size()>0) {
                    for (int i = 0; i < stoppoints.size(); i++) {
                        name="";
                        if(stoppoints.get(i).getStopLetter()!=null && !stoppoints.get(i).getStopLetter().isEmpty()){
                            name = stoppoints.get(i).getStopLetter();
                        }
                        if(name.isEmpty() && stoppoints.get(i).getCommonName()!=null){
                            name = stoppoints.get(i).getCommonName();
                        }
                        updateLocation(new DefaultLocation(Double.parseDouble(stoppoints.get(i).getLat()), Double.parseDouble(stoppoints.get(i).getLon()), name),false);
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
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(),16));
        }
    }


    @Override
    public void updateLocation(DefaultLocation defaultLocation, boolean mylocation) {
        if(defaultLocation!=null) {
            if(gMap!=null) {
                IconGenerator iconFactory = new IconGenerator(getContext());
                mDefaultLocation = new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude());
                MarkerOptions opt= new MarkerOptions();
                if(mylocation == false) {
                    //other markers that are not my location
                    iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
                    opt.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(defaultLocation.getName())));
                }
                mDefault = gMap.addMarker(opt.position(mDefaultLocation));
                mDefault.setTag(defaultLocation.getName());
                if(mDefault!=null) {
                    Log.v(TAG,"markers locations "+defaultLocation.getLatitude()+","+defaultLocation.getLongitude());
                    markers.add(mDefault);
                }
            }
        }
    }
}
