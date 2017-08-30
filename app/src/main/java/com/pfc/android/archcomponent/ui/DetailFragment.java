package com.pfc.android.archcomponent.ui;

import android.app.Application;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.ArrivalAdapter;
import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.api.ApiResponse2;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.repository.LocalRepositoryImpl;
import com.pfc.android.archcomponent.util.FavouriteApplication;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.vo.FavouriteEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.viewmodel.DetailViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ana on 16/08/17.
 */

public class DetailFragment extends LifecycleFragment {

    private final String TAG = DetailFragment.class.getName();

    protected ArrivalAdapter mAdapter;
//    private DetailViewModel dViewModel;
//    private AddFavouriteViewModel afViewModel;
    protected RecyclerView mRecyclerView;
    private UnifiedModelView unifiedModelView;

    //favourites?
//    private LocalRepositoryImpl localRepository;
//    private List<FavouriteEntity> favourites = new ArrayList<>();

//    @Inject
//    public AppDatabase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ((FavouriteApplication)this.getActivity().getApplication()).getFavComponent().inject(this);
//
        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

//        this.localRepository = new LocalRepositoryImpl(database);

//        localRepository.getFavourites().observe(this,new Observer<List<FavouriteEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<FavouriteEntity> favouriteEntities) {
//                favourites.addAll(favouriteEntities);
//            }
//        });

//        favourites.addAll(localRepository.getFavourites());

//        dViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
//
//        afViewModel = ViewModelProviders.of(this).get(AddFavouriteViewModel.class);
//        //user and password
//        String app_id=getString(R.string.api_transport_id);
//        String app_key=getString(R.string.api_transport_key);

//        int position =0;
//        Bundle args = getArguments();
//        if(args!=null) {
//            position = args.getInt("position");
//        }

        String naptanId = "";
        Bundle args = getArguments();
        if(args!=null){
            naptanId = args.getString("naptanId");
            Log.v(TAG, "argumento++++++++++++++++++++++++++++++++++"+naptanId);
        }

        //StopPointsEntity stop = ApiResponse.getStop(position);

        unifiedModelView.getArrivalInformation(naptanId);
//        dViewModel.loadArrivalInformation(stop.getNaptanId(),app_id,app_key);

//        // Handle changes emitted by LiveData
//        dViewModel.getApiResponse().observe(this, apiResponse -> {
//            if (apiResponse.getError() != null) {
//                handleError(apiResponse.getError());
//            } else {
//                handleResponse((List< ArrivalsFormatedEntity>) apiResponse.getArrivals());
//            }
//        });




        // Handle changes emitted by LiveData
        unifiedModelView.getmMutableArrivalsFormated().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> arrivalsFormatedEntities) {
                handleResponse(arrivalsFormatedEntities);
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        CustomDetailClickListener mDetailClickListener = new CustomDetailClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Save favourites in the DAtabase
                //change the background color of the radiobutton
                Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++en el click para guardar en la BBDD "+position);

                //ArrivalsFormatedEntity arrival = ApiResponse2.getArrival(position);
                ArrivalsFormatedEntity arrival = mAdapter.getArrivalsList().get(position);
                FavouriteEntity favourite = new FavouriteEntity (new Date(System.currentTimeMillis()),arrival.getLineId(),arrival.getPlatformName(),arrival.getDestinationName(),arrival.getNaptanId());
                Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++favourite "+favourite.getmNaptanId() +" , "+favourite.getmLineId());
                FragmentManager fm = getFragmentManager();
                FavouritesFragment favouritesfragment = new FavouritesFragment();
                Bundle args = new Bundle();
                args.putSerializable("FavouriteEntity", (Serializable) favourite);
                favouritesfragment.setArguments(args);
                fm.beginTransaction().replace(R.id.content_fragment, favouritesfragment).addToBackStack("favourite").commit();
            }
        };

        mAdapter = new ArrivalAdapter(getContext());
        mAdapter.setOnItemClickListener(mDetailClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void handleResponse(List<ArrivalsFormatedEntity> arrivals) {
        if (arrivals != null && arrivals.size()>0) {
//            boolean fav = false;
//            List<ArrivalsFormatedEntity> arrivalsWithFav = new ArrayList<>();
//            ArrivalsFormatedEntity arrivalFav;
//            Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ handleResponse ");
//            for(int i = 0; i<arrivals.size(); i ++){
//                Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ for ");
//                arrivalFav = arrivals.get(i);
////                fav = isFav(arrivalFav);
////                Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ fav "+fav);
//                arrivalFav.setFavourite(fav);
//                arrivalsWithFav.add(arrivalFav);
//            }
            mAdapter.addArrivalInformation(arrivals);
        } else {
            mAdapter.clearArrivalInformation();
            Toast.makeText(
                    getContext(),
                    "No arrival information found for the searched stop.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        mAdapter.clearArrivalInformation();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(getContext(), "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

//    private boolean isFav(ArrivalsFormatedEntity arrival){
//        Log.v(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ isFAV ");
//        return favourites.contains(arrival);
//    }

}
