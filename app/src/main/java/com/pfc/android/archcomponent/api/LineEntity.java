package com.pfc.android.archcomponent.api;

/**
 * Created by dr3amsit on 29/07/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

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
