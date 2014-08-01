package com.example.demouser.foodfriendly;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import static android.view.View.OnClickListener;


public class FilterFragment extends Fragment {
    private OnFragmentInteractionListener mListener;


    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.filter, container, false);
        FilterPref ownPref = new FilterPref();

        View general = v.findViewById(R.id.btn_dairy_free);
        general.setOnClickListener(mFilterSelectOnClickListener);
        if (!ownPref.getState(Utility.LACTOSE_STATE, v.getContext())) {
            general.setAlpha(Utility.SELECT_OFF);
            Log.d("initialize lactose","false");
        }

        general = v.findViewById(R.id.btn_gluten_free);
        general.setOnClickListener(mFilterSelectOnClickListener);
        if (!ownPref.getState(Utility.GLUTEN_STATE, v.getContext()))
            general.setAlpha(Utility.SELECT_OFF);

        general = v.findViewById(R.id.btn_vegan);
        general.setOnClickListener(mFilterSelectOnClickListener);
        if (!ownPref.getState(Utility.VEGAN_STATE, v.getContext()))
            general.setAlpha(Utility.SELECT_OFF);


        general = v.findViewById(R.id.btn_vegetarian);
        general.setOnClickListener(mFilterSelectOnClickListener);
        if (!ownPref.getState(Utility.VEGETARIAN_STATE, v.getContext()))
            general.setAlpha(Utility.SELECT_OFF);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    final OnClickListener mFilterSelectOnClickListener = new OnClickListener() {
        @Override
        public void onClick(final View v) {
            FilterPref f = new FilterPref();
            boolean newState = false;

            switch(v.getId()) {
                case R.id.btn_dairy_free:
                    newState = f.toggleFilterState(Utility.LACTOSE_STATE, v.getContext());
                    break;

                case R.id.btn_gluten_free:
                    newState = f.toggleFilterState(Utility.GLUTEN_STATE, v.getContext());
                    break;

                case R.id.btn_vegan:
                    newState = f.toggleFilterState(Utility.VEGAN_STATE, v.getContext());
                    break;

                case R.id.btn_vegetarian:
                    newState = f.toggleFilterState(Utility.VEGETARIAN_STATE, v.getContext());
                    break;
            }

            PropertyValuesHolder pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f);
            PropertyValuesHolder pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f);


            if (newState) {
                v.setAlpha(Utility.SELECT_ON);
            } else {
                v.setAlpha(Utility.SELECT_OFF);
                pvhSX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.8f);
                pvhSY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.8f);
            }

            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v,pvhSX,pvhSY);
            animator.setRepeatCount(1);
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setDuration(100);
            v.setClickable(false);
            animator.start();
            v.setClickable(true);
        }
    };

}
