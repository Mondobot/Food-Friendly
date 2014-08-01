package com.example.demouser.foodfriendly.map;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.demouser.foodfriendly.FilterFragment;
import com.example.demouser.foodfriendly.FragmentCallback;
import com.example.demouser.foodfriendly.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DetailsFragment extends Fragment {



    public DetailsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.details_layout, container, false);
    }


}
