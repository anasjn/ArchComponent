package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.db.AppDatabase;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.repository.LocalRepository;
import com.pfc.android.archcomponent.repository.LocalRepositoryImpl;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;


/**
 * Created by ana on 17/08/17.
 */

public class ArrivalAdapter  extends RecyclerView.Adapter<ArrivalAdapter.Holder> {

    private final String TAG = ArrivalAdapter.class.getName();

    Context mContext;
    private List<ArrivalsFormatedEntity> mArrivalsEntity;
    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener detailListener;

//    @Inject
//    public AppDatabase database;

    /**
     * Initialize the ArrayList of the Adapter.
     *
     */

    public ArrivalAdapter(Context context) {
        this.mContext = context;
        mArrivalsEntity = new ArrayList<>();
    }

    //Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
    //1.- getItemCount()
    //2.- onCreateViewHolder(ViewGroup parent, int viewType)
    //3.- onBindViewHolder(Holder holder, int position)
    @Override
    public int getItemCount() {
        return mArrivalsEntity.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArrivalAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_row, parent, false);
        final ArrivalAdapter.Holder mViewHolder = new ArrivalAdapter.Holder(row);
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(detailListener!=null) {
                    Log.v(TAG,"**************************************************ArrivalAdapter.Holder  onClick");
                    detailListener.onItemClick(v, mViewHolder.getAdapterPosition());
                }
            }
        });
        return mViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ArrivalAdapter.Holder holder, int position) {
        if(mArrivalsEntity!=null && mArrivalsEntity.size()>0) {
            ArrivalsFormatedEntity arrival = mArrivalsEntity.get(position);
            String timing = "Next bus in ";
            List<Integer> timesToStation;
            if(arrival!=null) {
                holder.mTextViewStationName.setText(arrival.getStationName());
                holder.mTextViewLineId.setText(arrival.getLineId());
                holder.mTextViewPlatformName.setText(arrival.getPlatformName());
                holder.mTextViewDestinationName.setText(arrival.getDestinationName());
                timesToStation = arrival.getTimeToStationSort();
                timing += timesToStation.get(0) + " mins.";
                holder.mTextViewTimeToStation.setText(timing);
                if(timesToStation.size()>1) {
                    timing = "The following in: ";
                    int last = timesToStation.size() - 1;
                    for (int i = 1; i < timesToStation.size(); i++) {
                        timing += timesToStation.get(i);
                        if (last == i) {
                            timing += " mins.";
                        } else {
                            timing += ", ";
                        }
                    }
                    holder.mTextViewTimeToStation2.setText(timing);
                }
                holder.mRadioButtonStar.setChecked(arrival.isFavourite());
                Log.v(TAG,"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ isfav? ");
            }
        }
    }


    //Adding the setter method on the CustomDetailClickListener
    public void setOnItemClickListener (final CustomDetailClickListener listener) {
        detailListener = listener;
    }

    public void addArrivalInformation(List <ArrivalsFormatedEntity> arrivals) {
        mArrivalsEntity.clear();
        mArrivalsEntity.addAll(arrivals);
        notifyDataSetChanged();
    }

    public List <ArrivalsFormatedEntity>  getArrivalsList() {
        notifyDataSetChanged();
        return mArrivalsEntity;

    }

    public void clearArrivalInformation() {
        if (mArrivalsEntity != null) {
            mArrivalsEntity.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * Whereas the use of the ViewHolder pattern is optional in ListView, RecyclerView enforces it.
     * This improves scrolling and performance by avoiding findViewById() for each cell.
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String TAG = ArrivalAdapter.Holder.class.getName();

        TextView mTextViewStationName, mTextViewLineId, mTextViewPlatformName, mTextViewDestinationName,mTextViewTimeToStation,mTextViewTimeToStation2;
        RadioButton mRadioButtonStar;

        public Holder(View view) {
            super(view);
            mTextViewStationName = (TextView) view.findViewById(R.id.station_name);
            mTextViewLineId = (TextView) view.findViewById(R.id.line_id);
            mTextViewPlatformName = (TextView) view.findViewById(R.id.platform_name);
            mTextViewDestinationName = (TextView) view.findViewById(R.id.destination_name);
            mTextViewTimeToStation = (TextView) view.findViewById(R.id.time_to_station1);
            mTextViewTimeToStation2 = (TextView) view.findViewById(R.id.time_to_station2);
            mRadioButtonStar = (RadioButton) view.findViewById(R.id.star_rb);
        }

        public Holder(View itemView,int ViewType,Context c) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        //Implements the onClick override method
        @Override
        public void onClick(View v) {
            if(detailListener!=null) {
                Log.v(TAG,"**************************************************ArrivalAdapter.Holder  onClick");
                detailListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
