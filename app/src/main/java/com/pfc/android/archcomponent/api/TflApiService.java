package com.pfc.android.archcomponent.api;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by dr3amsit on 29/07/17.
 */

public interface TflApiService {

    //Gets the list of arrival predictions for the given stop point id
    //https://api.tfl.gov.uk/StopPoint/{id}/Arrivals
    @GET("StopPoint/{id}/Arrivals")
    Call<List<ArrivalsEntity>> getArrivalInformation(
            @Path("id") String id,
            @Query("app_id") String api_transport_id,
            @Query("app_key") String api_transport_key
    );

    //Gets a list of StopPoints within {radius} by the specified criteria
    //https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=51.513395&lon=-0.089095&radius=100
    @GET("Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanPublicBusCoachTram")
    Call<StopLocationEntity> getStopLocation(
            @Query("app_id") String api_transport_id,
            @Query("app_key") String api_transport_key,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("radius") int radius
    );


}
