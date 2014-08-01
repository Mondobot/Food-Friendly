package com.example.demouser.foodfriendly;


import com.example.demouser.foodfriendly.Utility.FilterStatus;

import java.util.Date;

/**
 * Created by demouser on 8/1/14.
 */
public class Review {
    private int mRank;
    private String mText;
    private String mReviewerName;
    private String mTime;

    private FilterStatus mLactoseFree = FilterStatus.UNFILTERED;
    private FilterStatus mGlutenFree = FilterStatus.UNFILTERED;
    private FilterStatus mVegan = FilterStatus.UNFILTERED;
    private FilterStatus mVegetarian = FilterStatus.UNFILTERED;

    private Date mCreationDate;

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getReviewerName() {
        return mReviewerName;
    }

    public void setReviewerName(String reviewerName) {
        mReviewerName = reviewerName;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
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

    public Date getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(Date creationDate) {
        mCreationDate = creationDate;
    }
}
