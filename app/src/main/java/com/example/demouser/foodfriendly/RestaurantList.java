package com.example.demouser.foodfriendly;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class RestaurantList extends Fragment {
    private ListView mListView;
    private FragmentCallback mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.restaurantlist, null);
        mListView = (ListView) v.findViewById(R.id.restaurant_list);

        Log.d("something", "pass");

        mListView.setAdapter(new RestListAdapter(getActivity()));


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String myText = (String) mListView.getItemAtPosition(position);
                mListener.OnItemClicked(Utility.REST_FRAG, myText);

            }
        });


        return v;
    }

    public void onAttach(Activity act) {
        super.onAttach(act);
        if (act instanceof FragmentCallback) {
            mListener = (FragmentCallback) act;

        } else {
            throw new IllegalStateException("Activity should implement RestaurantList");
        }
    }

}
