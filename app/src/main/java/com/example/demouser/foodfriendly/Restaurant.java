package com.example.demouser.foodfriendly;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.example.demouser.foodfriendly.Utility.FilterStatus;

/**
 * Created by demouser on 8/1/14.
 */
public class Restaurant {


    public String getPlaceId() {
        return mPlaceId;
    }

    public Restaurant(HashMap<String,String> map){
        this.mPlaceId = map.get(R.string.restaurant_place_id);
        this.mName = map.get(R.string.restaurant_name);
        this.mAddress = map.get(R.string.restaurant_address);
        this.mUrl = map.get(R.string.restaurant_url);
        String ranking = map.get(R.string.restaurant_ranking);
        this.isRanked = ranking == "NA-" ? false : true;
        if(isRanked) {
            this.mRanking = Float.parseFloat(ranking);
        }
        this.mReviews = new ArrayList<Review>();

    }

    public void setPlaceId(String placeId) {
        mPlaceId = placeId;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public FilterStatus getLactoseFree() {
        return mLactoseFree;
    }

    public void setLactoseFree(FilterStatus lactoseFree) {
        mLactoseFree = lactoseFree;
    }

    public FilterStatus getGlutenFree() {
        return mGlutenFree;
    }

    public void setGlutenFree(FilterStatus glutenFree) {
        mGlutenFree = glutenFree;
    }

    public FilterStatus getVegan() {
        return mVegan;
    }

    public void setVegan(FilterStatus vegan) {
        mVegan = vegan;
    }

    public FilterStatus getVegetarian() {
        return mVegetarian;
    }

    public void setVegetarian(FilterStatus vegetarian) {
        mVegetarian = vegetarian;
    }

    public float getRanking() {
        return mRanking;
    }

    public void setRanking(float ranking) {
        mRanking = ranking;
    }

    public boolean isRanked() {
        return isRanked;
    }

    public void setRanked(boolean isRanked) {
        this.isRanked = isRanked;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        mDistance = distance;
    }

    public ArrayList<Review> getReviews() {
        return mReviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        mReviews = reviews;
    }

    private String mPlaceId;
    private String mName;
    private String mAddress;
    private String mUrl;
    private FilterStatus mLactoseFree = FilterStatus.UNFILTERED;
    private FilterStatus mGlutenFree = FilterStatus.UNFILTERED;
    private FilterStatus mVegan = FilterStatus.UNFILTERED;
    private FilterStatus mVegetarian = FilterStatus.UNFILTERED;
    private float mRanking;
    private boolean isRanked;
    private float mDistance;

    private ArrayList<Review> mReviews;


    public void addReview(Review review) {
        if(review.getLactoseFree()!= FilterStatus.UNFILTERED){
            this.mLactoseFree = review.getLactoseFree();
        }
        if(review.getGlutenFree()!= FilterStatus.UNFILTERED){
            this.mGlutenFree = review.getGlutenFree();
        }
        if(review.getVegan()!= FilterStatus.UNFILTERED){
            this.mVegan = review.getVegan();
        }
        if(review.getVegetarian()!=FilterStatus.UNFILTERED){
            this.mVegetarian = review.getVegetarian();
        }
        int newRank = review.getRank();
        if (isRanked == false){
            this.isRanked = true;
        }
        //calculate new ranking....
        float oldRanking = this.mRanking;
        this.mRanking = (oldRanking*mReviews.size() + newRank)/(float) (mReviews.size()+1);
        mReviews.add(review);
    }
}
