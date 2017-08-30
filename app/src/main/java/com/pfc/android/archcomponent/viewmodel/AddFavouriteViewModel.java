package com.pfc.android.archcomponent.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.api.ApiResponse3;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.repository.IssueRepository;
import com.pfc.android.archcomponent.repository.IssueRepositoryImpl;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ana on 18/08/17.
 */

public class AddFavouriteViewModel extends AndroidViewModel {

    private final String TAG = AddFavouriteViewModel.class.getName();

    @Inject
    public AppDatabase database;

    private LiveData<List<FavouriteEntity>> favouritesEntity;

//    private MediatorLiveData<ApiResponse> mApiResponse;
//    private IssueRepository mIssueRepository;

    public AddFavouriteViewModel(Application application) {
        super(application);
        ((FavouriteApplication)application).getFavComponent().inject(this);
        favouritesEntity = database.favouriteDao().getAllFavourites();
    }

    @NonNull
    public LiveData<List<FavouriteEntity>> getFavourites() {
        return favouritesEntity;
    }

//    public Integer isFavourite(FavouriteEntity favouriteEntity){
//       final Integer[] result = {0};
//        new AsyncTask<FavouriteEntity, Integer, Void>() {
//            @Override
//            protected Void doInBackground(FavouriteEntity... params) {
//                result[0] = database.favouriteDao().isFavourite(favouriteEntity.getmNaptanId(),favouriteEntity.getmLineId());
//                Log.v(TAG,"+++++++++++++++++++++isFavourite result "+result[0]);
//                return null;
//            }
//        }.execute(favouriteEntity);
//       return result[0];
//    }

    public void addFavourite(FavouriteEntity favouriteEntity) {
        if(!favouriteEntity.isTransient()) {
            throw new RuntimeException("Task already added.");
        }
        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                database.favouriteDao().addFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }

    private void updateFavourite(FavouriteEntity favouriteEntity) {
        if(favouriteEntity.isTransient()) {
            throw new RuntimeException("Task is transient.");
        }

        new AsyncTask<FavouriteEntity, Integer, Void>() {
            @Override
            protected Void doInBackground(FavouriteEntity... params) {
                database.favouriteDao().updateFavourite(params[0]);
                return null;
            }
        }.execute(favouriteEntity);
    }


}
