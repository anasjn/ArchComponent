package com.pfc.android.archcomponent.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.repository.LocalRepository;
import com.pfc.android.archcomponent.repository.LocalRepositoryImpl;
import com.pfc.android.archcomponent.repository.RemoteRepositoryImpl;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

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
//    private LiveData<List<FavouriteEntity>> favourites;

    @Inject
    public AppDatabase database;

    RemoteRepositoryImpl remoteRepository;
    LocalRepositoryImpl localRepository;

    private List<FavouriteEntity> favourites = new ArrayList<>();
   //private MutableLiveData<List<FavouriteEntity>> favouritesEntity;

    //user and password
    String app_id=getApplication().getString(R.string.api_transport_id);
    String app_key=getApplication().getString(R.string.api_transport_key);


    public UnifiedModelView(Application application) {
        super(application);
        ((FavouriteApplication)application).getFavComponent().inject(this);

        this.mMutableArrivalsFormated = new MutableLiveData<>();
        this.remoteRepository = new RemoteRepositoryImpl();
        this.localRepository = new LocalRepositoryImpl(database);
       // favouritesEntity = new MutableLiveData<>();
//        ((FavouriteApplication)application).getFavComponent().inject(this);
//        favouritesEntity = localRepository.getFavourites();
//        this.localRepository = new LocalRepositoryImpl(database);
//
//        favourites=localRepository.getFavourites();
//        localRepository.getFavourites().observe(this,new Observer<List<FavouriteEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<FavouriteEntity> favouriteEntities) {
//                favourites.addAll(favouriteEntities);
//            }
//        });

        favourites.addAll(localRepository.getFavourites());
    }

    @NonNull
    public List<FavouriteEntity> getFavourites() {
        Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ unifiedViewModel getFavourites: "+localRepository.getFavourites().size());
        return localRepository.getFavourites();
    }

//    public LiveData<List<FavouriteEntity>> getFavourites(){
//        new AsyncTask<Void, Integer, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                //getFavourites(localRepository.getFavourites());
//                favouritesEntity = localRepository.getFavourites();
//                return null;
//            }
//        }.execute();
//        return favouritesEntity;
//    }

    public void addFavourite(FavouriteEntity favouriteEntity) {
        Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ unifiedViewModel addFavourite");
        if(!favouriteEntity.isTransient()) {
            throw new RuntimeException("Task already added.");
        }
        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ unifiedViewModel addFavourite params "+params[0].getmLineId());
                database.favouriteDao().addFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    private void updateFavourite(FavouriteEntity favouriteEntity) {
        if(favouriteEntity.isTransient()) {
            throw new RuntimeException("Task is transient.");
        }

        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                database.favouriteDao().updateFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    public MutableLiveData<List<ArrivalsFormatedEntity>> getmMutableArrivalsFormated(){
        return mMutableArrivalsFormated;
    }

    public void getArrivalInformation(String naptanId) {
        remoteRepository.getArrivalInformation(naptanId, app_id, app_key, new Callback<List<ArrivalsEntity>>() {
            @Override
            public void onResponse(Call<List<ArrivalsEntity>> call, Response<List<ArrivalsEntity>> response) {
                List<ArrivalsFormatedEntity> arrivalsFormatedEntity = convert(response.body());
                mMutableArrivalsFormated.setValue(arrivalsFormatedEntity);
            }

            @Override
            public void onFailure(Call<List<ArrivalsEntity>> call, Throwable t) {

            }
        });
    }

    public List<ArrivalsFormatedEntity> convert(List<ArrivalsEntity> arrivals) {
        List<ArrivalsFormatedEntity> arrivalsformated = new ArrayList<>();
        if (arrivals != null && arrivals.size() > 0) {
            ArrayList<Integer> listtimes = new ArrayList<Integer>();
            listtimes.add(secondsToMinutes(arrivals.get(0).getTimeToStation()));
            String lineId = arrivals.get(0).getLineId();
            boolean fav = isFav(arrivals.get(0));
            Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++en el 0....fav " + fav);
            ArrivalsFormatedEntity aformated = new ArrivalsFormatedEntity(arrivals.get(0).get$type(), arrivals.get(0).getNaptanId(), lineId, arrivals.get(0).getStopLetter(), arrivals.get(0).getStationName(), arrivals.get(0).getPlatformName(), arrivals.get(0).getDestinationName(), listtimes, fav);
            arrivalsformated.add(aformated);
            List<Integer> times = null;
            int j = 0;
            String aux = "";
            ArrivalsEntity arrivalAux = null;
            int position = 0;
            int timesInMinutes = 0;
            Log.v(TAG, "+++++++++++++++++++++++++++arrivals: " + arrivals.size());
            Log.v(TAG, "+++++++++++++++++++++++++++arrivalsformated: " + arrivalsformated.size());
            for (int i = 1; i < arrivals.size(); i++) {
                arrivalAux = arrivals.get(i);
                lineId = arrivalAux.getLineId();
                Log.v(TAG, "+++++++++++++++++++++++++++lineId: " + lineId + "-Iteraci'on" + i);
                if (arrivalsformated.size() > 0) {
                    position = arrivalsformated.size() - 1;
                }
                aux = arrivalsformated.get(position).getLineId();
                Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Aux.... " + aux);
                timesInMinutes = secondsToMinutes(arrivalAux.getTimeToStation());
                if (lineId.equals(aux)) {
                    times = arrivalsformated.get(position).getTimeToStation();
                    times.add(timesInMinutes);
                    arrivalsformated.get(position).setTimeToStation(times);
                } else {
                    listtimes = new ArrayList<Integer>();
                    listtimes.add(timesInMinutes);
                    fav = isFav(arrivalAux);
                    Log.v(TAG, "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++Aux....fav " + fav);
                    aformated = new ArrivalsFormatedEntity(arrivalAux.get$type(), arrivalAux.getNaptanId(), arrivalAux.getLineId(), arrivalAux.getStopLetter(), arrivalAux.getStationName(), arrivalAux.getPlatformName(), arrivalAux.getDestinationName(), listtimes,fav);
                    arrivalsformated.add(aformated);
                }
            }

        }
        return arrivalsformated;
    }

    private int secondsToMinutes(int seconds){
        int minutes = 0;
        minutes = seconds/60;
        return minutes;
    }

    private boolean isFav(ArrivalsEntity arrival){
        Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ isFAV ");
       // FavouriteEntity favtemp = new FavouriteEntity(Datearrival.getLineId(), arrival.getNaptanId());

        for (FavouriteEntity fav :
                favourites) {
            if (fav.getmLineId().equals(arrival.getLineId()) && fav.getmNaptanId().equals(arrival.getNaptanId())){
                return true;
            }
        }
        return false;

        //return favourites.  .contains(arrival);
    }
}
