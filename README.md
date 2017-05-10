## Ubudu Google Map Layout

Android library module that provides Google Map layout component with an API designed for handling Indoor Location use cases. API covers things like:

- position marker placing + style (color, size, halo color and radius)
- custom marker placing with tags for clean maintanance + style (color or bitmap, size)
- zones highlighing + style(fill color, stroke color)
- camera control (zoom, rotation, animate/not animate)

<p align="center"><img src="screenshot.png" width="300px"></p>

## How to use

### Installing

Add Ubudu nexus repository url to your `build.gradle` file:

	repositories {
		maven { url 'http://nexus.ubudu.com:8081/nexus/content/groups/public/' }
		// ...
	}
    
Then add the following dependency:

    dependencies {
        compile('com.ubudu.gmaps:maplayout:1.0.0@aar')
        // ...
    }

### Integration

**Important!** MapLayout uses the Google Maps Android API. It is mandatory to register the app project on the Google API Console and get a Google API key which can be then added to the app. More information of how to achieve this can be found [here](https://developers.google.com/maps/documentation/android-api/signup).

Google Maps API key must be specified in the mobile app's manifest as a `meta-data`:

	<meta-data
		android:name="com.google.android.maps.v2.API_KEY"
		android:value="AIzaSyC30GXjc8Mep_tPYglfzA0UXBM5UYdUmmU" />

The UI component to be placed in the layout xml file:

	<com.ubudu.gmaps.MapLayout
        android:id="@+id/map"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
   
To initialize the layout object in the mobile application's code call the following in your fragment's `onViewCreated` or in your `onCreate` of your activity:

	mMapLayout.init(getContext());

To handle events coming from the map layout a listener interface has to be implemented:

	mMapLayout.setEventListener(new MapLayout.EventListener() {
	
		@Override
		public void onMapReady() {
			// called when map layout is ready to handle API calls
		}
		
		@Override
		public void onZoneClicked(Zone zone, Polygon polygon) {
			Log.i(TAG,"Polygon clicked: "+zone.getName()+", polygon id: "+polygon.getId());
		}
		
		@Override
		public void onMarkerClicked(com.ubudu.gmaps.model.Marker marker, Marker googleMarker) {
			Log.i(TAG, "Marker clicked: " + marker.getTitle() + ", tag: " + marker.getTags());
		}
		
	});
        
Sample API calls:

        // zone example
        ArrayList<LatLng> sampleZoneCoordinatesList = new ArrayList<>();
        sampleZoneCoordinatesList.add(new LatLng(52.200766, 21.024592));
        sampleZoneCoordinatesList.add(new LatLng(52.200872, 21.026395));
        sampleZoneCoordinatesList.add(new LatLng(52.201608, 21.027189));
        sampleZoneCoordinatesList.add(new LatLng(52.202134, 21.026052));
        sampleZoneCoordinatesList.add(new LatLng(52.201450, 21.024185));
        // normal and highlighted map layout's zone options will be used for appearance
        mMapLayout.addZone("sample zone 1",sampleZoneCoordinatesList);

.

        // sample custom marker
        mMapLayout.addMarker("test_markers", new LatLng(52.200682, 21.024481)
                , "test marker 2");

.

        // setup location marker options
        mMapLayout.setLocationMarkerOptionsStrategy(new MarkerOptionsStrategy()
                .setNormalMarkerOptions(MarkerOptionsFactory
                        .circleWithHaloMarkerOptions(15, "#4285F4", 100, "#304235F4")));

.

        // mark location
        mMapLayout.markLocation(new LatLng(52.201214, 21.025923));

.

        // update the camera
        mMapLayout.updateCamera(true);