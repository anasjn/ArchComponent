package com.pfc.android.archcomponent.repository;

import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.List;

/**
 * Created by ana on 29/08/17.
 */

public interface LocalRepository {

    List<FavouriteEntity> getFavourites();
    void addFavourite(FavouriteEntity favouriteEntity);
    void updateFavourite(FavouriteEntity favouriteEntity);

}

