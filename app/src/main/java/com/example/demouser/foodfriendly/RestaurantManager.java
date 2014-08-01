package com.example.demouser.foodfriendly;

import android.content.Context;

import com.example.demouser.foodfriendly.FilterPref;
import com.example.demouser.foodfriendly.R;
import com.example.demouser.foodfriendly.Restaurant;
import com.example.demouser.foodfriendly.Review;
import com.example.demouser.foodfriendly.Utility;
import com.example.demouser.foodfriendly.Utility.FilterStatus;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * Created by demouser on 8/1/14.
 */
public class RestaurantManager {


    HashMap<String,Restaurant> mAllRestaurants;
    HashSet<String> mLactoseFreeRestaurants;
    HashSet<String> mGlutenFreeRestaurants;
    HashSet<String> mVeganRestaurants;
    HashSet<String> mVegetarianRestaurants;

    Context mContext;

    public RestaurantManager(Context contex) {
        mContext = contex;
        mAllRestaurants = new HashMap<String, Restaurant>();
        mLactoseFreeRestaurants = new HashSet<String>();
        mGlutenFreeRestaurants = new HashSet<String>();
        mVeganRestaurants = new HashSet<String>();
        mVegetarianRestaurants = new HashSet<String>();
    }

    public void updateRestaurants(List<HashMap<String,String>> restaurants){
        for(HashMap<String,String> restaurantMap : restaurants){
            String id = restaurantMap.get(R.string.restaurant_place_id);
            if(!mAllRestaurants.containsKey(id)){
                Restaurant newRestaurant = new Restaurant(restaurantMap);
                mAllRestaurants.put(id, newRestaurant);
            }
        }
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
        if(filterPref.getState(Utility.LACTOSE_STATE,mContext)){
            for(String restaurantId : mLactoseFreeRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.GLUTEN_STATE,mContext)){
            for(String restaurantId : mGlutenFreeRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.VEGAN_STATE,mContext)){
            for(String restaurantId : mVeganRestaurants){
                resultSet.add(mAllRestaurants.get(restaurantId));
            }
        }
        if(filterPref.getState(Utility.VEGETARIAN_STATE,mContext)){
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

}
