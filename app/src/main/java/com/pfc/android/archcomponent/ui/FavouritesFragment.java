package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
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
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ana on 18/08/17.
 */

public class FavouritesFragment extends LifecycleFragment {

    private final String TAG = FavouritesFragment.class.getName();

    private UnifiedModelView unifiedModelView;

    protected RecyclerView mRecyclerView;
    protected FavouriteAdapter mAdapter;

    private List<FavouriteEntity> favourites = new ArrayList<>();

    public FavouritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        // Handle changes emitted by LiveData
        unifiedModelView.getmMutableLiveDataFavourites().observe(this, new Observer<List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> favourites) {
                handleResponse(favourites);
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
                FavouriteEntity favourite = mAdapter.getFavourite(position);
                Toast.makeText(getContext(), "Line: "+ favourite.getmLineId()+ " in Platform: " +favourite.getmPlatformName()+ " towards: "+favourite.getmDestinationName()+ " has been deleted from Favourites", Toast.LENGTH_SHORT).show();
                Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++favourite "+favourite.getmNaptanId() +" , "+favourite.getmLineId());
                unifiedModelView.deleteFavourite(favourite);
                unifiedModelView.setmMutableLiveDataFavourites();

            }
        };

        mAdapter = new FavouriteAdapter(getContext());
        mAdapter.setOnItemClickListener(mFavouriteClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    private void handleResponse(List<FavouriteEntity> favourites) {
        if (favourites != null && favourites.size()>0) {
            mAdapter.addFavourites(favourites);
        } else {
            mAdapter.clearFavourites();
            Toast.makeText(
                    getContext(),
                    "No arrival information found for the searched stop.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
