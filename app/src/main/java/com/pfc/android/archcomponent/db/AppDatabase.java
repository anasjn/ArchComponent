package com.pfc.android.archcomponent.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

/**
 * Created by dr3amsit on 29/07/17.
 */

@Database(entities = {ArrivalsFormatedEntity.class}, version = 1)
@TypeConverters({StringConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao favouriteDao();
}