package com.pfc.android.archcomponent.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * LineModeGroup Entity used to recover the information of the Transport API
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
