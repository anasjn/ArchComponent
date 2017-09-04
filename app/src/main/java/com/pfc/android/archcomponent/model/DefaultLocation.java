package com.pfc.android.archcomponent.model;

/**
 * DefaultLocation Object that defines a location as an object with a longitude, a latitude and a name.
 * <p>
 * This name allows to mark this location in the map.
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public class DefaultLocation {
    private final double longitude;
    private final double latitude;
    private final String name;

    /**
     * Constructor with a three parameters: latitude, longitude and name.
     * <p>
     * @param latitude double
     * @param longitude double
     * @param name String
     *
     */
    public DefaultLocation(double latitude, double longitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }
    /**
     * Longitude getter
     * <p>
     * @return  the longitude of the DefaultLocation object
     *
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * Latitude getter
     * <p>
     * @return  the latitude of the DefaultLocation object
     *
     */
    public double getLatitude() {
        return latitude;
    }
    /**
     * Name getter
     * <p>
     * @return  the name of the DefaultLocation object
     *
     */
    public String getName() {
        return name;
    }

}
