package com.pfc.android.archcomponent.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.api.StopPointsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {

    private final String TAG = DataAdapter.class.getName();

    private List<StopPointsEntity> mStopPoints;

    /**
     * Initialize the dataset of the Adapter.
     *
     */

    public DataAdapter() {
        mStopPoints = new ArrayList<>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
           return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false));

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.v(TAG,"**************************** onBindViewHolder");
        if(mStopPoints!=null && mStopPoints.size()>0) {
            StopPointsEntity stop = mStopPoints.get(position);
            Log.v(TAG,"**************************** stop "+stop);
            if(stop!=null) {
                holder.mTextViewLetter.setText(stop.getStopLetter());
                holder.mTextViewCommonName.setText(stop.getCommonName());
                holder.mTextViewDistance.setText(">" + stop.getDistance().intValue() + "m");
                String lines = "";
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

    @Override
    public int getItemCount() {
        return mStopPoints.size();
    }

    public void addStopInformation(List <StopPointsEntity> stops) {
        mStopPoints.clear();
        mStopPoints.addAll(stops);
        Log.v(TAG,"adapter stops "+mStopPoints.size());
        notifyDataSetChanged();
    }

    public void clearStopInformation() {
        if (mStopPoints != null) {
            mStopPoints.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class Holder extends RecyclerView.ViewHolder {

        private final String TAG = Holder.class.getName();

        TextView mTextViewLetter;
        TextView mTextViewCommonName;
        TextView mTextViewLine;
        TextView mTextViewTowards;
        TextView mTextViewDistance;

        public Holder(View v) {
            super(v);
            Log.v(TAG,"**************************************************Holder ");
            mTextViewLetter = (TextView) v.findViewById(R.id.letter);
            mTextViewCommonName = (TextView) v.findViewById(R.id.common_name);
            mTextViewLine = (TextView) v.findViewById(R.id.line);
            mTextViewTowards = (TextView) v.findViewById(R.id.towards);
            mTextViewDistance = (TextView) v.findViewById(R.id.distance);
        }
    }
}
