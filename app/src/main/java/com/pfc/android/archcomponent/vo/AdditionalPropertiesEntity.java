package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * AdditionalPropertiesEntity Entity used to recover the information of the Transport API
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
public class AdditionalPropertiesEntity {
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("sourceSystemKey")
    @Expose
    private String sourceSystemKey;
    @SerializedName("value")
    @Expose
    private String value;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public String getSourceSystemKey() {
        return sourceSystemKey;
    }

    public void setSourceSystemKey(String sourceSystemKey) {
        this.sourceSystemKey = sourceSystemKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
