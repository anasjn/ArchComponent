package com.pfc.android.archcomponent.util;

import android.app.Application;

import com.pfc.android.archcomponent.di.DaggerFavouriteComponent;
import com.pfc.android.archcomponent.di.FavouriteComponent;
import com.pfc.android.archcomponent.di.FavouriteModule;

/**
 * FavouriteApplication extends Application
 * <p>
 * We should do all this work within a specialization of the Application class
 * since these instances should be declared only once throughout the entire life of the application
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */



public class FavouriteApplication extends Application {

        private FavouriteComponent favouriteComponent;

        @Override
        public void onCreate() {
            super.onCreate();

            // Dagger%COMPONENT_NAME%
            favouriteComponent = DaggerFavouriteComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .favouriteModule(new FavouriteModule(this))
                    .build();
        }

        public FavouriteComponent getFavComponent() {
            return favouriteComponent;
        }

}
