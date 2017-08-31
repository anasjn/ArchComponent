package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

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

    private List<FavouriteEntity> favouritesEntity;

    //private MutableLiveData<List<FavouriteEntity>> mMutablefavouritesEntity;

    public LocalRepositoryImpl(AppDatabase database){
        this.database = database;
        //favouritesEntity = new ArrayList<>();
       // mMutablefavouritesEntity = new MutableLiveData<>();
    }

    public List<FavouriteEntity> getFavourites(){
        try {
            return new testGetFavourites(database).execute().get();
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static class testGetFavourites extends AsyncTask<Void, Void, List<FavouriteEntity>>{

        private AppDatabase database;

        public testGetFavourites(AppDatabase database) {
            this.database = database;
        }

        @Override
        protected List<FavouriteEntity> doInBackground(Void... voids) {
            return database.favouriteDao().getFavourites();
        }

        @Override
        protected void onPostExecute(List<FavouriteEntity> favouriteEntities) {
            super.onPostExecute(favouriteEntities);
        }
    }


//    public LiveData<List<FavouriteEntity>> getLiveDataFavourites(){
//        return database.favouriteDao().getAllFavourites();
//    }


    public void addFavourite(FavouriteEntity favouriteEntity) {
        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                database.favouriteDao().addFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }


    public void deleteFavourite(FavouriteEntity favouriteEntity) {
        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                database.favouriteDao().deleteFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }
}
