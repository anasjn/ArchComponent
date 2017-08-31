package com.pfc.android.archcomponent.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by dr3amsit on 29/07/17.
 */

@Entity(tableName = "favourites")
public class FavouriteEntity implements Serializable {

    @PrimaryKey (autoGenerate = true)
    public int mId;
    public Date mTime;
    public String mLineId;
    public String mPlatformName;
    public String mDestinationName;
    public String mNaptanId;
    public boolean mFavourite;

    public String getmNaptanId() {
        return mNaptanId;
    }

    public void setmNaptanId(String mNaptanId) {
        this.mNaptanId = mNaptanId;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getmLineId() {
        return mLineId;
    }

    public void setmLineId(String mLineId) {
        this.mLineId = mLineId;
    }

    public String getmPlatformName() {
        return mPlatformName;
    }

    public void setmPlatformName(String mPlatformName) {
        this.mPlatformName = mPlatformName;
    }

    public String getmDestinationName() {
        return mDestinationName;
    }

    public void setmDestinationName(String mDestinationName) {
        this.mDestinationName = mDestinationName;
    }

    public boolean ismFavourite() {
        return mFavourite;
    }

    public void setmFavourite(boolean mFavourite) {
        this.mFavourite = mFavourite;
    }

    public FavouriteEntity() {

    }

    public FavouriteEntity(Date time, String line, String platform, String destination, String naptanId, boolean favourite) {
        this.mTime = time;
        this.mLineId = line;
        this.mPlatformName = platform;
        this.mDestinationName = destination;
        this.mNaptanId = naptanId;
        this.mFavourite = favourite;
    }

//    public boolean isTransient() {
//        return getmId() == 0;
//    }
}