package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrivalAdapter extends RecyclerView.Adapter<ArrivalAdapter.Holder>
 * <p>
 * This Adapter is in charge of the interface elements that are showing in every recycler view for the detail fragments.
 * Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
 * <ul>
 * <li> 1.- getItemCount()
 * <li> 2.- onCreateViewHolder(ViewGroup parent, int viewType)
 * <li> 3.- onBindViewHolder(Holder holder, int position)
 * </ul>
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class ArrivalAdapter  extends RecyclerView.Adapter<ArrivalAdapter.Holder> {

    Context mContext;
    private List<ArrivalsFormatedEntity> mArrivalsEntity;
    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener detailListener;

    /**
     * Contructor with a context parameter
     * <p>
     * Initialize the ArrayList and the context of the Adapter.
     *
     */
    public ArrivalAdapter(Context context) {
        this.mContext = context;
        mArrivalsEntity = new ArrayList<>();
    }

    /**
     * Return the number of the elements in the list arricalsFormatedEntity
     * <p>
     *
     * @return int size
     */
    @Override
    public int getItemCount() {
        return mArrivalsEntity.size();
    }

    /**
     * Create new views (invoked by the layout manager)
     * <p>
     *
     * @param  parent  ViewGroup.
     * @param  viewType  int.
     * @return    a ArrivalAdapter.Holder
     */
    @Override
    public ArrivalAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_row, parent, false);
        final ArrivalAdapter.Holder mViewHolder = new ArrivalAdapter.Holder(row);
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
     * @param  holder  ArrivalAdapter.Holder.
     * @param  position  int.
     */
    @Override
    public void onBindViewHolder(final ArrivalAdapter.Holder holder, int position) {
        if(mArrivalsEntity!=null && !mArrivalsEntity.isEmpty()) {
            ArrivalsFormatedEntity arrival = mArrivalsEntity.get(position);
            String timing = mContext.getString(R.string.timing_first)+" ";
            List<Integer> timesToStation;
            if(arrival!=null) {
                holder.mTextViewStationName.setText(arrival.getStationName());
                holder.mTextViewLineId.setText(arrival.getLineId());
                holder.mTextViewPlatformName.setText(arrival.getPlatformName());
                holder.mTextViewDestinationName.setText(arrival.getDestinationName());
                timesToStation = arrival.getTimeToStationSort();
                timing += timesToStation.get(0) + " "+mContext.getString(R.string.final_separtor);
                holder.mTextViewTimeToStation.setText(timing);
                if(timesToStation.size()>1) {
                    timing =  mContext.getString(R.string.timing_next)+" ";
                    int last = timesToStation.size() - 1;
                    for (int i = 1; i < timesToStation.size(); i++) {
                        timing += timesToStation.get(i);
                        if (last == i) {
                            timing += " "+mContext.getString(R.string.final_separtor);
                        } else {
                            timing += mContext.getString(R.string.separator) +" ";
                        }
                    }
                    holder.mTextViewTimeToStation2.setText(timing);
                }
                holder.mRadioButtonStar.setChecked(arrival.isFavourite());
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
     * Add the list of arrivals
     * <p>
     *
     * @param  arrivals  List<ArrivalsFormatedEntity>.
     */
    public void addArrivalInformation(List <ArrivalsFormatedEntity> arrivals) {
        mArrivalsEntity.clear();
        mArrivalsEntity.addAll(arrivals);
        notifyDataSetChanged();
    }

    /**
     * Get the list of arrivals
     * <p>
     *
     * @return  an ArrivalsFormatedEntity element.
     */
    public List <ArrivalsFormatedEntity>  getArrivalsList() {
        notifyDataSetChanged();
        return mArrivalsEntity;

    }
    /**
     * Clear arrivals list.
     * <p>
     */
    public void clearArrivalInformation() {
        if (mArrivalsEntity != null) {
            mArrivalsEntity.clear();
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
     * @version     "%I%, %G%"
     * @since       1.0
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTextViewStationName;
        TextView mTextViewLineId;
        TextView mTextViewPlatformName;
        TextView mTextViewDestinationName;
        TextView mTextViewTimeToStation;
        TextView mTextViewTimeToStation2;
        RadioButton mRadioButtonStar;

        /**
         * Constructor with a view parameter.
         * <p>
         */
        public Holder(View view) {
            super(view);
            mTextViewStationName = view.findViewById(R.id.station_name);
            mTextViewLineId = view.findViewById(R.id.line_id);
            mTextViewPlatformName = view.findViewById(R.id.platform_name);
            mTextViewDestinationName = view.findViewById(R.id.destination_name);
            mTextViewTimeToStation = view.findViewById(R.id.time_to_station1);
            mTextViewTimeToStation2 = view.findViewById(R.id.time_to_station2);
            mRadioButtonStar = view.findViewById(R.id.star_rb);
        }

        /**
         * Constructor with a view, viewType and a context parameters.
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
