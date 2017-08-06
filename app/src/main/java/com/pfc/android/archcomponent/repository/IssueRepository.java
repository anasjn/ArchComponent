package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;

import com.pfc.android.archcomponent.api.ApiResponse;

/**
 * Created by dr3amsit on 29/07/17.
 */

public interface IssueRepository {
    LiveData<ApiResponse> getStopLocation(double lat, double lon, int radious);
}
