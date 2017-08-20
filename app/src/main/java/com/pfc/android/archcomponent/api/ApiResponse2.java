package com.pfc.android.archcomponent.api;

import android.util.Log;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;

import java.util.List;

/**
 * Created by ana on 16/08/17.
 */

public class ApiResponse2 {

    private final String TAG = ApiResponse2.class.getName();

    private static List<ArrivalsEntity> arrivals;
    private Throwable error;

    public ApiResponse2(List<ArrivalsEntity> arrivals) {
        if(arrivals.size()>0) {
            Log.v(TAG, "constructor ApiResponse2 " + arrivals.get(0).getVehicleId());
        }
        this.arrivals = arrivals;
        this.error = null;
    }

    public ApiResponse2(Throwable error) {
        this.error = error;
        this.arrivals = null;
    }

    public static List<ArrivalsEntity> getArrivals() {
        return arrivals;
    }

    public static ArrivalsEntity getArrival(int position) {
        return arrivals.get(position);
    }

    public Throwable getError() {
        return error;
    }
}
