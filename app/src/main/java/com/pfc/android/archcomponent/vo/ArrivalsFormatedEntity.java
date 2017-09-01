package com.pfc.android.archcomponent.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by ana on 26/08/17.
 */

@Entity(tableName = "favourites")
public class ArrivalsFormatedEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int mId;

    private String naptanId;

    private String lineId;

    private String stopLetter;

    private String stationName;

    private String platformName;

    private String direction;

    public String mLat;

    public String mLon;

    private String destinationName;

    private List<String> timeToStation;

    private boolean favourite;


    public String getNaptanId() {
        return naptanId;
    }

    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }
    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getStopLetter() {
        return stopLetter;
    }

    public void setStopLetter(String stopLetter) {
        this.stopLetter = stopLetter;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLon() {
        return mLon;
    }

    public void setmLon(String mLon) {
        this.mLon = mLon;
    }


    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public List<String> getTimeToStation() {
        return timeToStation;
    }

    public List<String> getTimeToStationSort() {
        Collections.sort(this.timeToStation);
        return timeToStation;
    }

    public void setTimeToStation(List<String> timeToStation) {
        this.timeToStation = timeToStation;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public ArrivalsFormatedEntity( String naptanId, String lineId, String stopLetter, String stationName, String platformName, String destinationName, String direction, boolean favourite, List<String> timeToStation) {
        this.naptanId= naptanId;
        this.lineId = lineId;
        this.stopLetter = stopLetter;
        this.stationName = stationName;
        this.platformName = platformName;
        this.destinationName = destinationName;
        this.timeToStation = timeToStation;
        this.direction = direction;
        //isfav?
        this.favourite = favourite;
    }

}
