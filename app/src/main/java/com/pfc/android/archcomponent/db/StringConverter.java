package com.pfc.android.archcomponent.db;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StringConverter is a class in order to have a List<Integer> from a String and a String from a List<Integer>.
 * <p>
 *
 * @author      Ana San Juan
 * @version     1.0
 * @since       1.0
 */
public class StringConverter {

    /**
     * Method to convert from List<Integer> to String.
     * <p>
     *
     * @param  times  a List<Integer> giving split through ",".
     * @return      a String with all the information split through ",".
     */
    @TypeConverter
    public static String toString(List<Integer> times) {
        String time = "";
        if(times !=null) {
            time = times.get(0).toString();
            for (int i = 1; i < times.size(); i++) {
                time += "," + times.get(i);
            }
        }
        return time;
    }
    /**
     * Method to convert from String to List<Integer>.
     * <p>
     *
     * @param  time  a String giving split through ",".
     * @return      a List<Integer> with the same information than the String.
     */
    @TypeConverter
    public static List<Integer> toIntegerList(String time) {
        List<Integer> intlist = new ArrayList<>();
        if(time !=null){
            List<String> stringlist = toList(time);
            for(int i = 0 ; i< stringlist.size();i++){
                intlist.add(Integer.parseInt(stringlist.get(i)));
            }
        }
        return intlist;
    }
    /**
     * Method to convert from String to List<String>.
     * <p>
     *
     * @param  time  a String giving split through ",".
     * @return      a List<String> with the same information than the String.
     */
    public static List<String> toList(String time) {
        return time == null ? null : new ArrayList<String>(Arrays.asList(time.split(",")));
    }
}
