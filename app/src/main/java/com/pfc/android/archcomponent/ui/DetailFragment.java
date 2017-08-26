package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.FavouriteEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.viewmodel.DetailViewModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by ana on 16/08/17.
 */

public class DetailFragment extends LifecycleFragment {

    private final String TAG = DetailFragment.class.getName();

    protected ArrivalAdapter mAdapter;
    private DetailViewModel dViewModel;
    protected RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dViewModel = ViewModelProviders.of(getActivity()).get(DetailViewModel.class);
        //user and password
        String app_id=getString(R.string.api_transport_id);
        String app_key=getString(R.string.api_transport_key);

        int position =0;
        Bundle args = getArguments();
        if(args!=null) {
            position = args.getInt("position");
        }

        StopPointsEntity stop = ApiResponse.getStop(position);

        dViewModel.loadArrivalInformation(stop.getNaptanId(),app_id,app_key);

        // Handle changes emitted by LiveData
        dViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse((List<ArrivalsEntity>) apiResponse.getArrivals());
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

                ArrivalsEntity arrival = ApiResponse2.getArrival(position);
                FavouriteEntity favourite = new FavouriteEntity (new Date(System.currentTimeMillis()),arrival.getLineId(),arrival.getPlatformName(),arrival.getDestinationName());

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

    private void handleResponse(List<ArrivalsEntity> arrivals) {
        if (arrivals != null && arrivals.size()>0) {
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
}
