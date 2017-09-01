package com.pfc.android.archcomponent.db;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dr3amsit on 29/07/17.
 */

public class StringConverter {

    @TypeConverter
    public static String toString(List<String> times) {
        String time = "";
        if(times !=null) {
            time = times.get(0);
            for (int i = 1; i < times.size(); i++) {
                time += "," + times.get(i);
            }
        }
        return time;
    }
    @TypeConverter
    public static List<String> toList(String time) {
        return time == null ? null : new ArrayList<String>(Arrays.asList(time.split(",")));
    }
}
