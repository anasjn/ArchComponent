package com.pfc.android.archcomponent.model;

/**
 * LocationListener Listener interface to generate the markers of the location, the locaton of the user and the favourites.
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public interface LocationListener{
    void updateLocation(DefaultLocation defaultLocation,boolean mylocation);
}
