package com.pfc.android.archcomponent.api;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * TflApiService is an interface that communicate with the Transport API directly.
 * <p>
 * List of calls:
 * <ul>
 *  <li>getArrivalInformation           - Gets the list of arrival predictions for the given stop point id. @link{https://api.tfl.gov.uk/StopPoint/{id}/Arrivals?app_id=xx&app_key=xx}.
 *  <li>getStopLocation                 - Gets a list of StopPoints within {radius} by the specified criteria. @link{https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=xxx&lon=xxx&radius=xxxapp_id=xx&app_key=xx}.
 * </ul>getPredictionsByStopPLine       - Gets the list of arrival predictions for given line ids based at the given stop. @link{https://api.tfl.gov.uk/Line/{line}/Arrivals/{naptanid}?direction=xxxxxapp_id=xx&app_key=xx}
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public interface TflApiService {

    /**
     * Gets the list of arrival predictions for the given stop point id
     * <p>
     * https://api.tfl.gov.uk/StopPoint/{id}/Arrivals?app_id=xxx&app_key=xxxx
     *
     * @param id
     * @param api_transport_id
     * @param api_transport_key
     * @return Call<List<ArrivalsEntity>>
     */
    @GET("StopPoint/{id}/Arrivals")
    Call<List<ArrivalsEntity>> getArrivalInformation(
            @Path("id") String id,
            @Query("app_id") String api_transport_id,
            @Query("app_key") String api_transport_key
    );

    /**
     * Gets a list of StopPoints within {radius} by the specified criteria
     * <p>
     * https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=51.513395&lon=-0.089095&radius=100&app_id=xxx&app_key=xxxx
     * @query api_transport_id
     * @query api_transport_key
     * @query lat
     * @query lon
     * @query radius
     * @return Call<StopLocationEntity>
     */
    @GET("Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanPublicBusCoachTram")
    Call<StopLocationEntity> getStopLocation(
            @Query("app_id") String api_transport_id,
            @Query("app_key") String api_transport_key,
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("radius") int radius
    );

    /**
     * Gets the list of arrival predictions for given line ids based at the given stop
     * <p>
     * https://api.tfl.gov.uk/Line/21/Arrivals/490000026E?direction=inbound&app_id=xxx&app_key=xxxx
     * @query api_transport_id
     * @query api_transport_key
     * @param lineid
     * @param naptanid
     * @query direction
     * @return Call<List<ArrivalsEntity>>
     */
    @GET("Line/{lineid}/Arrivals/{naptanid}")
    Call<List<ArrivalsEntity>> getPredictionsByStopPLine(
            @Path("lineid") String lineid,
            @Path("naptanid") String naptanid,
            @Query("direction") String direction,
            @Query("app_id") String api_transport_id,
            @Query("app_key") String api_transport_key
    );

}
