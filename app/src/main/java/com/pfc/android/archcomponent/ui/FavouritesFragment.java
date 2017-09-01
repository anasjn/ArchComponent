package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by ana on 18/08/17.
 */

public class FavouritesFragment extends LifecycleFragment {

    private final String TAG = FavouritesFragment.class.getName();

    private UnifiedModelView unifiedModelView;

    protected RecyclerView mRecyclerView;
    protected FavouriteAdapter mFavouriteAdapter;
    //protected ArrivalAdapter mArrivalAdapter;

    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableFavourites = new MutableLiveData<>();

    private DefaultLocation currentLocation;

    private List<ArrivalsFormatedEntity> favourites = new ArrayList<>();

    public FavouritesFragment() {
    }

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
                unifiedModelView.setmMutableLiveDataFavourites();
            }
        });

        // Handle changes emitted by LiveData
        unifiedModelView.getmMutableLiveDataFavourites().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> favourites) {
                unifiedModelView.setmMutablePredictionsByStopLine(favourites);
            }
        });

        unifiedModelView.getmMutablePredictionsByStopPLine().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> favouriteEntities) {
                handleResponse(favouriteEntities);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++onCreateView");
        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        CustomDetailClickListener mFavouriteClickListener = new CustomDetailClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ArrivalsFormatedEntity favourite = mFavouriteAdapter.getFavourite(position);
                Toast.makeText(getContext(), "Line: "+ favourite.getLineId()+ " in Platform: " +favourite.getPlatformName()+ " towards: "+favourite.getDestinationName()+ " has been deleted from Favourites", Toast.LENGTH_SHORT).show();
                unifiedModelView.deleteFavourite(favourite);
                unifiedModelView.setmMutableLiveDataFavourites();
            }
        };

        mFavouriteAdapter = new FavouriteAdapter(getContext());
        mFavouriteAdapter.setOnItemClickListener(mFavouriteClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mFavouriteAdapter);
        return rootView;
    }


    private void handleResponse(List<ArrivalsFormatedEntity> favourites) {
        if (favourites != null && favourites.size()>0) {
            mFavouriteAdapter.addFavourites(favourites);
            mFavouriteAdapter.addCurrentLocation(this.currentLocation);
        } else {
            mFavouriteAdapter.clearFavourites();
            Toast.makeText(
                    getContext(),
                    "No arrival information found for the searched stop.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


}
