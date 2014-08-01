package com.example.demouser.foodfriendly.map;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demouser.foodfriendly.FragmentCallback;
import com.example.demouser.foodfriendly.Restaurant;
import com.example.demouser.foodfriendly.RestaurantManager;
import com.example.demouser.foodfriendly.Utility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends SupportMapFragment
        implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String SUPPORT_MAP_BUNDLE_KEY = "MapOptions";
    private static final String LOG_TAG = "MAPFRG";
    private GoogleMap mMap;

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(LOG_TAG, "onInfoWindowClick..");
        MapController.getInstance().searchPlaceDetails(marker);
        if (mCallback != null) {
            String placeID = MapController.getInstance().getPlaceIDByMarker(marker);
            Log.d(LOG_TAG, "placeID " + placeID);
            Restaurant r = RestaurantManager.getInstance().getRestaurantByPlaceID(placeID);
            Log.d(LOG_TAG, "exist? " + (r!=null));
            if (r != null) {
                String placeName = r.getName();
                mCallback.OnItemClicked(Utility.MAP_FRAG, r);
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(LOG_TAG, "onMarkerClick");
        MapController.getInstance().searchPlaceDetails(marker);
        return false;
    }

    public static interface OnGoogleMapFragmentListener {
        void onMapReady(GoogleMap map);
        void onPlaceSelected(String placeID);
    }

    public static GoogleMapFragment newInstance() {
        return new GoogleMapFragment();
    }

    public static GoogleMapFragment newInstance(GoogleMapOptions options) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(SUPPORT_MAP_BUNDLE_KEY, options);

        GoogleMapFragment fragment = new GoogleMapFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (FragmentCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName() + " must implement OnGoogleMapFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mMap = getMap();
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);

        MapController.initialize(mMap, getActivity().getApplicationContext());

        return view;
    }

    public void showCurrentLocation () {
        MapController.getInstance().showCurrentLocation();
    }

    public void searchNearbyCurrentLocation () {
        MapController.getInstance().searchNearbyCurrentLocation();
    }

    public void searchPlaceDetails (String placeID) {
        MapController.getInstance().searchPlaceDetails(placeID);
    }

    private FragmentCallback mCallback;

}