package com.example.demouser.foodfriendly.map;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.demouser.foodfriendly.RestaurantManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by greenvirag on 8/1/14.
 */
public class MapCommunicator {
    private static final String LOG_TAG = "MAPCOM";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_SEARCH = "/search";
    private static final String TYPE_DETAILS = "/details";
    private static final String OUT_JSON = "/json";

    private static final String API_KEY = "AIzaSyB7iG7Ltiwy-jBVqkJWU22LVFfADLwUKqg";

    private static final String TYPE_KEY = "?key=";
    private static final String TYPE_PLACEID = "&placeid=";
    private static final String TYPE_RANKBY = "&rankby=distance";
    private static final String TYPE_TYPES = "&types=food";
    private static final String TYPE_LOCATION = "&location=";
    private static final String TYPE_RADIUS = "&radius=1000";
    private static final String TYPE_SENSOR = "&sensor=false";

    private final int COMTYPE_SEARCH = 0;
    private final int COMTYPE_DETAILS = 1;

    private GoogleMap mMap;

    public MapCommunicator (GoogleMap map) {
        mMap = map;
    }

    public void searchNearby(Location location) {
        String locationString = convertLocationToString(location);
        String request = createRequestStringPlaces(TYPE_SEARCH, locationString);

        new RemoteCommunicatorTask(mMap, COMTYPE_SEARCH).execute(request);
    }

    public void searchPlaceDetails(String placeID) {
        String request = createRequestStringPlaceDetails(TYPE_DETAILS, placeID);

        new RemoteCommunicatorTask(mMap, COMTYPE_DETAILS).execute(request);
    }

    private String convertLocationToString (Location location) {

        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());

        StringBuilder result = new StringBuilder(latitude);
        result.append(",");
        result.append(longitude);
        return result.toString();
    }

    private String createRequestStringPlaces (String type, String location) {
        StringBuilder sb = new StringBuilder(PLACES_API_BASE + type + OUT_JSON );
        sb.append(TYPE_KEY);
        sb.append(API_KEY);
        sb.append(TYPE_RANKBY);
        sb.append(TYPE_TYPES);
//            sb.append(TYPE_RADIUS);
        sb.append(TYPE_SENSOR);
        sb.append(TYPE_LOCATION);
        sb.append(location);
        return sb.toString();
    }

    private String createRequestStringPlaceDetails (String type, String placeID) {
        StringBuilder sb = new StringBuilder(PLACES_API_BASE + type + OUT_JSON );
        sb.append(TYPE_KEY);
        sb.append(API_KEY);
        sb.append(TYPE_PLACEID);
        sb.append(placeID);
        return sb.toString();
    }


    private class RemoteCommunicatorTask extends AsyncTask<String, Void, String> {

        private GoogleMap mMap;
        private int mType;

        public RemoteCommunicatorTask (GoogleMap map, int type) {
            mMap = map;
            mType = type;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.i(LOG_TAG, "Starting communication..");
            String request = params[0];
            String jsonResult = "";
            try {
                Log.d(LOG_TAG, "Sending: " + request);
                jsonResult = getJSONFromServer(request);


            } catch (NullPointerException e) {
            }
            Log.i(LOG_TAG, "Finished communication.");
            return jsonResult;
        }


        @Override
        protected void onPostExecute(String input) {
            Log.i(LOG_TAG, "Postexecute running.");

            if (mType == COMTYPE_SEARCH) {
                try {
                    JSONObject jsonObj = new JSONObject(input);
                    List<HashMap<String, String>> places = new Place_JSONParser().parse(Place_JSONParser.parseForResult, jsonObj);
                    Log.d(LOG_TAG, "Places: " + places.toString());

                    LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
                    for (HashMap<String, String> p : places) {

                        LatLng latlng = new LatLng(Double.parseDouble(p.get("lat")), Double.parseDouble(p.get("lng")));
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                        .position(latlng)
                                        .title(p.get("place_name"))
                        );

                        String placeId = p.get("place_id");
                        MapController.getInstance().registerMarker(marker, placeId);
                        boundsBuilder.include(latlng);
                    }

                    LatLngBounds bounds = boundsBuilder.build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 10);
                    mMap.moveCamera(cameraUpdate);

                    Log.d(LOG_TAG, "Places: "+ places.toString());

                    RestaurantManager.getInstance().updateRestaurants(places);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (mType == COMTYPE_DETAILS) {
                try {
                    JSONObject jsonObj = new JSONObject(input);
                    HashMap<String, String> place = new PlaceDetails_JSONParser().parse(jsonObj);
                    Log.d(LOG_TAG, "Place: " + place.toString());

                    LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();

                    LatLng latlng = new LatLng(Double.parseDouble(place.get("lat")), Double.parseDouble(place.get("lng")));
                    Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(latlng)
                                    .title(place.get("place_name"))
                    );

                    String placeId = place.get("place_id");
                    MapController.getInstance().registerMarker(marker, placeId);
                    boundsBuilder.include(latlng);

                    LatLngBounds bounds = boundsBuilder.build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 10);
                    mMap.moveCamera(cameraUpdate);

                    Log.d(LOG_TAG, "Place: "+ place.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Log.i(LOG_TAG, "Postexecute done.");
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
            Log.d(LOG_TAG, result);

            return result;
        }

//        private ArrayList<String> processJSON(String input) {
//            ArrayList<String> resultList = null;
//
//            try {
//                // Create a JSON object hierarchy from the results
//                JSONObject jsonObj = new JSONObject(input.toString());
//                JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");
//
//                // Extract the Place descriptions from the results
//                resultList = new ArrayList<String>(predsJsonArray.length());
//                for (int i = 0; i < predsJsonArray.length(); i++) {
//                    resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
//                }
//            } catch (JSONException e) {
//                Log.e(LOG_TAG, "Cannot process JSON results", e);
//            }
//
//            return resultList;
//        }

    }

}
