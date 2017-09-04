package com.pfc.android.archcomponent.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2 Database Component. - Dependency Injection and Code injection.
 * <p>
 * Module that allows to inject code in the ROOM backend in the database called favourite-db
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
@Module
public class FavouriteModule {
    public static final String TAG = FavouriteModule.class.getSimpleName();

    static final String DATABASE_NAME = "favourite-db";
    private FavouriteApplication app;

    public FavouriteModule(FavouriteApplication app) {
        this.app = app;
    }

    @Provides
    Context getApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    AppDatabase getTaskDatabase(Context context) {

        //Remove the database information
        //context.deleteDatabase(DATABASE_NAME);
        //create the database with Room
        AppDatabase db = Room
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        Log.d(TAG,"db created");
        return db;
    }
}
