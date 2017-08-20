package com.pfc.android.archcomponent.di;

/**
 * Created by ana on 19/08/17.
 */
import com.pfc.android.archcomponent.ui.FavouritesFragment;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FavouriteModule.class })
public interface FavouriteComponent {
    void inject(FavouritesFragment favouritesFragment);

    void inject(AddFavouriteViewModel favouriteViewModel);
}
