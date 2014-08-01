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
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by greenvirag on 7/31/14.
 */
public class MapController {

    private Context mApplicationContext;
    private GoogleMap mMap;
    private Location mLocation;

    public MapController(GoogleMap map, Context applicationContext) {
        mMap = map;
        mApplicationContext = applicationContext;

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) mApplicationContext.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                mLocation = location;
                Log.d("MAPCTRL", "New location: " + mLocation.toString());
                Log.d("MAPCTRL", "Map: " + mMap.toString());

                mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()))
                        .title("Hello world"));

                new MapControllerUpdaterTask().execute(convertLocationToString(mLocation));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

//        new MapControllerUpdaterTask().execute(convertLocationToString(mLocation));
    }

    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_SEARCH = "/search";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyB7iG7Ltiwy-jBVqkJWU22LVFfADLwUKqg";

    private static final String TYPE_RANKBY = "&rankby=distance";
    private static final String TYPE_TYPES = "&types=food|restaurant|cafe";
    private static final String TYPE_LOCATION = "&location=";
    private static final String TYPE_RADIUS = "&radius=1000";


    private String convertLocationToString (Location location) {

        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        StringBuilder result = new StringBuilder(latitude);
        result.append(",");
        result.append(longitude);
        return result.toString();
    }


    private class MapControllerUpdaterTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Log.i("MAPCTRL", "Starting communication..");
            String locationData = params[0];
            try {
                StringBuilder requestBuilder = new StringBuilder(TYPE_LOCATION);
                requestBuilder.append(locationData);
                String request = createRequestString(TYPE_SEARCH, requestBuilder.toString());
                String jsonResult = getJSONFromServer(request);


            } catch (NullPointerException e) {
            }
            Log.i("MAPCTRL", "Finished communication.");
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("MAPCTRL", "Postexecute.");

            super.onPostExecute(aVoid);
        }

        private String createRequestString (String type, String parameters) {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + type + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append(parameters);
            sb.append("&sensor=false");
            return sb.toString();
        }

        private String getJSONFromServer(String request) {
            String result = null;

            HttpsURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();

            try {
                URL url = new URL(request.toString());
                conn = (HttpsURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Load the results into a StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error processing Places API URL", e);
                return result;
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error connecting to Places API", e);
                return result;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            result = jsonResults.toString();
            Log.d("RESULT", result);

            return result;
        }

        private ArrayList<String> processJSON(String input) {
            ArrayList<String> resultList = null;

            try {
                // Create a JSON object hierarchy from the results
                JSONObject jsonObj = new JSONObject(input.toString());
                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                // Extract the Place descriptions from the results
                resultList = new ArrayList<String>(predsJsonArray.length());
                for (int i = 0; i < predsJsonArray.length(); i++) {
                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Cannot process JSON results", e);
            }

            return resultList;
        }

    }

}
