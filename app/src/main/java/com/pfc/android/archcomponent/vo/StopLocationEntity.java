package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * StopLocationEntity Entity used to recover the information of the Transport API
 * <p>
 * This object is part of the structure retrieve by this kind of call:
 * https://api.tfl.gov.uk/Stoppoint?stoptypes=NaptanRailStation,NaptanBusCoachStation,NaptanFerryPort,NaptanPublicBusCoachTram&lat=xxxxx&lon=xxxxx&radius=xxx
 * <p>
 * The relationship between the entities is:
 * <p>
 * <StopLocationEntity>
 *    List<StopPointsEntity>
 *      List<LineEntity>
 *      List<LineGroup>
 *      List<LineModeGroup>
 *      List<AdditionalPropertiesEntity>
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
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
