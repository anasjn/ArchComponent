package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.vo.StopPointsEntity;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder>
 * <p>
 * This Adapter is in charge of the interface elements that are showing in every recycler view for the listfragment.
 * Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
 * <ul>
 * <li> 1.- getItemCount()
 * <li> 2.- onCreateViewHolder(ViewGroup parent, int viewType)
 * <li> 3.- onBindViewHolder(Holder holder, int position)
 * </ul>
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {

    private final String TAG = DataAdapter.class.getName();

    Context mContext;
    private List<StopPointsEntity> mStopPoints;

    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener detailListener;

    /**
     * Contructor with a context parameter
     * <p>
     * Initialize the ArrayList and the context of the Adapter.
     *
     */
    public DataAdapter(Context context) {
        this.mContext = context;
        mStopPoints = new ArrayList<>();
    }

    /**
     * Return the number of the elements in the list arricalsFormatedEntity
     * <p>
     *
     * @return int size
     */
    @Override
    public int getItemCount() {
        return mStopPoints.size();
    }

    /**
     * Create new views (invoked by the layout manager)
     * <p>
     *
     * @param  parent  ViewGroup.
     * @param  viewType  int.
     * @return    a Holder
     */
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

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * <p>
     *
     * @param  holder  Holder.Holder.
     * @param  position  int.
     */
    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        String lines = "";
        if(mStopPoints!=null && mStopPoints.size()>0) {
            StopPointsEntity stop = mStopPoints.get(position);
            if(stop!=null) {
                holder.mTextViewLetter.setText(stop.getStopLetter());
                holder.mTextViewCommonName.setText(stop.getCommonName());
                holder.mTextViewDistance.setText(mContext.getString(R.string.separator_mayor) + stop.getDistance().intValue() + mContext.getString(R.string.separator_meters));
                if (stop.getListlines() != null && stop.getListlines().size()>0) {
                    lines = stop.getListlines().get(0).getName();
                    for (int i = 1; i < stop.getListlines().size(); i++) {
                        lines += " - " + stop.getListlines().get(i).getName();
                    }
                    holder.mTextViewLine.setText(lines);
                }
                if (stop.getAddproperties() != null && stop.getAddproperties().size()>0) {
                    for (int j = 0; j < stop.getAddproperties().size(); j++) {
                        if ("Towards".equals(stop.getAddproperties().get(j).getKey())) {
                            holder.mTextViewTowards.setText(mContext.getString(R.string.towards)+(CharSequence) stop.getAddproperties().get(j).getValue());
                        }

                    }
                }
            }
        }
    }


    /**
     * Adding the setter method on the CustomDetailClickListener
     * <p>
     *
     * @param  listener  CustomDetailClickListener.
     */
    public void setOnItemClickListener (final CustomDetailClickListener listener) {
        detailListener = listener;
    }
    /**
     * Set the list of favourites
     * <p>
     *
     * @param  stops  List<StopPointsEntity>.
     */
    public void addStopInformation(List <StopPointsEntity> stops) {
        mStopPoints.clear();
        mStopPoints.addAll(stops);
        notifyDataSetChanged();
    }
    /**
     * Get the list of the StopPointsEntity.
     * <p>
     *
     * @return  a list of StopPointsEntity elements.
     */
    public List <StopPointsEntity>  getStopInformation() {
        notifyDataSetChanged();
        return mStopPoints;

    }
    /**
     * Get the NaptanId to the given elements of the list in the position specified by the parameter
     * <p>
     *
     * @param position an int with the position in the list.
     * @return  an String NaptanId.
     */
    public String getNaptanIdByPosition(int position) {
        notifyDataSetChanged();
        return mStopPoints.get(position).getNaptanId();

    }
    /**
     * Get the latitude to the given elements of the list in the position specified by the parameter
     * <p>
     *
     * @param position an int with the position in the list.
     * @return  an String  latitude.
     */
    public String getLatByPosition(int position) {
        notifyDataSetChanged();
        return mStopPoints.get(position).getLat();

    }
    /**
     * Get the longitude to the given elements of the list in the position specified by the parameter
     * <p>
     *
     * @param position an int with the position in the list.
     * @return  an String  longitude.
     */
    public String getLonByPosition(int position) {
        notifyDataSetChanged();
        return mStopPoints.get(position).getLon();

    }
    /**
     * Clear stopPoints list.
     * <p>
     */
    public void clearStopInformation() {
        if (mStopPoints != null) {
            mStopPoints.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Holder extends RecyclerView.ViewHolder and implements View.OnClickListener
     * <p>
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * Whereas the use of the ViewHolder pattern is optional in ListView, RecyclerView enforces it.
     * This improves scrolling and performance by avoiding findViewById() for each cell.
     * <p>
     *
     * @author      Ana San Juan
     * @version     1.0
     * @since       1.0
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final String TAG = Holder.class.getName();

        TextView mTextViewLetter, mTextViewCommonName, mTextViewLine, mTextViewTowards, mTextViewDistance;

        /**
         * Contructor with a view parameter.
         * <p>
         */
        public Holder(View view) {
            super(view);
            mTextViewLetter = (TextView) view.findViewById(R.id.letter);
            mTextViewCommonName = (TextView) view.findViewById(R.id.common_name);
            mTextViewLine = (TextView) view.findViewById(R.id.line);
            mTextViewTowards = (TextView) view.findViewById(R.id.towards);
            mTextViewDistance = (TextView) view.findViewById(R.id.distance);
        }

        /**
         * Contructor with a view, viewType and a context parameters.
         * <p>
         *
         */
        public Holder(View itemView,int ViewType,Context c) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        /**
         * Implements the onClick override method
         * <p>
         *
         * return view View
         */
        @Override
        public void onClick(View v) {
            if(detailListener!=null) {
                detailListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
