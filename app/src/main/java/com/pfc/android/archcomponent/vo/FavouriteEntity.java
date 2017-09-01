//package com.pfc.android.archcomponent.vo;
//
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
//
//import java.io.Serializable;
//import java.util.List;
//
//
///**
// * Created by dr3amsit on 29/07/17.
// */
//
//@Entity(tableName = "favourites")
//public class FavouriteEntity implements Serializable {
//
//    @PrimaryKey (autoGenerate = true)
//    public int mId;
//    public String mLineId;
//    public String mPlatformName;
//    public String mDestinationName;
//    public String mStationName;
//    public String mNaptanId;
//    public String mLat;
//    public String mLon;
//    public String mDirection;
//    public List<String> mTimeToStation;
//
//    public boolean mFavourite;
//
//    public String getmNaptanId() {
//        return mNaptanId;
//    }
//
//    public void setmNaptanId(String mNaptanId) {
//        this.mNaptanId = mNaptanId;
//    }
//
//    public int getmId() {
//        return mId;
//    }
//
//    public void setmId(int mId) {
//        this.mId = mId;
//    }
//
//    public String getmLineId() {
//        return mLineId;
//    }
//
//    public void setmLineId(String mLineId) {
//        this.mLineId = mLineId;
//    }
//
//    public String getmPlatformName() {
//        return mPlatformName;
//    }
//
//    public void setmPlatformName(String mPlatformName) {
//        this.mPlatformName = mPlatformName;
//    }
//
//    public String getmDestinationName() {
//        return mDestinationName;
//    }
//
//    public void setmDestinationName(String mDestinationName) {
//        this.mDestinationName = mDestinationName;
//    }
//
//    public String getmStationName() {
//        return mStationName;
//    }
//
//    public void setmStationName(String mStationName) {
//        this.mStationName = mStationName;
//    }
//
//    public String getmLat() {
//        return mLat;
//    }
//
//    public void setmLat(String mLat) {
//        this.mLat = mLat;
//    }
//
//    public String getmLon() {
//        return mLon;
//    }
//
//    public void setmLon(String mLon) {
//        this.mLon = mLon;
//    }
//
//    public String getmDirection() {
//        return mDirection;
//    }
//
//    public void setmDirection(String mDirection) {
//        this.mDirection = mDirection;
//    }
//
//    public List<String> getmTimeToStation() {
//        return mTimeToStation;
//    }
//
//    public void setmTimeToStation(List<String> timeToStation) {
//        this.mTimeToStation = timeToStation;
//    }
//
//    public boolean ismFavourite() {
//        return mFavourite;
//    }
//
//    public void setmFavourite(boolean mFavourite) {
//        this.mFavourite = mFavourite;
//    }
//
//    public FavouriteEntity() {
//
//    }
//
//    public FavouriteEntity(String line, String platform, String destination, String stationName, String naptanId, String lat, String lon, String direction, boolean favourite, List<String> timeToStation) {
//        this.mNaptanId = naptanId;
//        this.mLineId = line;
//        this.mPlatformName = platform;
//        this.mDestinationName = destination;
//        this.mStationName = stationName;
//
//        this.mLat = lat;
//        this.mLon = lon;
//        this.mDirection = direction;
//        this.mFavourite = favourite;
//        this.mTimeToStation = timeToStation;
//    }
//
////    public boolean isTransient() {
////        return getmId() == 0;
////    }
//}