package com.pfc.android.archcomponent.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.repository.IssueRepository;
import com.pfc.android.archcomponent.repository.IssueRepositoryImpl;


/**
 * Created by dr3amsit on 29/07/17.
 */

public class ListLocationsViewModel extends ViewModel {

    private final String TAG = ListLocationsViewModel.class.getName();

    private MediatorLiveData<ApiResponse> mApiResponse;
    private IssueRepository mIssueRepository;

    // No argument constructor
    public ListLocationsViewModel() {
        mApiResponse = new MediatorLiveData<>();
        mIssueRepository = new IssueRepositoryImpl();
    }

    @NonNull
    public LiveData<ApiResponse> getApiResponse() {
        Log.v(TAG, "getApiResponse " +mApiResponse.toString());
        return mApiResponse;
    }

    public LiveData<ApiResponse> loadStopInformation(@NonNull String app_id, @NonNull String app_key,@NonNull double lat,@NonNull double lon, int radious) {
        MutableLiveData<ApiResponse> aux= mIssueRepository.getStopLocation(app_id,app_key,lat, lon, radious);
        mApiResponse.addSource(aux, new Observer <ApiResponse>(){
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                if(apiResponse==null){
                    Log.v(TAG,"Fetch data from API");
                }else{
//                    Log.v(TAG,"_____________________________________onChanged aux" + aux.getValue().getStopLocation());
                    mApiResponse.removeSource(aux);
                    mApiResponse.setValue(apiResponse);
                }
            }
        });
        return mApiResponse;
    }
}
