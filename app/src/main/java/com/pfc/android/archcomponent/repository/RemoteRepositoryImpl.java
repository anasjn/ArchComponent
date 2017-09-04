package com.pfc.android.archcomponent.repository;


import android.content.Context;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.api.TflApiService;
import com.pfc.android.archcomponent.di.ContextModule;
import com.pfc.android.archcomponent.di.DaggerTflApiComponent;
import com.pfc.android.archcomponent.di.TflApiComponent;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * RemoteRepositoryImpl implements a base interface: RemoteRepository
 * <p>
 * RemoteRepositoryImpl is the implementation class for managing the communication
 * between the ViewModel and the Remote backend: Unified Tfl API @link{https://api.tfl.gov.uk/}
 * using Retrofit.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class RemoteRepositoryImpl implements RemoteRepository{

    public static final String TAG = RemoteRepositoryImpl.class.getSimpleName();

    private TflApiService mApiService;
    private String mAppId;
    private String mAppKey;

    /**
     * Contructor with a context parameter to interact with the API backend.
     * <p>
     * This method  allows the communication withe the API transport backend
     *
     */
    public RemoteRepositoryImpl(Context context) {

        mAppId = context.getString(R.string.api_transport_id);
        mAppKey = context.getString(R.string.api_transport_key);

        //Delegate the instance creation of Retrofit in Dagger
        TflApiComponent component = DaggerTflApiComponent.builder()
                .contextModule(new ContextModule(context))
                .build();
        mApiService = component.getRetrofit();

    }

    /**
     * Method async with fourth parameters: latitude, longitude, radius and
     * a callback in order to make a call to the API transport. In the last one is where the answer
     * of the API is saved.
     * <p>
     * This method return the information about the Stops near to the location
     * given through the parameters (lat, lon and radious).
     *
     * @param  lat  a double number giving the latitude of the location.
     * @param  lon  a double number giving the longitude of the location.
     * @param  radius  an int number giving the radius of the search.
     * @param  callback  a Callback<StopLocationEntity> giving the object in order to keep the reply of the API.
     */
    public void getStopLocation(double lat, double lon, int radius, Callback<StopLocationEntity> callback) {
        Call<StopLocationEntity> call = mApiService.getStopLocation(mAppId,mAppKey,lat, lon, radius);
        call.enqueue(callback);
    }

    /**
     * Method async with two parameter: naptanId and a callback
     * in order to make a call to the API transport.  The last one is where the reply
     * of the API is saved.
     * <p>
     * This method return all the arrival information from the lines related with on Stop in this moment.
     * given through one parameters (naptanId).
     *
     * @param  naptanId  a String giving to identify the Stop that we are interesting on.
     * @param  callback  a Callback<List<ArrivalsEntity> giving the object in order to keep the answer of the API.
     */
    public void getArrivalInformation(String naptanId,Callback<List<ArrivalsEntity>> callback) {
        Call<List<ArrivalsEntity>> call = mApiService.getArrivalInformation(naptanId,mAppId,mAppKey);
        call.enqueue(callback);
    }

    /**
     * Method async with fourth parameters: lineId, naptanId, direction and
     * a callback in order to make a call to the API transport.  In the last one is where the reply
     * of the API is saved.
     * <p>
     * This method return the arrival information for one line, in one stop and with one direction
     * given through the parameters (lineId, naptanId and direction).
     *
     * @param  lineId  a String giving to identify the Line that we are interesting on.
     * @param  naptanId  a String giving to identify the Stop that we are interesting on.
     * @param  direction  a String that identify the direction of the bus. The value coud be "inbound", outbound" or "all".
     * @param  callback  a Callback<List<ArrivalsEntity> giving the object in order to keep the answer of the API
     */
    public void getPredictionsByStopPLine(String lineId, String naptanId, String direction, Callback<List<ArrivalsEntity>> callback){
        Call<List<ArrivalsEntity>> call = mApiService.getPredictionsByStopPLine(lineId,naptanId,direction,mAppId,mAppKey);
        call.enqueue(callback);
    }
}
