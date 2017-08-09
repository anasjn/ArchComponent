package com.pfc.android.archcomponent.api;

import android.util.Log;

import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class ApiResponse {

    private final String TAG = ApiResponse.class.getName();

    private List<StopPointsEntity> stoppoints;
    private Throwable error;

    public ApiResponse(List<StopPointsEntity> stoppoints) {
        Log.v(TAG,"constructor ApiResponse "+stoppoints.size());
        if(stoppoints.size()>0) {
            Log.v(TAG, "constructor ApiResponse " + stoppoints.get(0).getStopLetter());
        }
        this.stoppoints = stoppoints;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.stoppoints = null;
        Log.v(TAG, "ApiResponse ");
    }

    public List<StopPointsEntity> getStopLocation() {
        Log.v(TAG, "getStopLocation ");
        return stoppoints;
    }

    public Throwable getError() {
        Log.v(TAG, "getError "+error);
        return error;
    }

}
