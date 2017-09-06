package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pfc.android.archcomponent.R;
import com.pfc.android.archcomponent.model.CustomDetailClickListener;
import com.pfc.android.archcomponent.model.DefaultLocation;
import com.pfc.android.archcomponent.vo.ArrivalsFormatedEntity;

import java.util.List;

/**
 * FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder>
 * <p>
 * This Adapter is in charge of the interface elements that are showing in every recycler view for the favourites fragments.
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
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> {

    Context mContext;
    private List<ArrivalsFormatedEntity> favourites;

    // RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener favouriteListener;

    // In use only if this adapter is used by FavouriteFragment
    private DefaultLocation currentLocation;

    /**
     * Contructor with a context parameter and a list of ArrivalsFormatedEntity elements
     * <p>
     * Initialize the ArrayList and the context of the Adapter.
     *
     */
    public FavouriteAdapter(List<ArrivalsFormatedEntity> favourites,Context context) {
        this.mContext = context;
        this.favourites=favourites;
    }


    /**
     * Return the number of the elements in the list arrivalsFormatedEntity
     * <p>
     *
     * @return int size
     */
    @Override
    public int getItemCount() {
        return favourites.size();
    }

    /**
     * Create new views (invoked by the layout manager)
     * <p>
     *
     * @param  parent  ViewGroup.
     * @param  viewType  int.
     * @return    a FavouriteAdapter.Holder
     */
    @Override
    public FavouriteAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_row, parent, false);
        final FavouriteAdapter.Holder mViewHolder = new FavouriteAdapter.Holder(row);
        row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(favouriteListener!=null) {
                    favouriteListener.onItemClick(v, mViewHolder.getAdapterPosition());
                }

            }
        });
        return mViewHolder;

    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * <p>
     *
     * @param  holder  FavouriteAdapter.Holder.
     * @param  position  int.
     */
    @Override
    public void onBindViewHolder(final FavouriteAdapter.Holder holder, int position) {
        if(favourites!=null && !favourites.isEmpty()) {
            ArrivalsFormatedEntity favourite = favourites.get(position);
            String timing = mContext.getString(R.string.timing_first)+ " ";
            List<Integer> timesToStation;
            if(favourite!=null) {
                holder.mTextViewLineId.setText(favourite.getLineId());
                holder.mTextViewPlatformName.setText(favourite.getPlatformName());
                holder.mTextViewDestinationName.setText(favourite.getDestinationName());
                holder.mTextViewStationName.setText(favourite.getStationName());
                if(currentLocation!=null){
                   Double distance = 0.0;
                   distance = calDistance(Double.parseDouble(favourite.getLatitude()),Double.parseDouble(favourite.getLongitude()), currentLocation.getLatitude(), currentLocation.getLongitude());
                   holder.mTextViewDistance.setText(mContext.getString(R.string.separator_mayor)+ " " + distance.intValue() + " " +mContext.getString(R.string.separator_meters));
                }
                timesToStation = favourite.getTimeToStationSort();
                timing += timesToStation.get(0) + " " +mContext.getString(R.string.final_separtor);
                holder.mTextViewTimeToStation.setText(timing);
                if(timesToStation.size()>1) {
                    timing = mContext.getString(R.string.timing_next);
                    int last = timesToStation.size() - 1;
                    for (int i = 1; i < timesToStation.size(); i++) {
                        timing += timesToStation.get(i);
                        if (last == i) {
                            timing +=" "+ mContext.getString(R.string.final_separtor);
                        } else {
                            timing += mContext.getString(R.string.separator)+ " ";
                        }
                    }
                    holder.mTextViewTimeToStation2.setText(timing);
                }
            }
        }
    }

    /**
     * Adding the setter method on the CustomDetailClickListener
     * <p>
     *
     * @param  mFavouriteClickListener  CustomDetailClickListener.
     */
    public void setOnItemClickListener(CustomDetailClickListener mFavouriteClickListener) {
        favouriteListener = mFavouriteClickListener;
    }

    /**
     * Set the list of favourites
     * <p>
     *
     * @param  favourites  List<ArrivalsFormatedEntity>.
     */
    public void setFavourites(List<ArrivalsFormatedEntity> favourites) {
        this.favourites = favourites;
        this.notifyDataSetChanged();
    }

    /**
     * Get the favourite element in the positions given by the parameter
     * <p>
     *
     * @param position an int with the position in the list.
     * @return  an ArrivalsFormatedEntity element.
     */
    public ArrivalsFormatedEntity getFavourite(int position) {
        notifyDataSetChanged();
        return favourites.get(position);
    }

    /**
     * Add the list of favourites
     * <p>
     *
     * @param favourites List <ArrivalsFormatedEntity>
     */
    public void addFavourites(List <ArrivalsFormatedEntity> favourites) {
        this.favourites.clear();
        this.favourites.addAll(favourites);
        notifyDataSetChanged();
    }

    /**
     * Add current location
     * <p>
     *
     * @param defaultLocation DefaultLocation
     */
    public void addCurrentLocation (DefaultLocation defaultLocation){
        this.currentLocation = defaultLocation;
    }

    /**
     * Clear favourites list.
     * <p>
     */
    public void clearFavourites() {
        if (favourites != null) {
            favourites.clear();
        }
        notifyDataSetChanged();
    }


    /**
     * Method that implements Haversine Formula (from R.W. Sinnott, "Virtues of the Haversine", Sky and Telescope,
     * vol. 68, no. 2, 1984, p. 159): http://www.movable-type.co.uk/scripts/gis-faq-5.1.html
     * <p>
     * This calc the distance between 2 points following the Haversine Formula and return the distance in meters.
     *
     * @param  latPoint1  a double. Latitude point 1
     * @param  lonPoint1  a double. Longitude point 1
     * @param  latPoint2  a double. Latitude point 2
     * @param  lonPoint2  a double. Longitude point 2
     * @return  the distance between two points gave in the parameters.
     */
    public double calDistance (double latPoint1, double lonPoint1, double latPoint2, double lonPoint2){
        double dLat = Math.toRadians(latPoint2 - latPoint1);
        double dLon = Math.toRadians(lonPoint2 - lonPoint1);

        latPoint2 = Math.toRadians(latPoint2);
        latPoint1 = Math.toRadians(latPoint1);

        //radius of the Earth R = 6372.8 km = 3956 mi
        double earthRadious = 6372.8;// In kilometers

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.cos(latPoint1) *  Math.cos(latPoint2) * Math.pow(Math.sin(dLon / 2),2) ;
        double c = 2 * Math.asin(Math.sqrt(a));
        return earthRadious * c * 1000; //return the distance in meters.
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
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  mTextViewLineId;
        TextView mTextViewPlatformName;
        TextView mTextViewDestinationName;
        TextView mTextViewStationName;
        TextView mTextViewDistance;
        TextView mTextViewTimeToStation;
        TextView mTextViewTimeToStation2;

        /**
         * Contructor with a view parameter.
         * <p>
         */
        public Holder(View view) {
            super(view);
            mTextViewLineId = view.findViewById(R.id.line_id);
            mTextViewPlatformName = view.findViewById(R.id.platform_name);
            mTextViewDestinationName = view.findViewById(R.id.destination_name);
            mTextViewStationName = view.findViewById(R.id.station_name);
            mTextViewDistance = view.findViewById(R.id.distance);
            mTextViewTimeToStation = view.findViewById(R.id.time_to_station1);
            mTextViewTimeToStation2 = view.findViewById(R.id.time_to_station2);
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
            if(favouriteListener!=null) {
                favouriteListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
