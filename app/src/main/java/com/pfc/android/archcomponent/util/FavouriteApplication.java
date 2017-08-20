package com.pfc.android.archcomponent.util;

import android.app.Application;

import com.pfc.android.archcomponent.di.DaggerFavouriteComponent;
import com.pfc.android.archcomponent.di.FavouriteComponent;
import com.pfc.android.archcomponent.di.FavouriteModule;

/**
 * Created by ana on 19/08/17.
 */
// We should do all this work within a specialization of the Application class
// since these instances should be declared only once throughout the entire lifespan of the application
public class FavouriteApplication extends Application {

        private FavouriteComponent favouriteComponent;

        @Override
        public void onCreate() {
            super.onCreate();

            // Dagger%COMPONENT_NAME%
            favouriteComponent = DaggerFavouriteComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .favouriteModule(new FavouriteModule(this)) // This also corresponds to the name of your module: %component_name%Module
                    //.netModule(new NetModule("https://api.github.com"))
                    .build();

            // If a Dagger 2 component does not have any constructor arguments for any of its modules,
            // then we can use .create() as a shortcut instead:
            //  mNetComponent = com.codepath.dagger.components.DaggerNetComponent.create();
        }


        public FavouriteComponent getTaskComponent() {
            return favouriteComponent;
        }

}
