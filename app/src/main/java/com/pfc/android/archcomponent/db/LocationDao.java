package com.pfc.android.archcomponent.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

@Dao
public interface LocationDao {

//    @Query("SELECT * FROM locations where Stop = :stop")
//    LiveData<List<LocationEntity>> loadLocations(String stop);
//
//    @Query("SELECT * FROM locations where Stop = :stop")
//    List<LocationEntity> loadLocationsSync(String stop);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertAll(List<LocationEntity> favourites);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertFavourite(LocationEntity favourite);
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertLocation(LocationEntity currentLocation);
}
