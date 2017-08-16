package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.util.PermissionsRequester;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;


public class MainActivity extends LifecycleActivity {

    private final String TAG = MainActivity.class.getName();

    private PermissionsRequester permissionsRequester;
    private LocationFragment locationFragment;
    private View fragmentContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Location permissions check and fragment to use for location
        fragmentContainer = findViewById(R.id.fragment_container);
        permissionsRequester = PermissionsRequester.newInstance(this);


        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ListFragment fragment = new ListFragment();
            transaction.replace(R.id.content_fragment, fragment);
            transaction.commit();
        }
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
//        Log.v(TAG, "onStart +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
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

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
