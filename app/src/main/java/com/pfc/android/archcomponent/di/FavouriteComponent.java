package com.pfc.android.archcomponent.di;

/**
 * Dagger 2 - Dependency Injection and Code injection.
 * <p>
 * Component that allows to inject in the code in the classes listed related to the ROOM backend
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FavouriteModule.class })
public interface FavouriteComponent {
    void inject (UnifiedModelView unifiedModelView);
}
