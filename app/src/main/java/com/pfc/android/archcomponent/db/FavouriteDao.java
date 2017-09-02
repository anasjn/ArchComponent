package com.pfc.android.archcomponent.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.List;

/**
 * Interface for database for Favourite related operations.
 */

@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favourites")
    LiveData<List<ArrivalsFormatedEntity>> getFavouritesLiveData();
//
//    @Query("SELECT COUNT(*) FROM favourites WHERE mNaptanId LIKE :naptanid AND mLineId LIKE :lineid")
//    Integer isFavourite(String naptanid, String lineid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavourite(ArrivalsFormatedEntity favourite);

    @Delete
    void deleteFavourite(ArrivalsFormatedEntity favourite);

    @Query("SELECT * FROM favourites")
    List<ArrivalsFormatedEntity> getFavourites();

}
