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
import com.pfc.android.archcomponent.adapters.ArrivalAdapter;
import com.pfc.android.archcomponent.viewmodel.UnifiedModelView;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import java.util.List;


/**
 * Created by ana on 16/08/17.
 */

public class DetailFragment extends LifecycleFragment{

    private final String TAG = DetailFragment.class.getName();

    protected ArrivalAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    private UnifiedModelView unifiedModelView;

    private String lat = "";
    private String lon = "";

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
                ArrivalsFormatedEntity arrival = mAdapter.getArrivalsList().get(position);
                Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++en el click para guardar en la BBDD "+position +" isfav "+arrival.isFavourite() +" arrival.getDirection() "+ arrival.getDirection());
                if(!arrival.isFavourite()){
                    Toast.makeText(getContext(), "Line: "+ arrival.getLineId()+ " in Platform: " +arrival.getPlatformName()+ " towards: "+arrival.getDestinationName()+ " has been added to Favourites", Toast.LENGTH_SHORT).show();
                    unifiedModelView.addFavourite(arrival);
                    unifiedModelView.setmMutableLiveDataFavourites();
                }else{
                    Toast.makeText(getContext(), "Line: "+ arrival.getLineId()+ " in Platform: " +arrival.getPlatformName()+ " towards: "+arrival.getDestinationName()+ " has been deleted from Favourites", Toast.LENGTH_SHORT).show();
                    Log.v(TAG,"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++delete favourite "+arrival.getNaptanId() +" , "+arrival.getLineId());
                    unifiedModelView.deleteFavourite(arrival);
                    //unifiedModelView.setmMutableLiveDataFavourites();
                }
            }

        };
        mAdapter = new ArrivalAdapter(getContext());
        mAdapter.setOnItemClickListener(mDetailClickListener);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void handleResponse(List<ArrivalsFormatedEntity> arrivals) {
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
}
