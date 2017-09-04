package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;

import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.List;

/**
 * LocalRepository is a base interface to connect with the local database backend
 * <p>
 * LocalRepository is an interface for managing the communication
 * between the ViewModel and the Local backend: SQLite using ROOM
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */

public interface LocalRepository {
    LiveData<List<ArrivalsFormatedEntity>> getFavouritesLiveData();
    void addFavourite(ArrivalsFormatedEntity favouriteEntity);
    void deleteFavourite(ArrivalsFormatedEntity favouriteEntity);
    int elementInFav(String naptanid, String lineid);
    boolean isFav(ArrivalsEntity arrival);

}

