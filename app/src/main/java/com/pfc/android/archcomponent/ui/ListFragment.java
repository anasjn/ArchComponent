package com.pfc.android.archcomponent.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.adapters.DataAdapter;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.StopLocationEntity;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.model.DefaultLocation;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;
import java.util.List;


/**
 * Created by ana on 12/08/17.
 */

public class ListFragment extends LifecycleFragment {

    private static final String TAG = ListFragment.class.getName();
    protected RecyclerView mRecyclerView;
    protected DataAdapter mAdapter;
    private UnifiedModelView unifiedModelView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        //Get current location
        unifiedModelView.getLmLocationLiveData().observe(this, new Observer<DefaultLocation>() {
            @Override
            public void onChanged(@Nullable DefaultLocation defaultLocation) {
                //set the stops near my current location
                unifiedModelView.setStopPointMutableLiveData(defaultLocation.getLatitude(), defaultLocation.getLongitude(),200);
            }
        });

        // Handle changes emitted by StopPointMutableLiveData
        unifiedModelView.getmStopPointMutableLiveData().observe(this, new Observer<StopLocationEntity>() {
            @Override
            public void onChanged(@Nullable StopLocationEntity stopLocationEntity) {
                handleResponse((List<StopPointsEntity>) stopLocationEntity.getStopPoints());
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
                arguments.putString("naptanId", mAdapter.getNaptanIdByPosition(position));
                arguments.putString("lat", mAdapter.getLatByPosition(position));
                arguments.putString("lon", mAdapter.getLonByPosition(position));
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

}
