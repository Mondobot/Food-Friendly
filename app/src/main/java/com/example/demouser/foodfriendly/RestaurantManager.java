package com.example.demouser.foodfriendly;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.example.demouser.foodfriendly.FilterPref;
import com.example.demouser.foodfriendly.R;
import com.example.demouser.foodfriendly.Restaurant;
import com.example.demouser.foodfriendly.Review;
import com.example.demouser.foodfriendly.Utility;
import com.example.demouser.foodfriendly.Utility.FilterStatus;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;


/**
 * Created by demouser on 8/1/14.
 */
public class RestaurantManager {


    private static RestaurantManager singleton = new RestaurantManager();
    private RestaurantManager () {    }
    public static RestaurantManager getInstance( ) {
        return singleton;
    }

    private static HashMap<String,Restaurant> mAllRestaurants;
    private static HashSet<String> mLactoseFreeRestaurants;
    private static HashSet<String> mGlutenFreeRestaurants;
    private static HashSet<String> mVeganRestaurants;
    private static HashSet<String> mVegetarianRestaurants;

    private static Context mApplicationContext;
    private static Random mRandomGenerator;

    private static HashSet<BaseAdapter> mRegAdapters;

    public static void initialize(Context applicationContext) {
        mApplicationContext = applicationContext;
        mRandomGenerator = new Random();
        mAllRestaurants = new HashMap<String, Restaurant>();
        mLactoseFreeRestaurants = new HashSet<String>();
        mGlutenFreeRestaurants = new HashSet<String>();
        mVeganRestaurants = new HashSet<String>();
        mVegetarianRestaurants = new HashSet<String>();
        mRegAdapters = new HashSet<BaseAdapter>();
    }

    public static void registerAdapter(BaseAdapter toRegister) {
        mRegAdapters.add(toRegister);
    }

    public static void notifyAdapters() {
        for (BaseAdapter b : mRegAdapters)
            b.notifyDataSetChanged();
    }

    public void updateRestaurants(List<HashMap<String,String>> restaurants){
        for(HashMap<String,String> restaurantMap : restaurants){
            Log.d("RESTA", "Updating, rest: " + restaurantMap);
//            String id = restaurantMap.get(R.string.restaurant_place_id);
            String id = restaurantMap.get("place_id");
            Log.d("RESTA", "Updating, rest id: " + id);
            if(!mAllRestaurants.containsKey(id)){
                Restaurant newRestaurant = new Restaurant(restaurantMap);

                Log.d("RESTA", "It is new!!");

                mAllRestaurants.put(id, newRestaurant);

                if (mRandomGenerator.nextInt(100) % 2 == 0) {
                    mLactoseFreeRestaurants.add(id);
                }
                if (mRandomGenerator.nextInt(100) % 2 == 0) {
                    mGlutenFreeRestaurants.add(id);
                }
                if (mRandomGenerator.nextInt(100) % 2 == 0) {
                    mVeganRestaurants.add(id);
                }
                if (mRandomGenerator.nextInt(100) % 2 == 0) {
                    mVegetarianRestaurants.add(id);
                }
            }
        }

        notifyAdapters();
        Log.d("RESTA", "Updated: " + mAllRestaurants.toString());
    }

    public void addReviewToRestaurant(Restaurant restaurant, Review review){
        //TODO fix this logic to with filter NO...
        String id = restaurant.getPlaceId();

        if(review.getLactoseFree()!= FilterStatus.YES){
            mLactoseFreeRestaurants.add(id);
        }
        if(review.getLactoseFree()!= FilterStatus.NO){
            mLactoseFreeRestaurants.remove(id);
        }
        if(review.getGlutenFree()!= FilterStatus.YES){
            mGlutenFreeRestaurants.add(id);
        }
        if(review.getGlutenFree()!= FilterStatus.NO){
            mGlutenFreeRestaurants.remove(id);
        }
        if(review.getVegan()!= FilterStatus.YES){
            mVeganRestaurants.add(id);
        }
        if(review.getVegan()!= FilterStatus.NO){
            mVeganRestaurants.remove(id);
        }
        if(review.getVegetarian()!=FilterStatus.YES){
            mVegetarianRestaurants.add(id);
        }
        if(review.getVegetarian()!=FilterStatus.NO){
            mVegetarianRestaurants.remove(id);
        }
    }

    private ArrayList<Restaurant> getRelevantRestaurants(){
        ArrayList<Restaurant> resultSet = new ArrayList<Restaurant>();
        //TODO this logic!!!
        FilterPref filterPref = new FilterPref();
        if(filterPref.getState(Utility.LACTOSE_STATE,mApplicationContext)){
            for(String restaurantId : mLactoseFreeRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.GLUTEN_STATE,mApplicationContext)){
            for(String restaurantId : mGlutenFreeRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.VEGAN_STATE,mApplicationContext)){
            for(String restaurantId : mVeganRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.VEGETARIAN_STATE,mApplicationContext)){
            for(String restaurantId : mVegetarianRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        return resultSet;
    }

    public int getCount(){
        return getRelevantRestaurants().size();
    }

    public Restaurant getItemByPosition(int position){
        ArrayList<Restaurant> allRelevantRestaurants = getRelevantRestaurants();
        Collections.sort(allRelevantRestaurants,new RestaurantComparator());

        return allRelevantRestaurants.get(position);
    }

    public class RestaurantComparator implements Comparator<Restaurant> {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return new Float(o1.getDistance()).compareTo(o2.getDistance()); //TODO?// ???is this ok????
        }
    }


    public Restaurant getRestaurantByPlaceID (String placeID) {
        Restaurant restaurant;
        restaurant = mAllRestaurants.get(placeID);
        return restaurant;
    }

}
