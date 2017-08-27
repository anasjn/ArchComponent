package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.viewmodel.ListLocationsViewModel;
import com.pfc.android.archcomponent.viewmodel.LocationViewModel;

import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by ana on 12/08/17.
 */

public class ListFragment extends LifecycleFragment {

    private static final String TAG = ListFragment.class.getName();

    LiveData<DefaultLocation> liveData = null;

    private static final String FRACTIONAL_FORMAT = "%.4f";
    private static final String ACCURACY_FORMAT = "%.1fm";

    protected RecyclerView mRecyclerView;
    protected DataAdapter mAdapter;
    private ListLocationsViewModel mViewModel;
    //From the activity
    private LocationViewModel lViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ListLocationsViewModel.class);

        // Initialize location, getting the location from the Activity
        lViewModel =  ViewModelProviders.of(getActivity()).get(LocationViewModel.class);

        //user and password
        String app_id=getString(R.string.api_transport_id);
        String app_key=getString(R.string.api_transport_key);

        liveData = lViewModel.getLocation(getContext());
        Log.v(TAG, "+++++++++++++++++++++++++liveData "+liveData);
        liveData.observe(this,new Observer<DefaultLocation>(){
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation){
                Log.v(TAG, "liveData observe +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ "+defaultLocation.getLatitude() );
                mViewModel.loadStopInformation(app_id,app_key,defaultLocation.getLatitude(), defaultLocation.getLongitude(),200);
//                mViewModel.loadStopInformation(app_id,app_key,51.509865,-0.118092,200);
            }
        });

        // Handle changes emitted by LiveData
        mViewModel.getApiResponse().observe(this, apiResponse -> {
            if (apiResponse.getError() != null) {
                handleError(apiResponse.getError());
            } else {
                handleResponse(apiResponse.getStopLocation());
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

                FragmentManager fm = getFragmentManager();
                Bundle arguments = new Bundle();
                arguments.putInt("position", position);
                DetailFragment detailfragment = new DetailFragment();
                detailfragment.setArguments(arguments);
                fm.beginTransaction().replace(R.id.content_fragment, detailfragment).addToBackStack("detail").commit();
            }
        };
        mAdapter = new DataAdapter(getContext());
        mAdapter.setOnItemClickListener(mDetailClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }


    private void handleResponse(List<StopPointsEntity> stoppoints) {
        if (stoppoints != null && stoppoints.size()>0) {
            mAdapter.addStopInformation(stoppoints);
        } else {
            mAdapter.clearStopInformation();
            Toast.makeText(
                    getContext(),
                    "No stop information found for the searched repository.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void handleError(Throwable error) {
        mAdapter.clearStopInformation();
        Log.e(TAG, "error occured: " + error.toString());
        Toast.makeText(getActivity().getBaseContext(), "Oops! Some error occured.", Toast.LENGTH_SHORT).show();
    }

}
