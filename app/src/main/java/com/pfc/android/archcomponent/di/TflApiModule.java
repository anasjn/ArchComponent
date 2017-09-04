package com.pfc.android.archcomponent.di;

import android.content.Context;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.api.TflApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger 2 - Dependency Injection and Code injection.
 * <p>
 * Module provide the Retrofit instance over Transport API URL.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
@Module(includes = {ContextModule.class})
public class TflApiModule {

    /**
     * Method with one parameter: retrofit
     * <p>
     * This method create the retrofit instance using our service.
     *
     * @param  retrofit  a Retrofit object, in order to have this instance we need to execute
     *                   a method which return a Retrofit object: @method provideRetrofit
     * @return an instance of TflApiService
     */
    @Provides
    @BuStopAppScope
    public TflApiService provideTflApiService(Retrofit retrofit) {
        return retrofit.create(TflApiService.class);
    }

    /**
     * Method with one parameter: context. As we don't have any context in the call, we need to execute some
     * method in order to have the context. ContextModule allows us to have this context.
     * <p>
     * This method biuld the retrofit instance for an specific URL
     *
     * @param  context  a context in ordert to have access to the elements of this application.
     * @return an instance of Retrofit
     */
    @Provides
    @BuStopAppScope
    public Retrofit provideRetrofit(Context context) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(context.getString(R.string.api_transport_endpoint))
                .build();
    }
}
