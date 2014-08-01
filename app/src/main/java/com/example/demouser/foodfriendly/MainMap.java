package com.example.demouser.foodfriendly;


import java.util.Locale;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demouser.foodfriendly.map.DetailsFragment;
import com.example.demouser.foodfriendly.map.GoogleMapFragment;
import com.example.demouser.foodfriendly.map.MapController;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainMap extends ActionBarActivity implements
        ActionBar.TabListener, FilterFragment.OnFragmentInteractionListener,
        FragmentCallback {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
     * derivative, which will keep every loaded fragment in memory. If this
     * becomes too memory intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private GoogleMapFragment mMapFragment;

    private static final int TAB_FILTERS = 0;
    private static final int TAB_MAP = 1;
    private static final int TAB_NEARBY = 2;
    private static final int TAB_RESTAURANT = 3;
    private static final int TAB_REVIEW = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(10);
        mMapFragment = (GoogleMapFragment) mSectionsPagerAdapter.getItem(TAB_MAP);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }

        RestaurantManager.initialize(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
        case R.id.action_settings:
            return true;
        case R.id.action_show_me:

            mViewPager.setCurrentItem(TAB_MAP);
            if(mMapFragment != null) {
                mMapFragment.showCurrentLocation();
            }
            return true;
        case R.id.action_search_nearby:
            if(mMapFragment != null) {
                mMapFragment.searchNearbyCurrentLocation();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab,
                              FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab,
                                FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void OnItemClicked(String fragType, Object data) {
        Log.d("OnItemClicker", "OnItemClicked entering");
        if (fragType.equals(Utility.REST_FRAG) || fragType.equals(Utility.MAP_FRAG)) {
            Log.d("OnItemClicker", "OnItemClicked wohoo");
            String myString = (String)data;
            ActionBar actionBar = getSupportActionBar();

            // Do click logic
            // We need to move this in the fragment file
            DetailsFragment selectedRest = (DetailsFragment)mSectionsPagerAdapter.getItem(TAB_RESTAURANT);
            TextView restName = (TextView)selectedRest.getView().findViewById(R.id.restaurant_name);
            restName.setText(myString);

            actionBar.selectTab(actionBar.getTabAt(TAB_RESTAURANT));
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        FilterFragment mFilterFragment;
        GoogleMapFragment mGoogleMapFragment;
        RestaurantList mRestaurantList;
        DetailsFragment mDetailsFragment;
        PlaceholderFragment mPlaceholderFragment5;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFilterFragment = new FilterFragment();
            mGoogleMapFragment = new GoogleMapFragment();
            mRestaurantList = new RestaurantList();
            mDetailsFragment = new DetailsFragment();
            mPlaceholderFragment5 = new PlaceholderFragment();
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class
            // below).
            switch (position) {
                case TAB_FILTERS:
                    return mFilterFragment;
                case TAB_MAP:
                    return mGoogleMapFragment;
                case TAB_NEARBY:
                    return mRestaurantList;
                case TAB_RESTAURANT:
                    return mDetailsFragment;
                case TAB_REVIEW:
                    return mPlaceholderFragment5;
                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case TAB_FILTERS:
                    return getString(R.string.title_filters).toUpperCase(l);
                case TAB_MAP:
                    return getString(R.string.title_map).toUpperCase(l);
                case TAB_NEARBY:
                    return getString(R.string.title_nearby).toUpperCase(l);
                case TAB_RESTAURANT:
                    return getString(R.string.title_restaurant).toUpperCase(l);
                case TAB_REVIEW:
                    return getString(R.string.title_review).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }

}
