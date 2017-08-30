package com.pfc.android.archcomponent.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.pfc.android.archcomponent.api.ApiResponse2;
import com.pfc.android.archcomponent.api.ApiResponse3;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.db.FavouriteDao;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.FavouriteEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.api.TflApiService;
import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.vo.StopLocationEntity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class IssueRepositoryImpl implements IssueRepository {

    public static final String TAG = IssueRepositoryImpl.class.getSimpleName();

    public static final String BASE_URL = "https://api.tfl.gov.uk/";
    private TflApiService mApiService;


    public IssueRepositoryImpl() {
        // Set the custom client when building adapter
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mApiService = retrofit.create(TflApiService.class);

    }

    public MutableLiveData<ApiResponse> getStopLocation(String app_id, String app_key,double lat,double lon, int radius) {
        final MutableLiveData<ApiResponse> liveData = new MutableLiveData<>();
        Call<StopLocationEntity> call = mApiService.getStopLocation(app_id,app_key,lat, lon, radius);
        call.enqueue(new Callback<StopLocationEntity>() {
            @Override
            public void onResponse(Call<StopLocationEntity> call,Response<StopLocationEntity> response) {
                ApiResponse ap=new ApiResponse((List<StopPointsEntity>) response.body().getStopPoints());
                liveData.setValue(ap);
            }

            @Override
            public void onFailure(Call<StopLocationEntity> call, Throwable t) {
                liveData.setValue(new ApiResponse(t));
            }
        });
        return liveData;
    }


    public MutableLiveData<ApiResponse2> getArrivalInformation(String naptanId, String app_id, String app_key) {
        final MutableLiveData<ApiResponse2> liveData = new MutableLiveData<>();
        Call<List<ArrivalsEntity>> call = mApiService.getArrivalInformation(naptanId,app_id,app_key);
        call.enqueue(new Callback<List<ArrivalsEntity>>() {
            @Override
            public void onResponse(Call<List<ArrivalsEntity>> call,Response<List<ArrivalsEntity>> response) {
                if(response !=null) {
                    ApiResponse2 ap = new ApiResponse2(response.body());
                    liveData.setValue(ap);
                }
            }

            @Override
            public void onFailure(Call<List<ArrivalsEntity>> call, Throwable t) {
                liveData.setValue(new ApiResponse2(t));
            }
        });
        return liveData;
    }
}
