package com.pfc.android.archcomponent.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Dagger 2 - Scope created for the API calls.
 * <p>
 * This interface allows us to have only one instance of Retrofit.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
@Scope
@Retention(RetentionPolicy.CLASS)
public @interface BuStopAppScope {
}
