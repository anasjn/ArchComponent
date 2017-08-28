package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

/**
 * Created by ana on 26/08/17.
 */

public class ArrivalsFormatedEntity {
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("lineId")
    @Expose
    private String lineId;
    @SerializedName("lineName")
    @Expose
    private String stopLetter;
    @SerializedName("stationName")
    @Expose
    private String stationName;
    @SerializedName("platformName")
    @Expose
    private String platformName;
    @SerializedName("destinationName")
    @Expose
    private String destinationName;
    @SerializedName("timeToStation")
    @Expose
    private List<Integer> timeToStation;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
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

    public ArrivalsFormatedEntity(String $type, String lineId, String stopLetter, String stationName, String platformName, String destinationName, List<Integer> timeToStation) {
        this.$type = $type;
        this.lineId = lineId;
        this.stopLetter = stopLetter;
        this.stationName = stationName;
        this.platformName = platformName;
        this.destinationName = destinationName;
        this.timeToStation = timeToStation;
    }
}
