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
import com.pfc.android.archcomponent.adapters.ArrivalAdapter;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import java.util.List;

/**
 * DetailFragment extends LifecycleFragment
 * <p>
 * This is the fragment that is in charge of the detail. The detail in this case is a list of arrivals of
 * one stop selected by the user.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class DetailFragment extends LifecycleFragment{

    private final String tag = DetailFragment.class.getName();

    protected ArrivalAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    private UnifiedModelView unifiedModelView;

    private String lat = "";
    private String lon = "";

    /**
     * OnCreate Method
     * <p>
     * In this method we instantiate the ViewModel that we need in order to paint the User interface
     * and observe the ViewModel in order to refresh the UI when this ViewModel change.
     * <p>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unifiedModelView = ViewModelProviders.of(getActivity()).get(UnifiedModelView.class);

        String naptanId = "";
        Bundle args = getArguments();
        if(args!=null){
            naptanId = args.getString("naptanId");
            lat = args.getString("lat");
            lon = args.getString("lon");
        }

        unifiedModelView.setmMutableArrivalsFormated(naptanId,lat,lon);

        // Handle changes emitted by LiveData
        unifiedModelView.getmMutableArrivalsFormated().observe(this, new Observer<List<ArrivalsFormatedEntity>>() {
            @Override
            public void onChanged(@Nullable List<ArrivalsFormatedEntity> arrivalsFormatedEntities) {
                handleResponse(arrivalsFormatedEntities);
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

        CustomDetailClickListener mDetailClickListener = new CustomDetailClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ArrivalsFormatedEntity arrival = mAdapter.getArrivalsList().get(position);
                if(!arrival.isFavourite()){
                    Toast.makeText(getContext(), "Line: "+ arrival.getLineId()+ " in Platform: " +arrival.getPlatformName()+ " towards: "+arrival.getDestinationName()+ " has been added to Favourites", Toast.LENGTH_SHORT).show();
                    unifiedModelView.addFavourite(arrival);
                }else{
                    Toast.makeText(getContext(), "This element is already a favourite", Toast.LENGTH_SHORT).show();
                }
            }

        };
        mAdapter = new ArrivalAdapter(getContext());
        mAdapter.setOnItemClickListener(mDetailClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    /**
     * This Method handle the response, giving the List<ArrivalsFormatedEntity> to the adapter.
     * <p>
     *
     * @param   arrivals   List<ArrivalsFormatedEntity>
     */
    private void handleResponse(List<ArrivalsFormatedEntity> arrivals) {
        if (arrivals != null && !arrivals.isEmpty()) {
            mAdapter.addArrivalInformation(arrivals);
        } else {
            mAdapter.clearArrivalInformation();
        }
    }
}
