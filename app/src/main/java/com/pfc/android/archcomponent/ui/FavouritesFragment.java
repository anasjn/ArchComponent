package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.FavouriteAdapter;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * FavouritesFragment extends LifecycleFragment
 * <p>
 * This is the fragment that is in charge of the list of favurites. The detail in this case is a list
 * of lines selected as favourites for the user at some point.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */

public class FavouritesFragment extends LifecycleFragment {

    private final String tag = FavouritesFragment.class.getName();

    private UnifiedModelView unifiedModelView;

    protected RecyclerView mRecyclerView;
    protected FavouriteAdapter mFavouriteAdapter;

    private DefaultLocation currentLocation;

    /**
     * Empty constructor
     * <p>
     */
    public FavouritesFragment() {
    }

    /**
     * onResume Method
     * <p>
     * In this method we handle any changes that happens in the livedata for favourites and for the arrivals of the lines.
     * <p>
     */
    @Override
    public void onResume() {
        super.onResume();
        observeHandlers();
    }

    /**
     * OnCreate Method
     * <p>
     * In this method we instantiate the ViewModel that we need in order to get the location of the user
     * <p>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        //Get current location
        unifiedModelView.getLmLocationLiveData().observe(this, new Observer<DefaultLocation>() {
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation) {
                //set the favourites
                currentLocation = defaultLocation;
            }
        });
        observeHandlers();
    }

    /**
     * observeHandlers Method
     * <p>
     * In this method In this method we handle changes emitted by the favourites livedata and the predictions by stop and line livedata
     * <p>
     */
    public void observeHandlers(){

        unifiedModelView.getmLiveDataFavourites().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> arrivalsFormatedEntities) {
               unifiedModelView.setmMutableFavLines(arrivalsFormatedEntities);
            }
        });

        unifiedModelView.getmMutablePredictionsByStopPLine().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> favouriteEntities) {
                handleResponse(favouriteEntities);
            }
        });

    }

    /**
     * onCreateView Method
     * <p>
     * In this method we generate the RecyclerView for the list of elements that we are going to show.
     * <p>
     * LinearLayoutManager is used here, this will layout the elements in a similar fashion
     * to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
     * elements are laid out.
     *
     * @param   inflater   LayoutInflater
     * @param   container  ViewGroup
     * @param   savedInstanceState  Bundle
     * @return a View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        rootView.setTag(tag);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        CustomDetailClickListener mFavouriteClickListener = new CustomDetailClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ArrivalsFormatedEntity favourite = mFavouriteAdapter.getFavourite(position);
                Toast.makeText(getContext(), "Line: "+ favourite.getLineId()+ " in Platform: " +favourite.getPlatformName()+ " towards: "+favourite.getDestinationName()+ " has been deleted from Favourites", Toast.LENGTH_SHORT).show();
                unifiedModelView.deleteFavourite(favourite);
            }
        };

        mFavouriteAdapter = new FavouriteAdapter(new ArrayList<ArrivalsFormatedEntity>(),getContext());
        mFavouriteAdapter.setOnItemClickListener(mFavouriteClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mFavouriteAdapter);
        return rootView;
    }

    /**
     * This Method handle the response, giving the List<ArrivalsFormatedEntity> to the adapter.
     * <p>
     *
     * @param   favourites   List<ArrivalsFormatedEntity>
     */
    private void handleResponse(List<ArrivalsFormatedEntity> favourites) {
        if (favourites != null && !favourites.isEmpty()) {
            mFavouriteAdapter.addFavourites(favourites);
            mFavouriteAdapter.addCurrentLocation(this.currentLocation);
        } else {
            mFavouriteAdapter.clearFavourites();
        }
    }


}
