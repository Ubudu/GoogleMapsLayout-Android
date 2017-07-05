package com.ubudu.gmaps.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mgasztold on 16/05/2017.
 *
 * Class representing a path on Google Maps.
 */

public class Path {

    public static final String TAG = Path.class.getCanonicalName();

    private String uuid;
    private List<PolylineOptions> edges;
    private List<String> tags;

    public Path(String name){
        uuid = name;
        edges = new ArrayList<>();
    }

    public Path() {
        this(UUID.randomUUID().toString());
    }

    /**
     *
     * @param polylineOptions polylineOptions to add
     * @return true if polylineOptions has been added to the path, false if last point of the path does not match first point in the given polylineOptions
     */
    public boolean addPolyline(PolylineOptions polylineOptions) {
        if(getLastPoint()==null || polylineOptions.getPoints()!=null
                && polylineOptions.getPoints().size()>0
                && polylineOptions.getPoints().get(0).equals(getLastPoint())) {
            edges.add(polylineOptions);
            return true;
        }
        return false;
    }

    /**
     * Removes last polyline
     */
    public boolean removeLastPolyline() {
        if(edges.size()==0) return false;
        edges.remove(edges.size()-1);
        return true;
    }

    private LatLng getLastPoint(){
        if(edges.size()==0) return null;
        PolylineOptions lastPolylineOptions = edges.get(edges.size()-1);
        return lastPolylineOptions.getPoints().get(lastPolylineOptions.getPoints().size()-1);
    }

    public String getUuid() {
        return uuid;
    }

    public void setTags(List<String> tags) {
        this.tags = new ArrayList<>(tags);
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public boolean hasTag(String tag){
        return tags.contains(tag);
    }

    public void removeTag(String tag){
        tags.remove(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(!(other instanceof Path)) {
            return false;
        } else {
            Path var2 = (Path)other;
            return this.uuid.equals(var2.uuid);
        }
    }
}
