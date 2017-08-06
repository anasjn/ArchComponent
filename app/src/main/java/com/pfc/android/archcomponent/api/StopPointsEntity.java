package com.pfc.android.archcomponent.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class StopPointsEntity {
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
    private List<AdditionalPropertiesEntity> addproperties;
    @SerializedName("children")
    @Expose
    private List<String> children;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public List<String> getModes() {
        return modes;
    }

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

    public List<LineGroup> getLineGroup() {
        return lineGroup;
    }

    public void setLineGroup(List<LineGroup> lineGroup) {
        this.lineGroup = lineGroup;
    }

    public List<LineModeGroup> getLineModeGroups() {
        return lineModeGroups;
    }

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

    public void setAddproperties(List<AdditionalPropertiesEntity> addproperties) {
        this.addproperties = addproperties;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
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

    public List<LineEntity> getListlines() {
        return listlines;
    }

    public void setListlines(List<LineEntity> listlines) {
        this.listlines = listlines;
    }

    public List<AdditionalPropertiesEntity> getAddproperties() {
        return addproperties;
    }

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
