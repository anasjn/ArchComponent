package com.pfc.android.archcomponent.di;

import com.pfc.android.archcomponent.api.TflApiService;

import dagger.Component;

/**
 * Dagger 2 - Dependency Injection and Code injection.
 * <p>
 * Component that allows to get one instance of Retrofit.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */

@BuStopAppScope
@Component(modules = {TflApiModule.class})
public interface TflApiComponent {
    /**
     * This method  allows get the retrofit instances calling one method that retrieve
     * a TlfService inside TflApiModule.
     */
    public TflApiService getRetrofit();

}
