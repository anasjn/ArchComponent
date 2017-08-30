package com.pfc.android.archcomponent.di;

/**
 * Created by ana on 19/08/17.
 */
import com.pfc.android.archcomponent.adapters.ArrivalAdapter;
import com.pfc.android.archcomponent.ui.DetailFragment;
import com.pfc.android.archcomponent.ui.FavouritesFragment;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;
import com.pfc.android.archcomponent.viewmodel.DetailViewModel;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { FavouriteModule.class })
public interface FavouriteComponent {
    void inject(FavouritesFragment favouritesFragment);

    void inject(DetailFragment detailFragment);

    void inject(AddFavouriteViewModel favouriteViewModel);

    void inject (DetailViewModel detailViewModel);

    void inject (ArrivalAdapter arrivalAdapter);

    void inject (UnifiedModelView unifiedModelView);
}
