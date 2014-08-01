package com.example.demouser.foodfriendly.map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by greenvirag on 7/31/14.
 */
public class MapController {

    private static MapController singleton = new MapController( );

    private MapController() {}

    public static MapController getInstance( ) {
        return singleton;
    }

    private static final String LOG_TAG = "MAPCTRL";

    private static Context mApplicationContext;
    private static GoogleMap mMap;
    private static Location mLocation;
    private static HashMap<Marker, String> mMarkerMap;

    public static void initialize(GoogleMap map, Context applicationContext) {
        mMap = map;
        mApplicationContext = applicationContext;
        mMarkerMap = new HashMap<Marker, String>();

        registerLocationListener();
    }

    private static void registerLocationListener () {
        Log.d(LOG_TAG, "register location listener");
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) mApplicationContext.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                if (location != null) {
                    mLocation = location;
                    Log.d(LOG_TAG, "New location: " + mLocation.toString());
                    Log.d(LOG_TAG, "Map: " + mMap.toString());
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    public void showCurrentLocation () {
        if (mLocation != null ) {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                    .title("My Location"));
        }
    }

    public void searchNearbyCurrentLocation () {
        if (mLocation == null) return;
        MapCommunicator mapCommunicator = new MapCommunicator(mMap);
        mapCommunicator.searchNearby(mLocation);
    }

    public void searchPlaceDetails (String placeID) {
        if (mLocation == null) return;
        MapCommunicator mapCommunicator = new MapCommunicator(mMap);
        mapCommunicator.searchPlaceDetails(placeID);
    }

    public void searchPlaceDetails (Marker marker) {
        String placeID = mMarkerMap.get(marker);
        MapCommunicator mapCommunicator = new MapCommunicator(mMap);
        mapCommunicator.searchPlaceDetails(placeID);
    }

    public void showLocation (String address) {

    }

    public void registerMarker (Marker marker, String placeID) {
        mMarkerMap.put(marker, placeID);
    }

    public String getPlaceIDByMarker (Marker marker) {
        return mMarkerMap.get(marker);
    }
}
