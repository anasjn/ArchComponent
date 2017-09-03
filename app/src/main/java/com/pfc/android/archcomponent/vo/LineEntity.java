package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * LineEntity Entity used to recover the information of the Transport API
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
public class LineEntity {
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("crowding")
    @Expose
    private JSONObject crowding;

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getCrowding() {
        return crowding;
    }

    public void setCrowding(JSONObject crowding) {
        this.crowding = crowding;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
