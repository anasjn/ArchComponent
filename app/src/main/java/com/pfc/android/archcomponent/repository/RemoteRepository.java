package com.pfc.android.archcomponent.repository;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by ana on 29/08/17.
 */

public interface RemoteRepository {
    void getStopLocation(String app_id, String app_key, double lat, double lon, int radius, Callback<StopLocationEntity> callback);
    void getArrivalInformation(String naptanId, String app_id, String app_key, Callback<List<ArrivalsEntity>> callback);
    void getPredictionsByStopPLine(String app_id, String app_key,String lineId, String naptanId, String direction, Callback<List<ArrivalsEntity>> callback);
}
