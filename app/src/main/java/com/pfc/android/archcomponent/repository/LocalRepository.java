package com.pfc.android.archcomponent.repository;

import android.arch.lifecycle.LiveData;

import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.List;

/**
 * Created by ana on 29/08/17.
 */

public interface LocalRepository {

    List<FavouriteEntity> getFavourites();
//    LiveData<List<FavouriteEntity>> getLiveDataFavourites();
    void addFavourite(FavouriteEntity favouriteEntity);
    void deleteFavourite(FavouriteEntity favouriteEntity);

}

