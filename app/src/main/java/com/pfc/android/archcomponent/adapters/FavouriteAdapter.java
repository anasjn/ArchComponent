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
import com.pfc.android.archcomponent.vo.ArrivalsEntity;
import com.pfc.android.archcomponent.vo.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ana on 19/08/17.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.Holder> {

    private final String TAG = FavouriteAdapter.class.getName();

    Context mContext;

    private List<FavouriteEntity> favourites;
    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
//    CustomDetailClickListener detailListener;
    /**
     * Initialize the ArrayList of the Adapter.
     *
     */

    public FavouriteAdapter(Context context,List<FavouriteEntity> favourites) {
        this.mContext = context;
        this.favourites = favourites;
        Log.v(TAG,"**************************************************FavouriteAdapter");
    }

    //Because your adapter subclasses RecyclerView.Adapter, you need to add the following methods:
    //1.- getItemCount()
    //2.- onCreateViewHolder(ViewGroup parent, int viewType)
    //3.- onBindViewHolder(Holder holder, int position)
    @Override
    public int getItemCount() {
        int size = 0;
        if(favourites!=null) {
            size = favourites.size();
        }
        return size;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavouriteAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_row, parent, false);
        final FavouriteAdapter.Holder mViewHolder = new FavouriteAdapter.Holder(row);
        Log.v(TAG,"**************************************************FavouriteAdapter onCreateView");
        return mViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FavouriteAdapter.Holder holder, int position) {
        Log.v(TAG,"**************************************************FavouriteAdapter onBindViewHolder");
        if(favourites!=null && favourites.size()>0) {
            FavouriteEntity favourite = favourites.get(position);
            if(favourite!=null) {
                holder.mTextViewLineId.setText(favourite.getmLineId());
                holder.mTextViewPlatformName.setText(favourite.getmPlatformName());
                holder.mTextViewDestinationName.setText(favourite.getmDestinationName());
            }
        }
    }


    public void setFavourite(List<FavouriteEntity> favourites) {
        this.favourites = favourites;
        this.notifyDataSetChanged();
    }

    public FavouriteEntity  getFavourite(int position) {
        notifyDataSetChanged();
        return favourites.get(position);
    }

    public void addFavouriteInformation(List <FavouriteEntity> favourites) {
        Log.v(TAG,"**************************************************FavouriteAdapter addFavouriteInformation");
        favourites.clear();
        favourites.addAll(favourites);
        notifyDataSetChanged();
    }

    public List <FavouriteEntity>  getFavourites() {
        Log.v(TAG,"**************************************************FavouriteAdapter getFavourites");
        notifyDataSetChanged();
        return favourites;

    }

    public void clearFavouriteInformation() {
        Log.v(TAG,"**************************************************FavouriteAdapter clearFavouriteInformation");
        if (favourites != null) {
            favourites.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * Whereas the use of the ViewHolder pattern is optional in ListView, RecyclerView enforces it.
     * This improves scrolling and performance by avoiding findViewById() for each cell.
     */
    public class Holder extends RecyclerView.ViewHolder {

        private final String TAG = ArrivalAdapter.Holder.class.getName();

        TextView  mTextViewLineId, mTextViewPlatformName, mTextViewDestinationName;

        public Holder(View view) {
            super(view);
            Log.v(TAG,"**************************************************FavouriteAdapter Holder");
            mTextViewLineId = (TextView) view.findViewById(R.id.line_id);
            mTextViewPlatformName = (TextView) view.findViewById(R.id.platform_name);
            mTextViewDestinationName = (TextView) view.findViewById(R.id.destination_name);
        }

        public Holder(View itemView,int ViewType,Context c) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            Log.v(TAG,"**************************************************FavouriteAdapter Holder2");
//            itemView.setClickable(true);
        }


    }
}
