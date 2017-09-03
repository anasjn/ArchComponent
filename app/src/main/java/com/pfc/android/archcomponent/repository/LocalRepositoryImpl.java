package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * LocalRepositoryImpl implements a base interface: LocalRepository
 * <p>
 * LocalRepositoryImpl is the implementation class for managing the communication
 * between the ViewModel and the Local backend: SQLite using ROOM.
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public class LocalRepositoryImpl implements LocalRepository {

    private final String TAG = LocalRepositoryImpl.class.getName();

    public AppDatabase database;

    /**
     * Contructor with an Appdatabase parameter.
     * <p>
     * This method  allows the communication withe the database backend
     *
     */
    public LocalRepositoryImpl(AppDatabase database){
        this.database = database;
    }

    /**
     * Method that return a LiveData object without parameters. This method
     * made an async select in order to get all the elements that are in the database.
     * <p>
     * This method get a LiveData object with a list of ArrivalsFormatedEntitites.
     *
     * @return     a LiveData<List<ArrivalsFormatedEntity>> object giving to get all the favourites saved in the database.
     *
     */
    public LiveData<List<ArrivalsFormatedEntity>> getFavouritesLiveData(){
        return database.favouriteDao().getFavouritesLiveData();
    }

    /**
     * Method async with one parameter: ArrivalsFormatedEntity. This method
     * made an async insert to the database.
     * <p>
     * This method insert the favouriteEntity in the database.
     *
     * @param  favouriteEntity  a ArrivalsFormatedEntity object giving to add to the database.
     */
    public void addFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().addFavourite(params[0]);
                Log.d(TAG, "element added");
                return null;
            }
        }.execute(favouriteEntity);
    }

    /**
     * Method async with one parameter: ArrivalsFormatedEntity. This method
     * made an async deletion to the database.
     * <p>
     * This method delete the favouriteEntity from the database.
     *
     * @param  favouriteEntity  a ArrivalsFormatedEntity object giving to delete from the database.
     */
    public void deleteFavourite(ArrivalsFormatedEntity favouriteEntity) {
        new AsyncTask<ArrivalsFormatedEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(ArrivalsFormatedEntity... params) {
                database.favouriteDao().deleteFavourite(params[0]);
                Log.d(TAG, "element deleted");
                return null;
            }
        }.execute(favouriteEntity);
    }

    //a borrar

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
}
