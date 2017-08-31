//package com.pfc.android.archcomponent.viewmodel;
//
//import android.app.Application;
//import android.arch.lifecycle.AndroidViewModel;
//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.MediatorLiveData;
//import android.arch.lifecycle.MutableLiveData;
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModel;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.pfc.android.archcomponent.api.ApiResponse2;
//import com.pfc.android.archcomponent.db.AppDatabase;
//import com.pfc.android.archcomponent.repository.IssueRepository;
//import com.pfc.android.archcomponent.repository.IssueRepositoryImpl;
//
//import javax.inject.Inject;
//
///**
// * Created by ana on 17/08/17.
// */
//
//public class DetailViewModel extends AndroidViewModel {
//
//    private final String TAG = DetailViewModel.class.getName();
//
//    private MediatorLiveData<ApiResponse2> mApiResponse2;
//    private IssueRepository mIssueRepository;
//
//    @Inject
//    public AppDatabase database;
//
//    // No argument constructor
//    public DetailViewModel(Application application) {
//        super(application);
//        mApiResponse2 = new MediatorLiveData<>();
//        mIssueRepository = new IssueRepositoryImpl();
//    }
//
//
//
//    @NonNull
//    public LiveData<ApiResponse2> getApiResponse() {
//        return mApiResponse2;
//    }
//
//
//    public LiveData<ApiResponse2> loadArrivalInformation(@NonNull String naptan_id,@NonNull String app_id, @NonNull String app_key) {
//        MutableLiveData<ApiResponse2> aux= mIssueRepository.getArrivalInformation(naptan_id,app_id,app_key);
//        mApiResponse2.addSource(aux, new Observer<ApiResponse2>(){
//            @Override
//            public void onChanged(@Nullable ApiResponse2 apiResponse2) {
//                if(apiResponse2==null){
//                    Log.v(TAG,"Fetch data from API");
//                }else{
//                    mApiResponse2.removeSource(aux);
//                    mApiResponse2.setValue(apiResponse2);
//                }
//            }
//        });
//        return mApiResponse2;
//    }
//}
