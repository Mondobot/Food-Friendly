package com.example.demouser.foodfriendly;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Awesome Enginner on 31/07/2014.
 */
public class FilterPref {
    private SharedPreferences mSp = null;

    protected boolean getState(String type, Context context){
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
        //Log.d("getState", String.valueOf(mSp.getBoolean(type, false)));
        return mSp.getBoolean(type, false);
    }

    //protected void setState(String type)

    protected boolean toggleFilterState(String type, Context context){
        mSp = PreferenceManager.getDefaultSharedPreferences(context);
        boolean newValue = !mSp.getBoolean(type, false);

        //Log.d("toggle", String.valueOf(newValue));
        mSp.edit().putBoolean(type, newValue).apply();

        //Log.d("string", type);
        //Log.d("afterToggle", String.valueOf(mSp.getBoolean(type, false)));
        //Log.d("afterToggle2", String.valueOf(mSp.getBoolean(type, false)));

        return newValue;
    }
}
