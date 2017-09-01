package com.pfc.android.archcomponent.repository;


import android.util.Log;

import com.pfc.android.archcomponent.api.TflApiService;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ana on 29/08/17.
 */

public class RemoteRepositoryImpl implements RemoteRepository{
    public static final String TAG = RemoteRepositoryImpl.class.getSimpleName();

    public static final String BASE_URL = "https://api.tfl.gov.uk/";
    private TflApiService mApiService;


    public RemoteRepositoryImpl() {
        // Set the custom client when building adapter
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        Log.v(TAG,"retrofit "+retrofit.baseUrl());
        mApiService = retrofit.create(TflApiService.class);
        Log.v(TAG,"mApiService "+mApiService.toString());
    }

    public void getStopLocation(String app_id, String app_key, double lat, double lon, int radius, Callback<StopLocationEntity> callback) {
        Call<StopLocationEntity> call = mApiService.getStopLocation(app_id,app_key,lat, lon, radius);
        call.enqueue(callback);
    }


    public void getArrivalInformation(String naptanId, String app_id, String app_key, Callback<List<ArrivalsEntity>> callback) {
        Call<List<ArrivalsEntity>> call = mApiService.getArrivalInformation(naptanId,app_id,app_key);
        call.enqueue(callback);
    }

    public void getPredictionsByStopPLine(String app_id, String app_key,String lineId, String naptanId, String direction, Callback<List<ArrivalsEntity>> callback){
        Log.v(TAG,"app_id "+app_id+"app_key" +app_key+"lineId"+lineId+"naptanId"+naptanId+"direction"+direction);
        Call<List<ArrivalsEntity>> call = mApiService.getPredictionsByStopPLine(app_id,app_key,lineId,naptanId,direction);
        Log.v(TAG,"app_id "+app_id+"app_key" +app_key+"lineId"+lineId+"naptanId"+naptanId+"direction"+direction);
        call.enqueue(callback);
    }
}
