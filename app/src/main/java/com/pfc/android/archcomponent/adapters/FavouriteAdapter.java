package com.pfc.android.archcomponent.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by ana on 19/08/17.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> {

    private final String TAG = FavouriteAdapter.class.getName();

    Context mContext;
    private List<ArrivalsFormatedEntity> favourites;
    //ASJ commment
//    private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableFavourites;
    //private MutableLiveData<List<ArrivalsFormatedEntity>> mMutableArrrivals;

    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener favouriteListener;


    private DefaultLocation currentLocation;
    /**
     * Initialize the ArrayList of the Adapter.
     *
     */

    public FavouriteAdapter(List<ArrivalsFormatedEntity> favourites,Context context) {
        Log.v(TAG, "constructor Fvourite Adapter");
        this.mContext = context;
        //ASJ comment
        //this.favourites = new ArrayList<>();
//        this.mMutableFavourites = new MutableLiveData<>();

        //ASJ add
        this.favourites=favourites;
      //  this.mMutableArrrivals = new MutableLiveData<>();
    }


    //Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
    //1.- getItemCount()
    //2.- onCreateViewHolder(ViewGroup parent, int viewType)
    //3.- onBindViewHolder(Holder holder, int position)
    @Override
    public int getItemCount() {
        return favourites.size();
    }

    // Create new views (invoked by the layout manager)
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


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FavouriteAdapter.Holder holder, int position) {
        if(favourites!=null && favourites.size()>0) {
            ArrivalsFormatedEntity favourite = favourites.get(position);
            String timing = "Next bus in ";
            List<Integer> timesToStation;
            if(favourite!=null) {
                Log.v(TAG,"naptan"+favourite.getNaptanId());
                Log.v(TAG,"getLineId"+favourite.getLineId());
                Log.v(TAG,"getStopLetter"+ favourite.getStopLetter());
                        Log.v(TAG,"getStationName"+favourite.getStationName());
                Log.v(TAG,"getPlatformName"+favourite.getPlatformName());
                Log.v(TAG,"getDestinationName"+favourite.getDestinationName());
                Log.v(TAG,"getDirection"+favourite.getDirection());
                Log.v(TAG,"isFavourite"+favourite.isFavourite());
                        Log.v(TAG,"getTimeToStationSort"+favourite.getTimeToStationSort());
                holder.mTextViewLineId.setText(favourite.getLineId());
                holder.mTextViewPlatformName.setText(favourite.getPlatformName());
                holder.mTextViewDestinationName.setText(favourite.getDestinationName());
                holder.mTextViewStationName.setText(favourite.getStationName());
                Double distance = 0.0;
                if(currentLocation!=null && favourite !=null){
                   distance = calDistance(Double.parseDouble(favourite.getmLat()),Double.parseDouble(favourite.getmLon()), currentLocation.getLatitude(), currentLocation.getLongitude());
                }
                holder.mTextViewDistance.setText(">" + distance.intValue() + "m");
                timesToStation = favourite.getTimeToStationSort();
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
            }
        }
    }

    //Adding the setter method on the CustomDetailClickListener
    public void setOnItemClickListener(CustomDetailClickListener mFavouriteClickListener) {
        favouriteListener = mFavouriteClickListener;
    }



    public void setFavourites(List<ArrivalsFormatedEntity> favourites) {
        this.favourites = favourites;
        this.notifyDataSetChanged();
    }

    public ArrivalsFormatedEntity getFavourite(int position) {
        notifyDataSetChanged();
        return favourites.get(position);
    }

    public void addFavourites(List <ArrivalsFormatedEntity> favourites) {
        this.favourites.clear();
        this.favourites.addAll(favourites);
        notifyDataSetChanged();
    }
//ASJ Comment
//    public List <ArrivalsFormatedEntity> getFavourites() {
//        Log.v(TAG,"**************************************************FavouriteAdapter getFavourites favourites "+favourites);
//        notifyDataSetChanged();
//        return favourites;
//
//    }
//ASJ commment
//    public MutableLiveData<List<ArrivalsFormatedEntity>> getMutableFavourites() {
//        Log.v(TAG,"**************************************************FavouriteAdapter getMutableFavourites favourites "+favourites);
//        notifyDataSetChanged();
//        return mMutableFavourites;
//
//    }

    public void addCurrentLocation (DefaultLocation defaultLocation){
        this.currentLocation = defaultLocation;
    }

    public void clearFavourites() {
        Log.v(TAG,"**************************************************FavouriteAdapter clearFavouriteInformation");
        if (favourites != null) {
            favourites.clear();
        }
        notifyDataSetChanged();
    }

    //Haversine Formula (from R.W. Sinnott, "Virtues of the Haversine", Sky and Telescope, vol. 68, no. 2, 1984, p. 159):
    //http://www.movable-type.co.uk/scripts/gis-faq-5.1.html
    public double calDistance (double latPoint1, double lonPoint1, double latPoint2, double lonPoint2){
        double dLat = Math.toRadians(latPoint2 - latPoint1);
        double dLon = Math.toRadians(lonPoint2 - lonPoint1);

        latPoint2 = Math.toRadians(latPoint2);
        latPoint1 = Math.toRadians(latPoint1);

        //radius of the Earth R = 6372.8 km = 3956 mi
        double earthRadious = 6372.8;// In kilometers

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.cos(latPoint1) *  Math.cos(latPoint2) * Math.pow(Math.sin(dLon / 2),2) ;
        double c = 2 * Math.asin(Math.sqrt(a));
        return earthRadious * c;
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * Whereas the use of the ViewHolder pattern is optional in ListView, RecyclerView enforces it.
     * This improves scrolling and performance by avoiding findViewById() for each cell.
     */
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final String TAG = ArrivalAdapter.Holder.class.getName();

        TextView  mTextViewLineId, mTextViewPlatformName, mTextViewDestinationName,mTextViewStationName,mTextViewDistance,mTextViewTimeToStation,mTextViewTimeToStation2;

        public Holder(View view) {
            super(view);
            mTextViewLineId = (TextView) view.findViewById(R.id.line_id);
            mTextViewPlatformName = (TextView) view.findViewById(R.id.platform_name);
            mTextViewDestinationName = (TextView) view.findViewById(R.id.destination_name);
            mTextViewStationName = (TextView) view.findViewById(R.id.station_name);
            mTextViewDistance = (TextView) view.findViewById(R.id.distance);
            mTextViewTimeToStation = (TextView) view.findViewById(R.id.time_to_station1);
            mTextViewTimeToStation2 = (TextView) view.findViewById(R.id.time_to_station2);
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
            if(favouriteListener!=null) {
                favouriteListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
}
