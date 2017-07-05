package com.ubudu.gmaps.factory;

import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by mgasztold on 16/05/2017.
 */

public class PolylineOptionsFactory {

    public static PolylineOptions defaultPolylineOptions(){
        return new PolylineOptions().width(4f);
    }

    public static PolylineOptions polylineWithColor(int color) {
        return defaultPolylineOptions().color(color);
    }

}
