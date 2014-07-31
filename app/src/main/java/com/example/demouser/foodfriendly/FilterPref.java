package com.example.demouser.foodfriendly;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by Maya Arbel on 31/07/2014.
 */
public class FilterPref {

    private final String  LACTOSE_STATE = "lactose_state";

    private SharedPreferences mSp = null;

    protected boolean isLactoseFree(Context context){
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
        return mSp.getBoolean(LACTOSE_STATE, false);
    }

    protected boolean toggleLactoseFree(Context context){
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean newValue =!isLactoseFree(context);
        mSp.edit().putBoolean(LACTOSE_STATE, newValue);

        return newValue;
    }


}
