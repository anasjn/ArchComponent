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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.adapters.FavouriteAdapter;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.model.LocationListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * LocationFragment extends LifecycleFragment and implements LocationListener, OnMapReadyCallback
 * <p>
 * This is the fragment that is in charge of the list of stops near the location of the user.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class LocationFragment extends LifecycleFragment implements LocationListener, OnMapReadyCallback {

    private GoogleMap gMap = null;
    private Marker mDefault;
    private LatLng mDefaultLocation = null;

    //To show in the map the markers for the list of stops location near me
    protected DataAdapter mAdapter = new DataAdapter(getContext());
    //To show in the map the markers for the list of favourites stops
    protected FavouriteAdapter mFAvouritesAdapter = new FavouriteAdapter(new ArrayList<ArrivalsFormatedEntity>(),getContext());


    //to show a list of markers in the map and adjust the zoom for the list of markers
    ArrayList<Marker> markers =  new ArrayList<Marker>();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();


    private UnifiedModelView unifiedModelView;

    /**
     * onCreateView Method
     * <p>
     * In this method we set the GoogleMap in order to alocate the user and the Stops or Favourites points
     * <p>
     *
     * @param   inflater   LayoutInflater
     * @param   container  ViewGroup
     * @param   savedInstanceState  Bundle
     * @return a View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        //set the map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }


    /**
     * onMapReady Method
     * <p>
     * This method is called when the map is ready in order work with it, as adding markers and moving the camera
     * <p>
     *
     * @param   googleMap   GoogleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap){
//        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++onMapReady");
        gMap = googleMap;
        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setAllGesturesEnabled(true);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        // If the GPS is disabled, generate a custom location around london
        if ( !unifiedModelView.isGPSEnabled() ) {
            unifiedModelView.setRandomLocationLiveData(getContext());
        }

        // Handle changes emitted by LiveDataLocation
        unifiedModelView.getLmLocationLiveData().observe(this,
                defaultLocation -> updateLocation(defaultLocation, true)
        );

        Bundle args = getArguments();
        if(args!=null){
            unifiedModelView.getmLiveDataFavourites().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
                @Override
                public void onChanged(@Nullable List<ArrivalsFormatedEntity> arrivalsFormatedEntities) {
                    handleResponseFavourites(arrivalsFormatedEntities);
                }
            });
        }else {
            // Handle changes emitted by StopPointMutableLiveData
            unifiedModelView.getmStopPointMutableLiveData().observe(this, new Observer<StopLocationEntity>() {
                @Override
                public void onChanged(@Nullable StopLocationEntity stopLocationEntity) {
                    handleResponseNearMe(stopLocationEntity.getStopPoints());
                }
            });
        }
    }


    /**
     * This Method handle the response, giving the List<StopPointsEntity> to the adapter.
     * <p>
     *
     * @param   stoppoints   List<StopPointsEntity>
     */
    private void handleResponseNearMe(List<StopPointsEntity> stoppoints) {
        markers =  new ArrayList<Marker>();
        builder = new LatLngBounds.Builder();
        String name;
        if (stoppoints != null && !stoppoints.isEmpty()) {
            if(mAdapter!=null) {
                mAdapter.addStopInformation(stoppoints);
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
        } else {
            mAdapter.clearStopInformation();
        }
        if(markers!=null & markers.size()>0) {
            for (int j = 0; j < markers.size(); j++) {
                builder.include(markers.get(j).getPosition());
            }
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(),16));
        }
    }

    /**
     * This Method handle the response, giving the List<ArrivalsFormatedEntity> to the adapter.
     * <p>
     *
     * @param   lines   List<ArrivalsFormatedEntity>
     */
    private void handleResponseFavourites(List<ArrivalsFormatedEntity> lines) {
        markers =  new ArrayList<Marker>();
        builder = new LatLngBounds.Builder();
        String name;
        if (lines != null && !lines.isEmpty()) {
            if(mFAvouritesAdapter!=null) {
                mFAvouritesAdapter.addFavourites(lines);
                for (int i = 0; i < lines.size(); i++) {
                    name="";
                    if(lines.get(i).getPlatformName()!=null && !lines.get(i).getPlatformName().isEmpty()){
                        name = lines.get(i).getPlatformName();
                    }
                    if(name.isEmpty() && lines.get(i).getStationName()!=null){
                        name = lines.get(i).getStationName();
                    }
                    if(lines.get(i).getLineId()!=null && !lines.get(i).getLineId().isEmpty()){
                        name += " - "+lines.get(i).getLineId();
                    }
                    if(lines.get(i)!=null && lines.get(i).getLatitude()!=null && lines.get(i).getLongitude()!=null)
                    {
                        DefaultLocation location = new DefaultLocation( Double.parseDouble(lines.get(i).getLatitude()), Double.parseDouble(lines.get(i).getLongitude()),name);
                        updateLocation(location,false);
                    }
                }
            }
        } else {
            mFAvouritesAdapter.clearFavourites();
        }
        if(markers!=null & !markers.isEmpty()) {
            for (int j = 0; j < markers.size(); j++) {
                builder.include(markers.get(j).getPosition());
            }
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(),16));
        }
    }
    /**
     * This Method add markers to a list (the location and the nearme stops markers)
     * <p>
     *
     * @param   defaultLocation   DefaultLocation (lat, long and name information of one location)
     * @param   mylocation   boolean if this is the location of the user or one stop because the marker added is going to be different.
     */
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
                if(mDefault!=null) {
                    mDefault.setTag(defaultLocation.getName());
                    markers.add(mDefault);
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(defaultLocation.getLatitude(), defaultLocation.getLongitude()), 16));
                }
            }
        }
    }
}
