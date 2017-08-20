package com.pfc.android.archcomponent.api;

import android.util.Log;

import com.pfc.android.archcomponent.vo.StopPointsEntity;

import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class ApiResponse {

    private final String TAG = ApiResponse.class.getName();

    private static List<StopPointsEntity> stoppoints;
    private Throwable error;

    public ApiResponse(List<StopPointsEntity> stoppoints) {
        if(stoppoints.size()>0) {
            Log.v(TAG, "constructor ApiResponse " + stoppoints.get(0).getStopLetter());
        }
        this.stoppoints = stoppoints;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.stoppoints = null;
    }

    public static List<StopPointsEntity> getStopLocation() {
        return stoppoints;
    }

    public static StopPointsEntity getStop(int position) {
        return stoppoints.get(position);
    }

    public Throwable getError() {
        return error;
    }

}
