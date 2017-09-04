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
 * FavouriteDao is an interface that communicate with the database directly.
 * <p>
 * List of queries:
 * <ul>
 *  <li>getFavouritesLiveData     - this method retrieve a LiveData<List<ArrivalsFormatedEntity>> object without parameters.
 *  <li>addFavourite              - this method add one element to the database. parameter: ArrivalsFormatedEntity. return: void.
 *  <li>deleteFavourite           - this method delete one element to the database. parameter: ArrivalsFormatedEntity. return: void.
 *  <li>elementInFav              - this method gives the number of entries with this naptanid and this lineid that are in the database
 * </ul>
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
@Dao
public interface FavouriteDao {
    @Query("SELECT * FROM favourites")
    LiveData<List<ArrivalsFormatedEntity>> getFavouritesLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFavourite(ArrivalsFormatedEntity favourite);

    @Delete
    void deleteFavourite(ArrivalsFormatedEntity favourite);

    @Query("SELECT COUNT(*) FROM favourites WHERE naptanid = :naptanid and lineid = :lineid")
    int elementInFav(String naptanid, String lineid);

}
