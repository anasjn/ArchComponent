package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.api.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {

    private final String TAG = DataAdapter.class.getName();

    Context mContext;
    private List<StopPointsEntity> mStopPoints;
    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener detailListener;

    /**
     * Initialize the ArrayList of the Adapter.
     *
     */

    public DataAdapter(Context context) {
        this.mContext = context;
        mStopPoints = new ArrayList<>();
    }

    //Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
    //1.- getItemCount()
    //2.- onCreateViewHolder(ViewGroup parent, int viewType)
    //3.- onBindViewHolder(Holder holder, int position)
    @Override
    public int getItemCount() {
        return mStopPoints.size();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        final Holder mViewHolder = new Holder(row);
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(detailListener!=null) {
                    detailListener.onItemClick(v, mViewHolder.getAdapterPosition());
                }
            }
        });
        return mViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        String lines = "";
        if(mStopPoints!=null && mStopPoints.size()>0) {
            StopPointsEntity stop = mStopPoints.get(position);
            if(stop!=null) {
                holder.mTextViewLetter.setText(stop.getStopLetter());
                holder.mTextViewCommonName.setText(stop.getCommonName());
                holder.mTextViewDistance.setText(">" + stop.getDistance().intValue() + "m");

                Log.v(TAG,"**************************** Letter "+stop.getStopLetter());
                Log.v(TAG,"**************************** CommonName "+stop.getCommonName());
                Log.v(TAG,"**************************** distance "+stop.getDistance().intValue() );

                if (stop.getListlines() != null && stop.getListlines().size()>0) {
                    lines = stop.getListlines().get(0).getName();
                    Log.v(TAG, "+++++++++++++lines " + lines);
                    for (int i = 1; i < stop.getListlines().size(); i++) {
                        lines += " - " + stop.getListlines().get(i).getName();
                        holder.mTextViewLine.setText(lines);
                    }
                }
                if (stop.getAddproperties() != null && stop.getAddproperties().size()>0) {
                    for (int j = 0; j < stop.getAddproperties().size(); j++) {
                        if ("Towards".equals(stop.getAddproperties().get(j).getKey())) {
                            holder.mTextViewTowards.setText((CharSequence) stop.getAddproperties().get(j).getValue());
                            Log.v(TAG,"**************************** towards "+stop.getAddproperties().get(j).getValue() );
                        }

                    }
                }
            }
        }
    }


    //Adding the setter method on the CustomDetailClickListener
    public void setOnItemClickListener (final CustomDetailClickListener listener) {
        detailListener = listener;
    }

    public void addStopInformation(List <StopPointsEntity> stops) {
        mStopPoints.clear();
        mStopPoints.addAll(stops);
        Log.v(TAG,"adapter stops "+mStopPoints.size());
        notifyDataSetChanged();
    }

    public List <StopPointsEntity>  getStopInformation() {
        notifyDataSetChanged();
        Log.v(TAG,"stop mStopPoints "+mStopPoints.size());
        return mStopPoints;

    }

    public void clearStopInformation() {
        if (mStopPoints != null) {
            mStopPoints.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * Whereas the use of the ViewHolder pattern is optional in ListView, RecyclerView enforces it.
     * This improves scrolling and performance by avoiding findViewById() for each cell.
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String TAG = Holder.class.getName();

        TextView mTextViewLetter, mTextViewCommonName, mTextViewLine, mTextViewTowards, mTextViewDistance;

        public Holder(View view) {
            super(view);
            Log.v(TAG,"**************************************************Holder ");
            mTextViewLetter = (TextView) view.findViewById(R.id.letter);
            mTextViewCommonName = (TextView) view.findViewById(R.id.common_name);
            mTextViewLine = (TextView) view.findViewById(R.id.line);
            mTextViewTowards = (TextView) view.findViewById(R.id.towards);
            mTextViewDistance = (TextView) view.findViewById(R.id.distance);
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
                detailListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
