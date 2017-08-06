package com.pfc.android.archcomponent.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dr3amsit on 30/07/17.
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
