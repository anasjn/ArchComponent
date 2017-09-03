package com.pfc.android.archcomponent.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.lang.ref.WeakReference;

/**
 * PermissionsRequester
 * <p>
 * This class manage to check if the user has enough persmissions to use the location service.
 * <p>
 *
 * @author      Ana San Juan
 * @version     "%I%, %G%"
 * @since       1.0
 */
public class PermissionsRequester {
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private final WeakReference<Activity> activityWeakReference;

    public static PermissionsRequester newInstance(@NonNull Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        return new PermissionsRequester(activityWeakReference);
    }

    private PermissionsRequester(@NonNull WeakReference<Activity> activityWeakReference) {
        this.activityWeakReference = activityWeakReference;
    }

    public boolean hasPermissions() {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void requestPermissions() {
        Activity activity = activityWeakReference.get();
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, 0);
        }
    }

}
