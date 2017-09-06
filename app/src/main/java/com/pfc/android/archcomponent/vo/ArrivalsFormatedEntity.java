package com.pfc.android.archcomponent.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * ArrivalsFormatedEntity Entity used to define the structure of the database in SQLite
 * <p>
 * This is handle by only one entity:
 * <p>
 * ArrivalsFormatedEntity
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
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

    private String latitude;

    private String longitude;

    private String destinationName;

    private List<Integer> timeToStation;

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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public List<Integer> getTimeToStation() {
        return timeToStation;
    }

    public List<Integer> getTimeToStationSort() {
        Collections.sort(this.timeToStation);
        return timeToStation;
    }

    public void setTimeToStation(List<Integer> timeToStation) {
        this.timeToStation = timeToStation;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }


    public ArrivalsFormatedEntity(){}
}
