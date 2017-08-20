package com.pfc.android.archcomponent.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.util.FavouriteApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ana on 19/08/17.
 */

@Module
public class FavouriteModule {
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
        AppDatabase db = Room
                .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .build();
        return db;
    }
}
