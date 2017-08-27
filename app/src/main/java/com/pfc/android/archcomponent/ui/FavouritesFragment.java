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


import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.FavouriteAdapter;
import com.pfc.android.archcomponent.viewmodel.AddFavouriteViewModel;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.List;

/**
 * Created by ana on 18/08/17.
 */

public class FavouritesFragment extends LifecycleFragment {

    private final String TAG = FavouritesFragment.class.getName();
    private static final String ARG_FAV = "FavouriteEntity";

    private FavouriteEntity favouriteEntity;
    private AddFavouriteViewModel afViewModel;

    protected RecyclerView mRecyclerView;
    protected FavouriteAdapter adapter;

    public FavouritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        afViewModel = ViewModelProviders.of(this).get(AddFavouriteViewModel.class);

        if (getArguments() != null) {
            favouriteEntity = (FavouriteEntity)getArguments().getSerializable(ARG_FAV);
            afViewModel.addFavourite(favouriteEntity);
        }
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

        afViewModel.getFavourites().observe(this, new Observer <List<FavouriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavouriteEntity> favourites) {
                if(adapter==null){
                    adapter = new FavouriteAdapter(getContext(),favourites);
                    // Set CustomAdapter as the adapter for RecyclerView.
                    mRecyclerView.setAdapter(adapter);
                }else{
                    adapter.setFavourite(favourites);
                }
            }
        });

        return rootView;
    }
}
