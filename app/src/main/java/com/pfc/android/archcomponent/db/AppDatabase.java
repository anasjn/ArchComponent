package com.pfc.android.archcomponent.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.pfc.android.archcomponent.vo.FavouriteEntity;

/**
 * Created by dr3amsit on 29/07/17.
 */

@Database(entities = {FavouriteEntity.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao favouriteDao();
}