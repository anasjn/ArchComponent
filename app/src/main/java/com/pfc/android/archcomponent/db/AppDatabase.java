package com.pfc.android.archcomponent.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

/**
 * AppDatabase abstract class that extends RoomDatabase and who only has one abstract method
 * to get a DAO element to connect withe the database.
 * <p>
 * This class used a TypeConverter in order to have a List<Integer> from a String and a String from a List<Integer>
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
@Database(entities = {ArrivalsFormatedEntity.class}, version = 1)
@TypeConverters({StringConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao favouriteDao();
}