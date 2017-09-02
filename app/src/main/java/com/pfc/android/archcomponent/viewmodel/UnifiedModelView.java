package com.pfc.android.archcomponent.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.pfc.android.archcomponent.R;
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
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ana on 29/08/17.
 */

public class UnifiedModelView extends AndroidViewModel {

    private final String TAG = UnifiedModelView.class.getName();

    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableArrivalsFormated;
    private MutableLiveData<List<ArrivalsFormatedEntity>> mFavouritesMutableLiveData;

    private LiveData<DefaultLocation> mLocationLiveData;
    private MutableLiveData<StopLocationEntity> mStopPointMutableLiveData;
    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableLineFav;

    @Inject
    public AppDatabase database;

    RemoteRepository mRemoteRepository;
    LocalRepository mLocalRepository;

    //List of favourites in order to check if the arrival is a favourite or not
    private List<ArrivalsFormatedEntity> favourites = new ArrayList<>();

    //user and password
    String app_id=getApplication().getString(R.string.api_transport_id);
    String app_key=getApplication().getString(R.string.api_transport_key);




    public UnifiedModelView(Application application) {
        super(application);
        ((FavouriteApplication)application).getFavComponent().inject(this);
        this.mMutableArrivalsFormated = new MutableLiveData<>();
        this.mFavouritesMutableLiveData = new MutableLiveData<>();
        this.mRemoteRepository = new RemoteRepositoryImpl();
        this.mLocalRepository = new LocalRepositoryImpl(database);
        this.mStopPointMutableLiveData = new MutableLiveData<>();
        this.mLocationLiveData = new LocationLiveData(application.getApplicationContext());
        this.mMutableLineFav = new MutableLiveData<>();
    }


    public LiveData<DefaultLocation> getLmLocationLiveData() {
       return mLocationLiveData;
    }

    public void setLmLocationLiveData(Context context) {
        mLocationLiveData = new LocationLiveData(context);
    }

    public MutableLiveData<StopLocationEntity> getmStopPointMutableLiveData(){
        return mStopPointMutableLiveData;
    }

