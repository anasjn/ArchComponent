package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.ArrivalAdapter;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.api.ApiResponse;
import com.pfc.android.archcomponent.api.ArrivalsEntity;
import com.pfc.android.archcomponent.api.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.viewmodel.DetailViewModel;
import com.pfc.android.archcomponent.viewmodel.ListLocationsViewModel;
import com.pfc.android.archcomponent.viewmodel.LocationViewModel;

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
        dViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        //user and password
        String app_id=getString(R.string.api_transport_id);
        String app_key=getString(R.string.api_transport_key);

        //String naptanId = "";

        int position =0;
        Bundle args = getArguments();
        if(args!=null) {
            position = args.getInt("position");
            Log.v(TAG, "************************************************** onCreateView position " + position);
        }

        StopPointsEntity stop = ApiResponse.getStop(position);
        Log.v(TAG, "************************************************** onCreateView getApiResponse fuera " +stop.getNaptanId());

        // Handle changes emitted by LiveData
//        dViewModel.getApiResponse().observe(this, apiResponse -> {
//            Log.v(TAG, "************************************************** onCreateView getApiResponse " + stop.getNaptanId());
            //update the UI
            dViewModel.loadArrivalInformation(stop.getNaptanId(),app_id,app_key);
//        });

        // Handle changes emitted by LiveData
        dViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
                Log.v(TAG, "handleError distinto null ");
            } else {
                handleResponse((List<ArrivalsEntity>) apiResponse.getArrivals());
                Log.v(TAG, "handleResponse "+apiResponse.getArrivals().size());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_lines_view);


        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        Log.v(TAG, "************************************************** onCreateView ArrivalAdapter entrando");
        mAdapter = new ArrivalAdapter(getContext());
       // mAdapter.setOnItemClickListener(mDetailClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void handleResponse(List<ArrivalsEntity> arrivals) {
        if (arrivals != null && arrivals.size()>0) {
            Log.v(TAG,"hr arrivlas "+arrivals.size());
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
