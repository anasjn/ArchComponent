package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * StopPointsEntity Entity used to recover the information of the Transport API
 * <p>
 * This object is part of the structure retrieve by this kind of call:
 * https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=xxxxx&lon=xxxxx&radius=xxx
 * <p>
 * The relationship between the entities is:
 * <p>
 * <ul>
 *    <li>StopLocationEntity
 *       <ul>
 *         <li> List of StopPointsEntity
 *              <ul>
 *                <li>List of LineEntity
 *                <li>List of LineGroup
 *                <li>List of LineModeGroup
 *                <li>List of AdditionalPropertiesEntity
 *              </ul>
 *          </li>
 *      </ul>
 *   </li>
 * </ul>
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */

public class StopPointsEntity {
    private final String TAG = StopPointsEntity.class.getName();
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("naptanId")
    @Expose
    private String naptanId;
    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("stopLetter")
    @Expose
    private String stopLetter;
    @SerializedName("modes")
    @Expose
    private List<String> modes;
    @SerializedName("icsCode")
    @Expose
    private String icsCode;
    @SerializedName("stopType")
    @Expose
    private String stopType;
    @SerializedName("stationNaptan")
    @Expose
    private String stationNaptan;
    @SerializedName("hubNaptanCode")
    @Expose
    private String hubNaptanCode;
    @SerializedName("lines")
    @Expose
    private List<LineEntity> listlines;
    @SerializedName("lineGroup")
    @Expose
    private List<LineGroup> lineGroup;
    @SerializedName("lineModeGroups")
    @Expose
    private List<LineModeGroup> lineModeGroups;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("commonName")
    @Expose
    private String commonName;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("placeType")
    @Expose
    private String placeType;
    @SerializedName("additionalProperties")
    @Expose
    private List<AdditionalPropertiesEntity> addproperties;;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public String get$type() { return $type; }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public List<String> getModes() { return modes; }

    public void setModes(List<String> modes) {
        this.modes = modes;
    }

    public String getIcsCode() {
        return icsCode;
    }

    public void setIcsCode(String icsCode) {
        this.icsCode = icsCode;
    }

    public String getStopType() {
        return stopType;
    }

    public void setStopType(String stopType) {
        this.stopType = stopType;
    }

    public String getStationNaptan() {
        return stationNaptan;
    }

    public void setStationNaptan(String stationNaptan) {
        this.stationNaptan = stationNaptan;
    }

    public String getHubNaptanCode() {
        return hubNaptanCode;
    }

    public void setHubNaptanCode(String hubNaptanCode) {
        this.hubNaptanCode = hubNaptanCode;
    }

    public List<LineGroup> getLineGroup() { return lineGroup; }

    public void setLineGroup(List<LineGroup> lineGroup) {
        this.lineGroup = lineGroup;
    }

    public List<LineModeGroup> getLineModeGroups() { return lineModeGroups; }

    public void setLineModeGroups(List<LineModeGroup> lineModeGroups) {
        this.lineModeGroups = lineModeGroups;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getNaptanId() {
        return naptanId;
    }

    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getStopLetter() {
        return stopLetter;
    }

    public void setStopLetter(String stopLetter) {
        this.stopLetter = stopLetter;
    }

    public List<LineEntity> getListlines() { return listlines; }

    public void setListlines(List<LineEntity> listlines) {
        this.listlines = listlines;
    }

    public List<AdditionalPropertiesEntity> getAddproperties() { return addproperties; }

    public void setLines(List<AdditionalPropertiesEntity> addproperties) {
        this.addproperties = addproperties;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }
}
