package com.pfc.android.archcomponent.repository;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;

import java.util.List;

import retrofit2.Callback;

/**
 * RemoteRepository is a base interface to connect with the API backend
 * <p>
 * RemoteRepository is an interface for managing the communication
 * between the ViewModel and the Remote backend: Unified Tfl API (https://api.tfl.gov.uk/)
 * using Retrofit.
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public interface RemoteRepository {
    void getStopLocation(double lat, double lon, int radius, Callback<StopLocationEntity> callback);
    void getArrivalInformation(String naptanId, Callback<List<ArrivalsEntity>> callback);
    void getPredictionsByStopPLine(String lineId, String naptanId, String direction, Callback<List<ArrivalsEntity>> callback);
}
