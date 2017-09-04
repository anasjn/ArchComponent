package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * ArrivalsEntity Entity used to recover the information of the Transport API
 * <p>
 * This object is part of the structure retrieve by this kind of call:
 * https://api.tfl.gov.uk/StopPoint/{id}/Arrivals?app_id=xxx&app_key=xxxx
 * <p>
 * This is handle by only one entity:
 * <p>
 * ArrivalsEntity
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class ArrivalsEntity {

    private final String TAG = ArrivalsEntity.class.getName();

    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("naptanId")
    @Expose
    private String naptanId;
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
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("destinationName")
    @Expose
    private String destinationName;
    @SerializedName("timeToStation")
    @Expose
    private int timeToStation;
    @SerializedName("expectedArrival")
    @Expose
    private String expectedArrival;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

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

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public int getTimeToStation() {
        return timeToStation;
    }

    public void setTimeToStation(int timeToStation) {
        this.timeToStation = timeToStation;
    }

    public String getExpectedArrival() {
        return expectedArrival;
    }

    public void setExpectedArrival(String expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

}
