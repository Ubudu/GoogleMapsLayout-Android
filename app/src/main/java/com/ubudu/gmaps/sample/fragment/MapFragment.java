package com.ubudu.gmaps.sample.fragment;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polygon;
import com.ubudu.gmaps.MapLayout;
import com.ubudu.gmaps.factory.MarkerOptionsFactory;
import com.ubudu.gmaps.factory.MarkerOptionsStrategyFactory;
import com.ubudu.gmaps.factory.PolylineOptionsFactory;
import com.ubudu.gmaps.factory.ZoneOptionsFactory;
import com.ubudu.gmaps.model.Zone;
import com.ubudu.gmaps.sample.R;
import com.ubudu.gmaps.util.MarkerOptionsStrategy;
import com.ubudu.gmaps.util.MarkerSearchPattern;
import com.ubudu.gmaps.util.ZoneLabelOptions;
import com.ubudu.gmaps.util.ZoneOptions;
import com.ubudu.gmaps.util.ZoneOptionsStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mgasztold on 09/01/2017.
 */

public class MapFragment extends BaseFragment implements MapLayout.EventListener {

    public static final String TAG = MapFragment.class.getCanonicalName();

    private DrawerLayout mRootView;

    private int bearing = 0;
    private double accuracy = 0;

    @BindView(R.id.map)
    MapLayout mMapLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = (DrawerLayout) inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapLayout.init(getContext());
        mMapLayout.setEventListener(this);
    }

    @Override
    public void onMapReady() {

        // zones example
        ArrayList<LatLng> sampleZoneCoordinatesList = new ArrayList<>();
        sampleZoneCoordinatesList.add(new LatLng(52.200766, 21.024592));
        sampleZoneCoordinatesList.add(new LatLng(52.200872, 21.026395));
        sampleZoneCoordinatesList.add(new LatLng(52.201608, 21.027189));
        sampleZoneCoordinatesList.add(new LatLng(52.202134, 21.026052));
        sampleZoneCoordinatesList.add(new LatLng(52.201450, 21.024185));
        // normal and highlighted map layout's zone options will be used for appearance
        mMapLayout.addZone("sample zone 1", sampleZoneCoordinatesList);

        sampleZoneCoordinatesList = new ArrayList<>();
        sampleZoneCoordinatesList.add(new LatLng(52.200345, 21.025631));
        sampleZoneCoordinatesList.add(new LatLng(52.200702, 21.026725));
        sampleZoneCoordinatesList.add(new LatLng(52.201248, 21.026210));
        Zone zone = new Zone("sample zone 2", sampleZoneCoordinatesList);
        zone.setOptionsStrategy(new ZoneOptionsStrategy()
                .normalZoneOptions(new ZoneOptions()
                        .zoneLabelOptions(new ZoneLabelOptions()
                                .labelColor(Color.YELLOW)))
                .highlightedZoneOptions(ZoneOptionsFactory.defaultHighlightedZoneOptions()));
        // normal and highlighted zone's object specific options will be used for appearance
        mMapLayout.addZone(zone);

        // setup custom markers default options
        mMapLayout.setMarkerOptionsStrategy(new MarkerOptionsStrategy()
                .setNormalMarkerOptions(MarkerOptionsFactory
                        .bitmapMarkerOptions(BitmapFactory.decodeResource(getResources(), R.drawable.location)))
                .setInforWindowEnabled(false));

        // sample custom marker 1
        mMapLayout.addMarker("test_markers", new LatLng(52.200937, 21.024743)
                , "test marker 1"
                , new MarkerOptionsStrategy()
                        .setNormalMarkerOptions(MarkerOptionsFactory
                                .bitmapWithHaloMarkerOptions(BitmapFactory.decodeResource(getResources(), R.drawable.ic_warning_black_24dp)
                                        , 70
                                        , "#301235F4"))
                        .setHighlightedMarkerOptions(MarkerOptionsFactory.defaultMarkerOptions()));

        // sample custom marker 2
        mMapLayout.addMarker("test_markers", new LatLng(52.202682, 21.021481)
                , "test marker 2", MarkerOptionsStrategyFactory.defaultMarkerOptionsStrategy()
                        .setNormalMarkerOptions(MarkerOptionsFactory.circleMarkerOptions().snippet("test snipper 2")));

        // sample custom marker 2
        mMapLayout.addMarker("test_markers", new LatLng(52.206682, 21.023481)
                , "test marker 3", MarkerOptionsStrategyFactory.defaultMarkerOptionsStrategy()
                        .setNormalMarkerOptions(MarkerOptionsFactory.circleMarkerOptions().snippet("test snipper 3")));

        // sample custom marker 2
        mMapLayout.addMarker("test_markers", new LatLng(52.203682, 21.029481)
                , "test marker 4");

        List<String> tags = new ArrayList<>();
        tags.add("test_markers12");
//        tags.add("asd");
        Map<com.ubudu.gmaps.model.Marker, Marker> markers = mMapLayout.findMarkers(new MarkerSearchPattern().title("test marker 1"));
        Log.e(TAG, "searching for markers");
        for (Marker marker : markers.values()) {
            Log.i(TAG, "Found marker: " + marker.getId() + ", title: " + marker.getTitle());
        }

        Log.i(TAG, "removed markers count: " + mMapLayout.removeMarkers(new MarkerSearchPattern().tag("test_markerss").title("test marker 2")));


        // setup location marker options
        mMapLayout.setLocationMarkerOptionsStrategy(new MarkerOptionsStrategy()
                .setNormalMarkerOptions(MarkerOptionsFactory
                        .bitmapMarkerOptions(BitmapFactory.decodeResource(getResources(), R.drawable.ic_map_location_marker)).anchor(0.1f, 0.1f)));

        // mark location
        mMapLayout.updateLocationBearing(45);
        mMapLayout.markLocation(new LatLng(52.201214, 21.025923), 3);

        // update the camera
        mMapLayout.updateCamera(true);

        mMapLayout.getMap().setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.i(TAG, "map clicked at: " + latLng.latitude + "," + latLng.longitude);
            }
        });

        // sample path operations
        final LatLng latLng1 = new LatLng(52.202682, 21.021481);
        final LatLng latLng2 = new LatLng(52.206682, 21.023481);
        final LatLng latLng3 = new LatLng(52.203682, 21.029481);

        List<LatLng> points = new ArrayList<>();
        points.add(latLng1);
        points.add(latLng2);
        points.add(latLng3);

        mMapLayout.addPolylineToPath("test_path", PolylineOptionsFactory.polylineWithColor(getResources().getColor(R.color.colorAccent)).addAll(points));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                LatLng latLng4 = new LatLng(52.201214, 21.025923);
                mMapLayout.addPolylineToPath("test_path", PolylineOptionsFactory.polylineWithColor(getResources().getColor(R.color.darkTextColor)).add(latLng3).add(latLng4));
            }
        }, 5000L);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapLayout.removePath("test_path");
            }
        }, 10000L);
    }

    @Override
    public void onZoneClicked(Zone zone, Polygon polygon) {
        Log.i(TAG, "Polygon clicked: " + zone.getName() + ", polygon id: " + polygon.getId());
    }

    @Override
    public void onMarkerClicked(com.ubudu.gmaps.model.Marker marker, Marker googleMarker) {
        Log.i(TAG, "Marker clicked: " + marker.getTitle() + ", tag: " + marker.getTags());
    }
}