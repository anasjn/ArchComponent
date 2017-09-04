package com.pfc.android.archcomponent.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2 - This class give us the context that we need for the TflApiModule.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
@Module
public class ContextModule {

    private final Context context;

    /**
     * Contructor with a context parameter.
     * <p>
     * @param context a Context of the application.
     */
    public ContextModule(Context context) {
        this.context = context;
    }

    /**
     * This method  allows get a context.
     * <p>
     * @return     a context.
     */
    @Provides
    @BuStopAppScope
    public Context provideContext() {
        return context;
    }
}

