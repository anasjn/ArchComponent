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
 * ListFragment extends LifecycleFragment
 * <p>
 * This is the fragment that is in charge of the list of stops near the location of the user.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class ListFragment extends LifecycleFragment {

    private static final String TAG = ListFragment.class.getName();
    protected RecyclerView mRecyclerView;
    protected DataAdapter mAdapter;
    private UnifiedModelView unifiedModelView;


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

    }

    /**
     * onResume Method
     * <p>
     * In this method we handle any changes that happens in the livedata for the stopoints.
     * <p>
     */
    @Override
    public void onResume() {
        super.onResume();
        observeHandlers();
    }
    /**
     * observeHandlers Method
     * <p>
     * In this method we handle changes emitted by the  location livedata and the stoppoints livedata
     * <p>
     */
    private void observeHandlers() {
        // Get current location
        unifiedModelView.getLmLocationLiveData().observe(this, defaultLocation -> {
            //set the stops near my current location
            unifiedModelView.setStopPointMutableLiveData(defaultLocation.getLatitude(), defaultLocation.getLongitude(),200);
        });

        // Handle changes emitted by StopPointMutableLiveData
        unifiedModelView.getmStopPointMutableLiveData().observe(this, stopLocationEntity -> {
            handleResponse( stopLocationEntity.getStopPoints() ) ;
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
        rootView.setTag(TAG);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
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

    /**
     * This Method handle the response, giving the List<StopPointsEntity> to the adapter.
     * <p>
     *
     * @param   stoppoints   List<StopPointsEntity>
     */
    private void handleResponse(List<StopPointsEntity> stoppoints) {
        if (stoppoints != null && !stoppoints.isEmpty()) {
            mAdapter.addStopInformation(stoppoints);
        } else {
            mAdapter.clearStopInformation();
        }
    }

}
