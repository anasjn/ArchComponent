package com.pfc.android.archcomponent.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
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
    @Query("SELECT * FROM favourites")
    LiveData<List<FavouriteEntity>> getAllFavourites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavourite(FavouriteEntity favourite);

    @Update
    void updateFavourite(FavouriteEntity favourite);
}
