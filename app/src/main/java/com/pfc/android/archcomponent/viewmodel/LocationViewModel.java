package com.pfc.android.archcomponent.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.location.Location;

import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.util.LocationLiveData;

/**
 * Created by ana on 07/08/17.
 */

public class LocationViewModel extends ViewModel {

    private LiveData<DefaultLocation> locationLiveData = null;

    public LiveData<DefaultLocation> getLocation(Context context) {
        if (locationLiveData == null) {
            locationLiveData = new LocationLiveData(context);
        }
        return locationLiveData;
    }
}

