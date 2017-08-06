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

    private final LayoutInflater mInflator;
    private List<StopPointsEntity> mStopPoints;

    public DataAdapter(LayoutInflater inflator) {
        Log.v(TAG,"********************************************** adapter constructor");
        mInflator = inflator;
        mStopPoints = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG,"**************************************** onCreateViewHolder");
        return new Holder(mInflator.inflate(R.layout.issue_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.v(TAG,"**************************** onBindViewHolder");
        StopPointsEntity stop = mStopPoints.get(position);
        holder.mTextViewLetter.setText(stop.getStopLetter());
        holder.mTextViewCommonName.setText(stop.getCommonName());
        holder.mTextViewDistance.setText(">"+stop.getDistance().intValue()+ "m");
        String lines = "";
            Log.v(TAG,"+++++++++++++lines " +stop.getListlines().size());

            if (stop.getListlines() != null) {
                lines = stop.getListlines().get(0).getName();
                for (int i = 1; i < stop.getListlines().size(); i++) {
                    lines +=  " - " + stop.getListlines().get(i).getName();
                    holder.mTextViewLine.setText(lines);
                }
            }
            if (stop.getAddproperties() != null) {
                for (int j = 0; j < stop.getAddproperties().size(); j++) {
                    if ("Towards".equals(stop.getAddproperties().get(j).getKey())) {
                        holder.mTextViewTowards.setText((CharSequence) stop.getAddproperties().get(j).getValue());
                    }

                }

            }
    }

    @Override
    public int getItemCount() {
        Log.v(TAG, "getItemCount " + mStopPoints.size());
        return mStopPoints.size();
    }

    public void addStopInformation(List <StopPointsEntity> stops) {
        Log.v(TAG,"addStopInformation");
        mStopPoints.clear();
        mStopPoints.addAll(stops);
        Log.v(TAG,"adapter stops "+mStopPoints.size());
        notifyDataSetChanged();
    }

    public void clearStopInformation() {
        Log.v(TAG,"clearIssues ");
        if (mStopPoints != null) {
            mStopPoints.clear();
        }
        notifyDataSetChanged();
    }

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
