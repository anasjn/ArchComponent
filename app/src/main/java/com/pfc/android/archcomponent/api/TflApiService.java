package com.pfc.android.archcomponent.api;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dr3amsit on 29/07/17.
 */

public interface TflApiService {

//    @GET("/repos/{owner}/{repo}/issues")
//    Call<List<StopLocationEntity>> getIssues(@Path("owner") String owner, @Path("repo") String repo);

    //https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=51.513395&lon=-0.089095&radius=100
    @GET("Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram")
    Call<StopLocationEntity> getStopLocation(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("radius") int radius);

}