    public void setStopPointMutableLiveData(@NonNull double lat, @NonNull double lon, int radious) {
        mRemoteRepository.getStopLocation(app_id,app_key,lat, lon, radious, new Callback<StopLocationEntity> () {
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


    public void setmMutableLiveDataFavourites() {
        favourites = mLocalRepository.getFavourites();
        mFavouritesMutableLiveData.setValue(favourites);
    }

    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutableLiveDataFavourites(){
        return mFavouritesMutableLiveData;
    }


    public LiveData<List<ArrivalsFormatedEntity>> getmLiveDataFavourites(){
        return mLocalRepository.getFavouritesLiveData();
    }


    public void addFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().addFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    public void deleteFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().deleteFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutableArrivalsFormated(){
        return mMutableArrivalsFormated;
    }

    public void setmMutableArrivalsFormated(@NonNull String naptanId, @NonNull String lat, @NonNull String lon) {
        mRemoteRepository.getArrivalInformation(naptanId, app_id, app_key, new Callback<List<ArrivalsEntity>>() {
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


    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutablePredictionsByStopPLine(){
        return mMutableLineFav;
    }


    //ASJ add
    public void setmMutableFavLines(List<ArrivalsFormatedEntity> favourites) {
        List<ArrivalsFormatedEntity> favouritesResult = new ArrayList<ArrivalsFormatedEntity>();
        for (ArrivalsFormatedEntity fav:favourites) {
            Log.v(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++setmMutableFavLines favourites "+favourites.size());

            Log.v(TAG,"unifiedViewMOder naptan"+fav.getNaptanId());
            Log.v(TAG,"getLineId"+fav.getLineId());
            Log.v(TAG,"getStopLetter"+ fav.getStopLetter());
            Log.v(TAG,"getStationName"+fav.getStationName());
            Log.v(TAG,"getPlatformName"+fav.getPlatformName());
            Log.v(TAG,"getDestinationName"+fav.getDestinationName());
            Log.v(TAG,"getDirection"+fav.getDirection());
            Log.v(TAG,"isFavourite"+fav.isFavourite());
            Log.v(TAG,"getTimeToStationSort"+fav.getTimeToStationSort());

            mRemoteRepository.getPredictionsByStopPLine(app_id, app_key, fav.getLineId(), fav.getNaptanId(), fav.getDirection(), new Callback<List<ArrivalsEntity>>() {
                @Override
                public void onResponse(Call<List<ArrivalsEntity>> call, Response<List<ArrivalsEntity>> response) {
                    if(response !=null & response.body()!=null) {
                        List<ArrivalsFormatedEntity> arrivalsFormatedEntity = convert(response.body(), fav.getmLat(), fav.getmLon());
                        fav.setTimeToStation(arrivalsFormatedEntity.get(0).getTimeToStation());
                        favouritesResult.add(new ArrivalsFormatedEntity(fav.getNaptanId(), fav.getLineId(), fav.getStopLetter(), fav.getStationName(), fav.getPlatformName(),fav.getDestinationName(), fav.getmLat(), fav.getmLon(), fav.getDirection(), fav.isFavourite(), fav.getTimeToStation()));
                        Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++favouritesResult " + favouritesResult.size());
                        if (favourites.size() == favouritesResult.size())mMutableLineFav.setValue(favouritesResult);
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

    public List<ArrivalsFormatedEntity> convert(List<ArrivalsEntity> arrivals, String lat,String lon) {
        Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++convert arrivals.get(0).getDirection() "+arrivals);
        List<ArrivalsFormatedEntity> arrivalsformated = new ArrayList<>();
        if (arrivals != null && arrivals.size() > 0) {
            ArrayList<Integer> listtimes = new ArrayList<Integer>();
            listtimes.add(secondsToMinutes(arrivals.get(0).getTimeToStation()));
            String lineId = arrivals.get(0).getLineId();
            boolean fav = isFav(arrivals.get(0));
            ArrivalsFormatedEntity aformated = new ArrivalsFormatedEntity(arrivals.get(0).getNaptanId(), lineId, arrivals.get(0).getStopLetter(), arrivals.get(0).getStationName(), arrivals.get(0).getPlatformName(), arrivals.get(0).getDestinationName(), lat, lon, arrivals.get(0).getDirection(),fav,listtimes);
            arrivalsformated.add(aformated);
            List<Integer> times = null;
            int j = 0;
            String aux = "";
            ArrivalsEntity arrivalAux = null;
            int position = 0;
            Integer timesInMinutes = 0;
            for (int i = 1; i < arrivals.size(); i++) {
                arrivalAux = arrivals.get(i);
                lineId = arrivalAux.getLineId();
                if (arrivalsformated.size() > 0) {
                    position = arrivalsformated.size() - 1;
                }
                aux = arrivalsformated.get(position).getLineId();
                timesInMinutes = secondsToMinutes(arrivalAux.getTimeToStation());
                if (lineId.equals(aux)) {
                    times = arrivalsformated.get(position).getTimeToStation();
                    times.add(timesInMinutes);
                    arrivalsformated.get(position).setTimeToStation(times);
                } else {
                    listtimes = new ArrayList<Integer>();
                    listtimes.add(timesInMinutes);
                    fav = isFav(arrivalAux);
                    aformated = new ArrivalsFormatedEntity(arrivalAux.getNaptanId(), arrivalAux.getLineId(), arrivalAux.getStopLetter(), arrivalAux.getStationName(), arrivalAux.getPlatformName(), arrivalAux.getDestinationName(),lat, lon, arrivalAux.getDirection(),fav,listtimes);
                    arrivalsformated.add(aformated);
                }
            }

        }
        return arrivalsformated;
    }

    private Integer secondsToMinutes(int seconds){
        int minutes = 0;
        minutes = seconds/60;
        return minutes;
    }

    private boolean isFav(ArrivalsEntity arrival){
        for (ArrivalsFormatedEntity fav :
                favourites) {
            if (fav.getLineId().equals(arrival.getLineId()) && fav.getNaptanId().equals(arrival.getNaptanId())){
                return true;
            }
        }
        return false;
    }

}
