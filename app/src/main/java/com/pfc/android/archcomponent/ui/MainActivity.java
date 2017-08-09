package com.pfc.android.archcomponent.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.api.StopPointsEntity;
import com.pfc.android.archcomponent.util.PermissionsRequester;
import com.pfc.android.archcomponent.viewmodel.ListLocationsViewModel;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;

import com.google.android.gms.location.LocationListener;

import java.util.List;


public class MainActivity extends LifecycleActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final String TAG = MainActivity.class.getName();

    private GoogleMap gMap;

    private ListLocationsViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private ProgressDialog mDialog;
    private DataAdapter mAdapter;
    private EditText mSearchEditText;

    private PermissionsRequester permissionsRequester;
    private LocationFragment locationFragment;
    private View fragmentContainer;


    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Location permissions check and fragment to use for location
        fragmentContainer = findViewById(R.id.fragment_container);
        permissionsRequester = PermissionsRequester.newInstance(this);


        //ListLocation for the radius sent: Tfl
//        mViewModel = ViewModelProviders.of(this).get(ListLocationsViewModel.class);
//        setupView();
//
//
//        mSearchEditText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                String repo = mSearchEditText.getText().toString();
//                if (repo.length() > 0) {
//                    String[] query = repo.split("/");
//                    if (query.length == 3) {
//                        Log.v(TAG, "3 ");
//                        hideSoftKeyboard(MainActivity.this, v);
//                        setProgress(true);
//                        mViewModel.loadStopInformation(Double.parseDouble(query[0]),Double.parseDouble(query[1]),Integer.parseInt(query[2]));
//                    } else {
//                        handleError(new Exception(
//                                "Error wrong format of input. Required format lat/lon/radious")
//                        );
//                    }
//                } else {
//                    handleError(new Exception(
//                            "Repository name empty. Required format lat/lon,radious")
//                    );
//                }
//                return true;
//            }
//            return false;
//        });
//
//        // Handle changes emitted by LiveData
//        mViewModel.getApiResponse().observe(this, apiResponse -> {
//            if (apiResponse.getError() != null) {
//                handleError(apiResponse.getError());
//                Log.v(TAG, "handleError distinto null ");
//            } else {
//                handleResponse(apiResponse.getStopLocation());
//                Log.v(TAG, "handleResponse ");
//            }
//        });

    }

    //Location
    private void createLocationFragment() {
        locationFragment = (LocationFragment) Fragment.instantiate(this, LocationFragment.class.getCanonicalName());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, locationFragment);
        transaction.commit();
    }

    //Location
    @Override
    protected void onStart() {
        super.onStart();
        if (!permissionsRequester.hasPermissions()) {
            permissionsRequester.requestPermissions();
        } else {
            createLocationFragment();
        }
    }

    //Location
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(fragmentContainer, R.string.no_permissions, Snackbar.LENGTH_LONG).show();
                finish();
                return;
            }
        }
        createLocationFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy ");
    }


    private void setupView() {
       Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++dentro setupView ");
//        mText1 = (TextView) findViewById(R.id.latitude_value);
//        mText2 = (TextView) findViewById(R.id.longitude_value);
//        mText3 = (TextView) findViewById(R.id.accuracy_value);

//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mSearchEditText = (EditText) findViewById(R.id.et_search);
//
//        // Setup Progress Dialog to show loading state
//        mDialog = new ProgressDialog(MainActivity.this);
//        mDialog.setIndeterminate(true);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mDialog.setTitle(getString(R.string.progress_title));
//        mDialog.setMessage(getString(R.string.progress_body));
//        mDialog.setCancelable(false);
//        mDialog.setCanceledOnTouchOutside(false);
//
//        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false)
//        );
//        mRecyclerView.hasFixedSize();
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
//                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL
//        );
//        mRecyclerView.addItemDecoration(mDividerItemDecoration);
//        mAdapter = new DataAdapter(getLayoutInflater());
//        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++setupView mAdapter " +mAdapter);
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void hideSoftKeyboard(Activity activity, View view) {
        Log.v(TAG, "dentro hideSoftKeyboard ");
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private void handleResponse(List<StopPointsEntity> stoppoints) {
        Log.v(TAG, "dentro handleResponse ");
        setProgress(false);
        if (stoppoints != null && stoppoints.size()>0) {
            Log.v(TAG,"hr stoopoints "+stoppoints.size());
            mAdapter.addStopInformation(stoppoints);
        } else {
            mAdapter.clearStopInformation();
            Toast.makeText(
                    this,
                    "No stop information found for the searched repository.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        Log.v(TAG, "dentro handleError ");
        setProgress(false);
        mAdapter.clearStopInformation();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(this, "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

    public void setProgress(boolean flag) {
        Log.v(TAG, "dentro setProgress "+flag);
        if (flag) {
            mDialog.show();
        } else {
            mDialog.dismiss();
        }
    }

}
