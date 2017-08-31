package com.pfc.android.archcomponent.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.List;

/**
 * Interface for database for Favourite related operations.
 */

@Dao
public interface FavouriteDao {
//    @Query("SELECT * FROM favourites")
//    LiveData<List<FavouriteEntity>> getAllFavourites();
//
//    @Query("SELECT COUNT(*) FROM favourites WHERE mNaptanId LIKE :naptanid AND mLineId LIKE :lineid")
//    Integer isFavourite(String naptanid, String lineid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavourite(FavouriteEntity favourite);

    @Delete
    void deleteFavourite(FavouriteEntity favourite);

    @Query("SELECT * FROM favourites")
    List<FavouriteEntity> getFavourites();

}
