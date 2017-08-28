package com.pfc.android.archcomponent.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ana on 16/08/17.
 */

public class ApiResponse2 {

    private final String TAG = ApiResponse2.class.getName();

    private static List<ArrivalsEntity> arrivals;
    private static List<ArrivalsFormatedEntity> arrivalsformated = null;
    private Throwable error;

    public ApiResponse2(List<ArrivalsEntity> arrivals) {
        if (arrivals!=null && arrivals.size() > 0) {

            ArrayList<Integer> listtimes = new ArrayList<Integer>();
            arrivalsformated = new ArrayList<>();

            listtimes.add(secondsToMinutes(arrivals.get(0).getTimeToStation()));
            String lineId = arrivals.get(0).getLineId();
            ArrivalsFormatedEntity aformated = new ArrivalsFormatedEntity(arrivals.get(0).get$type(),lineId , arrivals.get(0).getStopLetter(), arrivals.get(0).getStationName(), arrivals.get(0).getPlatformName(), arrivals.get(0).getDestinationName(),listtimes);
            arrivalsformated.add(aformated);

            List<Integer> times = null;
            int j = 0;
            String aux="";
            ArrivalsEntity arrivalAux=null;
            int position = 0;
            int timesInMinutes = 0;
            Log.v(TAG, "+++++++++++++++++++++++++++arrivals: " + arrivals.size());
            Log.v(TAG, "+++++++++++++++++++++++++++arrivalsformated: " + arrivalsformated.size());
            for (int i = 1; i < arrivals.size(); i++) {
                arrivalAux = arrivals.get(i);
                lineId = arrivalAux.getLineId();
                Log.v(TAG, "+++++++++++++++++++++++++++lineId: " + lineId+ "-Iteraci'on"+i);
                if(arrivalsformated.size()>0) {
                    position = arrivalsformated.size() - 1;
                }
                aux=arrivalsformated.get(position).getLineId();
                Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Aux.... " + aux);
                timesInMinutes = secondsToMinutes(arrivalAux.getTimeToStation());
                if (lineId.equals(aux)) {
                        times = arrivalsformated.get(position).getTimeToStation();
                        times.add(timesInMinutes);
                        arrivalsformated.get(position).setTimeToStation(times);
                    }else {
                        listtimes = new ArrayList<Integer>();
                        listtimes.add(timesInMinutes);
                        aformated = new ArrivalsFormatedEntity(arrivalAux.get$type(), arrivalAux.getLineId(), arrivalAux.getStopLetter(), arrivalAux.getStationName(), arrivalAux.getPlatformName(), arrivalAux.getDestinationName(), listtimes);
                        arrivalsformated.add(aformated);
                    }
            }
            Log.v(TAG, "+++++++++++++++++++++++++++arrivals: " + arrivals.size());
            Log.v(TAG, "+++++++++++++++++++++++++++arrivalsformated: " + arrivalsformated.size());
            this.arrivals = arrivals;
            error = null;
        }
    }

    private int secondsToMinutes(int seconds){
        int minutes = 0;
        minutes = seconds/60;
        return minutes;
    }

    public ApiResponse2(Throwable error) {
        this.error = error;
        this.arrivals = null;
        this.arrivalsformated = null;
    }

    public static List<ArrivalsEntity> getArrivals2() {
        return arrivals;
    }

    public static List<ArrivalsFormatedEntity> getArrivals() {
        return arrivalsformated;
    }

    public static ArrivalsEntity getArrival2(int position) {
        return arrivals.get(position);
    }

    public static ArrivalsFormatedEntity getArrival(int position) {
        return arrivalsformated.get(position);
    }

    public Throwable getError() {
        return error;
    }
}
