package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dr3amsit on 30/07/17.
 */

public class LineModeGroup {
    @SerializedName("$type")
    @Expose
    private String $type;
    @SerializedName("modeName")
    @Expose
    private String modeName;

    public List<String> getLineIdentifier() {
        return lineIdentifier;
    }

    public void setLineIdentifier(List<String> lineIdentifier) {
        this.lineIdentifier = lineIdentifier;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String get$type() {
        return $type;
    }

    public void set$type(String $type) {
        this.$type = $type;
    }

    @SerializedName("lineIdentifier")
    @Expose
    private List<String> lineIdentifier;
}
