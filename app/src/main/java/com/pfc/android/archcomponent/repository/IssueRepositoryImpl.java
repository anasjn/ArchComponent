package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.pfc.android.archcomponent.api.StopPointsEntity;
import com.pfc.android.archcomponent.api.TflApiService;
import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.api.StopLocationEntity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.radius;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class IssueRepositoryImpl implements IssueRepository {

    public static final String TAG = IssueRepositoryImpl.class.getSimpleName();

    public static final String BASE_URL = "https://api.tfl.gov.uk/";
    private TflApiService mApiService;

    public IssueRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApiService = retrofit.create(TflApiService.class);
        Log.v(TAG,retrofit.toString());
    }

    public LiveData<ApiResponse> getStopLocation(double lat,double lon, int radius) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<StopLocationEntity> call = mApiService.getStopLocation(lat, lon, radius);
        Log.v(TAG,"getStopLocation __________________________ " + call.request().url());
        call.enqueue(new Callback<StopLocationEntity>() {
            @Override
            public void onResponse(Call<StopLocationEntity> call,Response<StopLocationEntity> response) {
                Log.v(TAG,"onResponse "+response.body().getStopPoints().size());
                ApiResponse ap=new ApiResponse((List<StopPointsEntity>) response.body().getStopPoints());
                Log.v(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++++++ap"+ap.getStopLocation().size());
                liveData.setValue(ap);
                Log.v(TAG, "liveData 1: " +liveData.getValue().getStopLocation().size());
            }

            @Override
            public void onFailure(Call<StopLocationEntity> call, Throwable t) {
                Log.v(TAG,"onFailure  setValue" + mApiService.toString());
                liveData.setValue(new ApiResponse(t));
            }
        });
        Log.v(TAG, "liveData " +liveData.getValue());
        return liveData;
    }
}
