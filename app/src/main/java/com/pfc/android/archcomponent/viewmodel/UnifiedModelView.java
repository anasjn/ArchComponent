package com.pfc.android.archcomponent.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.repository.LocalRepository;
import com.pfc.android.archcomponent.repository.LocalRepositoryImpl;
import com.pfc.android.archcomponent.repository.RemoteRepository;
import com.pfc.android.archcomponent.repository.RemoteRepositoryImpl;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import com.pfc.android.archcomponent.util.LocationLiveData;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UnifiedModelView extends AndroidViewModel
 * <p>
 * This is the ViewModel that manage the communication between the fragments and the repository through the LiveData.
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public class UnifiedModelView extends AndroidViewModel {

    private final String TAG = UnifiedModelView.class.getName();

    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableArrivalsFormated;
    private MutableLiveData<List<ArrivalsFormatedEntity>> mFavouritesMutableLiveData;
    private LocationLiveData mLocationLiveData;
    private MutableLiveData<StopLocationEntity> mStopPointMutableLiveData;
    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableLineFav;

    @Inject
    public AppDatabase database;

    RemoteRepository mRemoteRepository;
    LocalRepository mLocalRepository;

    /**
     * Contructor with a application parameter to interact with all the livedata
     * <p>
     *
     * @param application Application
     */
    public UnifiedModelView(Application application) {
        super(application);
        ((FavouriteApplication)application).getFavComponent().inject(this);
        this.mMutableArrivalsFormated = new MutableLiveData<>();
        this.mFavouritesMutableLiveData = new MutableLiveData<>();
        this.mRemoteRepository = new RemoteRepositoryImpl(application);
        this.mLocalRepository = new LocalRepositoryImpl(database);
        this.mStopPointMutableLiveData = new MutableLiveData<>();
        // This set own location if the GPS is enabled into the device.
        this.mLocationLiveData = new LocationLiveData(application.getApplicationContext());
        this.mMutableLineFav = new MutableLiveData<>();
    }

    /**
     * This Method return the LiveData<DefaultLocation>
     * <p>
     * @return   LiveData<DefaultLocation>
     */
    public LiveData<DefaultLocation> getLmLocationLiveData() {
       return mLocationLiveData;
    }

    /**
     * This Method makes the set of the location. As the presentation is not going to be in London,
     * setRandomLocation gives us a location in London in order to test.
     * <p>
     * @param   context Context
     */
    public void setRandomLocationLiveData(Context context) {
        mLocationLiveData.setRandomLocation();
    }


    /**
     * This Method return the MutableLiveData mStopPointMutableLiveData
     * <p>
     * @return   MutableLiveData<StopLocationEntity>
     */
    public MutableLiveData<StopLocationEntity> getmStopPointMutableLiveData(){
        return mStopPointMutableLiveData;
    }
    /**
     * This Method makes the setValue of the MutableLiveData:mStopPointMutableLiveData
     * <p>
     * @param   radious int
     * @param   lat  String
     * @param   lon  String
     */
    public void setStopPointMutableLiveData(@NonNull double lat, @NonNull double lon, int radious) {
        mRemoteRepository.getStopLocation(lat, lon, radious, new Callback<StopLocationEntity> () {
            @Override
            public void onResponse(Call<StopLocationEntity> call, Response<StopLocationEntity> response) {
                mStopPointMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<StopLocationEntity> call, Throwable t) {
                Log.e(TAG, "error occured: " + t.toString());
                Toast.makeText(getApplication().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This Method checks if the GPS in the device is enable in order to locate own position.
     * <p>
     * @return boolean
     */
    public boolean isGPSEnabled() {
        final LocationManager manager = (LocationManager) getApplication().getSystemService( Context.LOCATION_SERVICE );
        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER );
    }



    /**
     * This Method return the LiveData mStopPointMutableLiveData
     * <p>
     * @return   LiveData<List<ArrivalsFormatedEntity>>
     */
    public LiveData<List<ArrivalsFormatedEntity>> getmLiveDataFavourites(){
        return mLocalRepository.getFavouritesLiveData();
    }


    /**
     * This Method add a favourite asynchronous to the database
     * <p>
     * @param   favouriteEntity ArrivalsFormatedEntity
     */
    public void addFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().addFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    /**
     * This Method delete a favourite asynchronous from the database
     * <p>
     * @param   favouriteEntity ArrivalsFormatedEntity
     */
    public void deleteFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().deleteFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }


    /**
     * This Method return the MutableLiveData mMutableArrivalsFormated
     * <p>
     * @return   MutableLiveData<List<ArrivalsFormatedEntity>>
     */
    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutableArrivalsFormated(){
        return mMutableArrivalsFormated;
    }

    /**
     * This Method makes the setValue of the MutableLiveData:mMutableArrivalsFormated
     * <p>
     * @param   naptanId String
     * @param   lat  String
     * @param   lon  String
     */
    public void setmMutableArrivalsFormated(@NonNull String naptanId, @NonNull String lat, @NonNull String lon) {
        mRemoteRepository.getArrivalInformation(naptanId, new Callback<List<ArrivalsEntity>>() {
            @Override
            public void onResponse(Call<List<ArrivalsEntity>> call, Response<List<ArrivalsEntity>> response) {
                List<ArrivalsFormatedEntity> arrivalsFormatedEntity = convert(response.body(),lat,lon);
                mMutableArrivalsFormated.setValue(arrivalsFormatedEntity);
            }

            @Override
            public void onFailure(Call<List<ArrivalsEntity>> call, Throwable t) {
                Log.e(TAG, "error occured: " + t.toString());
                Toast.makeText(getApplication().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * This Method return the MutableLiveData mMutableLineFav
     * <p>
     * @return   MutableLiveData<List<ArrivalsFormatedEntity>>
     */
    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutablePredictionsByStopPLine(){
        return mMutableLineFav;
    }

    /**
     * This Method makes the setValue of the MutableLiveData:mMutableLineFav
     * <p>
     *
     * @param  favourites List<ArrivalsFormatedEntity>
     */
    public void setmMutableFavLines(List<ArrivalsFormatedEntity> favourites) {
        List<ArrivalsFormatedEntity> favouritesResult = new ArrayList<ArrivalsFormatedEntity>();

        if (favourites.isEmpty())
            mMutableLineFav.setValue(favouritesResult);

        for (ArrivalsFormatedEntity fav:favourites) {
            mRemoteRepository.getPredictionsByStopPLine(fav.getLineId(), fav.getNaptanId(), fav.getDirection(), new Callback<List<ArrivalsEntity>>() {
                @Override
                public void onResponse(Call<List<ArrivalsEntity>> call, Response<List<ArrivalsEntity>> response) {
                    if(response !=null & response.body()!=null) {
                        List<ArrivalsFormatedEntity> arrivalsFormatedEntity = convert(response.body(), fav.getmLat(), fav.getmLon());
                        fav.setTimeToStation(arrivalsFormatedEntity.get(0).getTimeToStation());
                        favouritesResult.add(fav);
                        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++favouritesResult " + favouritesResult.size());
                        if (favourites.size() == favouritesResult.size())
                            mMutableLineFav.setValue(favouritesResult);
                    }
                }

                @Override
                public void onFailure(Call<List<ArrivalsEntity>> call, Throwable t) {
                    Log.e(TAG, "error occured: " + t.toString());
                    Toast.makeText(getApplication().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    /**
     * This Method tranforms an ArrivalEntity into a ArrivalsFormatedEntity. It does not fill in all the values.
     * <p>
     *
     * @param   arrival  ArrivalsEntity
     * @return  ArrivalsFormatedEntity
     */
    private ArrivalsFormatedEntity transformToArrivalsFormatedEntity(ArrivalsEntity arrival) {

        ArrivalsFormatedEntity arrivalsFormatted = new ArrivalsFormatedEntity();
        arrivalsFormatted.setNaptanId(arrival.getNaptanId());
        arrivalsFormatted.setLineId(arrival.getLineId());
        arrivalsFormatted.setStopLetter(arrival.getStopLetter());
        arrivalsFormatted.setStationName(arrival.getStationName());
        arrivalsFormatted.setPlatformName(arrival.getPlatformName());
        arrivalsFormatted.setDestinationName(arrival.getDestinationName());
        arrivalsFormatted.setDirection(arrival.getDirection());

        return arrivalsFormatted;
    }


    /**
     * This Method checks if a formatted arrival already exists in a collection of formatted and returns its position from the list
     * If not exists, returns a -1
     * <p>
     * There are two parameters adding in order to add the information about the location of the stop in the favourites because after we want to
     * show it in the application.
     *
     * @param   arrivalFormated   ArrivalsFormatedEntity
     * @param   arrivalFormatedList List<ArrivalsFormatedEntity>
     * @return  int that indicates the position inside the list. -1 is returned when it is not found.
     */
    private int arrivalExistInCurrentList(ArrivalsFormatedEntity arrivalFormated, List<ArrivalsFormatedEntity> arrivalFormatedList) {
        for ( ArrivalsFormatedEntity item : arrivalFormatedList ) {
            if ( item.getLineId().equals(arrivalFormated.getLineId()) )   // And same line ID
                return arrivalFormatedList.indexOf(item);
        }
        return -1;
    }


    /**
     * This Method convert one List<ArrivalsEntity> to a List<ArrivalsFormatedEntity>, in order to show in the list the different arrivals
     * times (this is how is returned in the API) for a one single line in the same entry (how we want to show in the application).
     * <p>
     * There are two parameters adding in order to add the information about the location of the stop in the favourites because after we want to
     * show it in the application.
     *
     * @param   arrivals   List<ArrivalsEntity>
     * @param   latitude String
     * @param   longitude String
     * @return  List<ArrivalsFormatedEntity>
     */
    public List<ArrivalsFormatedEntity> convert(List<ArrivalsEntity> arrivals, String latitude,String longitude) {

        // Create the result list
        List<ArrivalsFormatedEntity> arrivalsFormated = new ArrayList<>();

        if (arrivals != null && !arrivals.isEmpty()) { //If it si not null and not empty ...
            for( ArrivalsEntity arrival : arrivals) { // Lets iterate ...

                // Create a new arrivalsFormattedEntity
                ArrivalsFormatedEntity arrivalsFormatedEntity = transformToArrivalsFormatedEntity(arrival);
                // Check if the new arrivalsFormattedEntity exist in the list of arrivalsFormattedEntity
                int positionInList = arrivalExistInCurrentList(arrivalsFormatedEntity, arrivalsFormated);
                if ( positionInList == -1)  {
                    // As it does not exists:
                    // Set current stop latitude and longitude
                    arrivalsFormatedEntity.setmLat(latitude);
                    arrivalsFormatedEntity.setmLon(longitude);
                    // Set if this arrival is in our favourite list
                    arrivalsFormatedEntity.setFavourite( isFav(arrival) );
                    // Set the schedule time into the collection
                    arrivalsFormatedEntity.setTimeToStation(new ArrayList<>(Collections.singletonList(secondsToMinutes(arrival.getTimeToStation()))));
                    // ... and add it into the arrivalsFormattedEntity list
                    arrivalsFormated.add( arrivalsFormatedEntity);
                } else {
                    // As it already exists, lets add the scheduler time
                    List<Integer> getCurrentSchedule = arrivalsFormated.get(positionInList).getTimeToStation(); // Get current schedule
                    getCurrentSchedule.add(secondsToMinutes(arrival.getTimeToStation())); // And the new time
                    arrivalsFormated.get(positionInList).setTimeToStation(getCurrentSchedule); // ... and set it into the collection.
                }
            }
        }

        return arrivalsFormated;

    }

    /**
     * This Method return the minutes equivalents to the seconds given as a parameter
     * <p>
     *
     * @param   seconds   int
     * @return  Integer
     */
    private Integer secondsToMinutes(int seconds) { return seconds/60; }

    /**
     * This Method return true if the arrival parameter is a favourite save in the database
     * <p>
     *
     * @param   arrival   ArrivalsEntity
     * @return  boolean
     */
    private boolean isFav(ArrivalsEntity arrival){

        return mLocalRepository.isFav(arrival);

    }





}
