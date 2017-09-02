package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;

import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.List;

/**
 * Created by ana on 29/08/17.
 */

public interface LocalRepository {

    List<ArrivalsFormatedEntity> getFavourites();
    LiveData<List<ArrivalsFormatedEntity>> getFavouritesLiveData();
//    LiveData<List<FavouriteEntity>> getLiveDataFavourites();
    void addFavourite(ArrivalsFormatedEntity favouriteEntity);
    void deleteFavourite(ArrivalsFormatedEntity favouriteEntity);

}

