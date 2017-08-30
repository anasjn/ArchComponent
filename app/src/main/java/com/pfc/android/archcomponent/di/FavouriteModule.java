package com.pfc.android.archcomponent.di;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.repository.IssueRepositoryImpl;
import com.pfc.android.archcomponent.util.FavouriteApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ana on 19/08/17.
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
        //create the database
        AppDatabase db = Room
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();

        Log.v(TAG,"db created");
        return db;
    }
}
