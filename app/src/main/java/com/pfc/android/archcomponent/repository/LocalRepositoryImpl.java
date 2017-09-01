package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

/**
 * Created by ana on 29/08/17.
 */

public class LocalRepositoryImpl implements LocalRepository {

    private final String TAG = LocalRepositoryImpl.class.getName();

    public AppDatabase database;

    private List<ArrivalsFormatedEntity> favouritesEntity;

    //private MutableLiveData<List<FavouriteEntity>> mMutablefavouritesEntity;

    public LocalRepositoryImpl(AppDatabase database){
        this.database = database;
        //favouritesEntity = new ArrayList<>();
       // mMutablefavouritesEntity = new MutableLiveData<>();
    }

    public List<ArrivalsFormatedEntity> getFavourites(){
        try {
            return new testGetFavourites(database).execute().get();
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static class testGetFavourites extends AsyncTask<Void, Void, List<ArrivalsFormatedEntity>>{

        private AppDatabase database;

        public testGetFavourites(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected List<ArrivalsFormatedEntity> doInBackground(Void... voids) {
            return database.favouriteDao().getFavourites();
        }

        @Override
        protected void onPostExecute(List<ArrivalsFormatedEntity> favouriteEntities) {
            super.onPostExecute(favouriteEntities);
        }
    }


//    public LiveData<List<FavouriteEntity>> getLiveDataFavourites(){
//        return database.favouriteDao().getAllFavourites();
//    }


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
}
