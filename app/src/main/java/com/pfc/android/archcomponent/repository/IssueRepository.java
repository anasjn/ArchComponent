package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.MutableLiveData;

import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.api.ApiResponse2;

/**
 * Created by dr3amsit on 29/07/17.
 */

public interface IssueRepository {
    MutableLiveData<ApiResponse> getStopLocation(String app_id, String app_key, double lat, double lon, int radious);
    MutableLiveData<ApiResponse2> getArrivalInformation(String naptanId, String app_id, String app_key);
}
