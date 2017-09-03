package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * LineGroup Entity used to recover the information of the Transport API
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
public class LineGroup {
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("naptanIdReference")
    @Expose
    private String naptanIdReference;
    @SerializedName("stationAtcoCode")
    @Expose
    private String stationAtcoCode;
    @SerializedName("lineIdentifier")
    @Expose
    private List<String> lineIdentifier;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public List<String> getLineIdentifier() {
        return lineIdentifier;
    }

    public void setLineIdentifier(List<String> lineIdentifier) {
        this.lineIdentifier = lineIdentifier;
    }

    public String getStationAtcoCode() {
        return stationAtcoCode;
    }

    public void setStationAtcoCode(String stationAtcoCode) {
        this.stationAtcoCode = stationAtcoCode;
    }

    public String getNaptanIdReference() {
        return naptanIdReference;
    }

    public void setNaptanIdReference(String naptanIdReference) {
        this.naptanIdReference = naptanIdReference;
    }
}
