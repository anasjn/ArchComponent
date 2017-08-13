package com.pfc.android.archcomponent.api;

/**
 * Created by dr3amsit on 29/07/17.
 */

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pfc.android.archcomponent.ui.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class StopLocationEntity {

    private final String TAG = StopLocationEntity.class.getName();

    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("centrePoint")
    @Expose
    private List<Double> centrePoint;
    @SerializedName("stopPoints")
    @Expose
    private List<StopPointsEntity> stopPoints;
    @SerializedName("pageSize")
    @Expose
    private int pageSize;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("page")
    @Expose
    private int page;

    public String get$type() { return $type; }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public List<Double> getCentrePoint() {
        return centrePoint;
    }

    public void setCentrePoint(List<Double> centrePoint) {
        this.centrePoint = centrePoint;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<StopPointsEntity> getStopPoints() {
        return stopPoints;
    }

    public void setStopPoints(List<StopPointsEntity> stopPoints) { this.stopPoints = stopPoints; }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
