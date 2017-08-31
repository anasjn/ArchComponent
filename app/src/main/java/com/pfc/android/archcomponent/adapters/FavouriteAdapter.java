package com.pfc.android.archcomponent.adapters;

import android.arch.lifecycle.MutableLiveData;
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
    private MutableLiveData<List<FavouriteEntity>> mMutablefavourites;
    //RecyclerView doesn't come with an onItemClick interface, so we have
    // to implement one in the adapter.This is the field that hold an instance of CustomDetailClickListener
    CustomDetailClickListener favouriteListener;
    /**
     * Initialize the ArrayList of the Adapter.
     *
     */

    public FavouriteAdapter(Context context) {
        Log.v(TAG,"**************************************************FavouriteAdapter Constructor");
        this.mContext = context;
        this.favourites = new ArrayList<>();
        this.mMutablefavourites = new MutableLiveData<>();
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
                    Log.v(TAG,"**************************************************FavouriteAdapter.Holder  onClick");
                    favouriteListener.onItemClick(v, mViewHolder.getAdapterPosition());
                }
            }
        });
        return mViewHolder;

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final FavouriteAdapter.Holder holder, int position) {
        Log.v(TAG,"**************************************************FavouriteAdapter onBindViewHolder");
        if(favourites!=null && favourites.size()>0) {
            FavouriteEntity favourite = favourites.get(position);
            if(favourite!=null) {
                Log.v(TAG,"**************************************************FavouriteAdapter onBindViewHolder favourite"+favourite);
                holder.mTextViewLineId.setText(favourite.getmLineId());
                holder.mTextViewPlatformName.setText(favourite.getmPlatformName());
                holder.mTextViewDestinationName.setText(favourite.getmDestinationName());
            }
        }
    }

    //Adding the setter method on the CustomDetailClickListener
    public void setOnItemClickListener(CustomDetailClickListener mFavouriteClickListener) {
        favouriteListener = mFavouriteClickListener;
    }

    public void setFavourites(List<FavouriteEntity> favourites) {
        this.favourites = favourites;
        this.notifyDataSetChanged();
        Log.v(TAG,"**************************************************FavouriteAdapter setFavourites ");
    }

    public FavouriteEntity getFavourite(int position) {
        notifyDataSetChanged();
        Log.v(TAG,"**************************************************FavouriteAdapter getFavourite ");
        return favourites.get(position);
    }

    public void addFavourites(List <FavouriteEntity> favourites) {
        Log.v(TAG,"**************************************************FavouriteAdapter addFavourites");
        this.favourites.clear();
        Log.v(TAG,"**************************************************FavouriteAdapter addFavourites favourites "+favourites);
        this.favourites.addAll(favourites);
        notifyDataSetChanged();
    }

    public List <FavouriteEntity> getFavourites() {
        Log.v(TAG,"**************************************************FavouriteAdapter getFavourites favourites "+favourites);
        notifyDataSetChanged();
        return favourites;

    }

    public MutableLiveData<List<FavouriteEntity>> getMutableFavourites() {
        Log.v(TAG,"**************************************************FavouriteAdapter getFavourites favourites "+favourites);
        notifyDataSetChanged();
        return mMutablefavourites;

    }


    public void clearFavourites() {
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
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final String TAG = ArrivalAdapter.Holder.class.getName();

        TextView  mTextViewLineId, mTextViewPlatformName, mTextViewDestinationName;

        public Holder(View view) {
            super(view);
            Log.v(TAG,"**************************************************FavouriteAdapter Holder constructor view solo");
            mTextViewLineId = (TextView) view.findViewById(R.id.line_id);
            mTextViewPlatformName = (TextView) view.findViewById(R.id.platform_name);
            mTextViewDestinationName = (TextView) view.findViewById(R.id.destination_name);
        }

        public Holder(View itemView,int ViewType,Context c) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);
            Log.v(TAG,"**************************************************FavouriteAdapter Holder Constructor");
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        //Implements the onClick override method
        @Override
        public void onClick(View v) {
            if(favouriteListener!=null) {
                Log.v(TAG,"**************************************************ArrivalAdapter.Holder  onClick");
                favouriteListener.onItemClick(v,getAdapterPosition());
            }
        }

    }
}
