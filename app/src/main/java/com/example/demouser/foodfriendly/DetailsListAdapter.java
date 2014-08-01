package com.example.demouser.foodfriendly;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

/**
 * Created by Awesome Engineer on 01/08/2014.
 */
public class DetailsListAdapter extends BaseAdapter {

        private String[] mDummyData = {"Afghanistan", "Albania", "Algeria", "Andorra", "Angola","Argentina"
                ,"Armenia","Austria","Bahamas","Bahrain", "Bangladesh","Barbados", "Belarus","Belgium",
                "Benin","Bhutan","Bolivia","Bosnia & Herzegovina","Botswana","Brazil","Bulgaria",
                "Burkina Faso","Burma","Burundi","Cambodia","Cameroon","Canada", "China","Colombia",
                "Comoros","Congo","Croatia","Cuba","Cyprus","Czech Republic","Denmark", "Georgia",
                "Germany","Ghana","Great Britain","Greece","Hungary","Holland","India","Iran","Iraq",
                "Italy","Somalia", "Spain", "Sri Lanka", "Sudan","Suriname", "Swaziland","Sweden",
                "Switzerland", "Syria","Uganda","Ukraine","United Arab Emirates","United Kingdom",
                "United States","Uruguay","Uzbekistan","Vanuatu","Venezuela","Vietnam",
                "Yemen","Zaire","Zambia","Zimbabwe"};

        private Context mContext;

        public DetailsListAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mDummyData.length;
        }

        @Override
        public Object getItem(int position) {
            return mDummyData[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if(position == 0)
                return 0;

            return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            DetailsViewHolder holder = null;
            if (null == convertView) {
                //inflate the view from xml
                LayoutInflater inflater = LayoutInflater.from(mContext);
                if (0 == getItemViewType(position)) {
                    v = inflater.inflate(R.layout.details_item, null, false);

                    holder = new DetailsViewHolder();
                    holder.mTitle = (TextView) v.findViewById(R.id.listview_rest_title);
                    holder.mAddress = (TextView) v.findViewById(R.id.restaurant_address);
                    holder.mDist = (TextView) v.findViewById(R.id.distance);

                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star1));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star2));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star3));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star4));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star5));

                    holder.mUserRating.add((ImageView)v.findViewById(R.id.rate_and_review_star1));
                    holder.mUserRating.add((ImageView)v.findViewById(R.id.rate_and_review_star2));
                    holder.mUserRating.add((ImageView)v.findViewById(R.id.rate_and_review_star3));
                    holder.mUserRating.add((ImageView)v.findViewById(R.id.rate_and_review_star4));
                    holder.mUserRating.add((ImageView)v.findViewById(R.id.rate_and_review_star5));


                } else {
                    v = inflater.inflate(R.layout.review_item, null, false);
                    holder = new DetailsViewHolder();

                    holder.mTitle = (TextView) v.findViewById(R.id.review_name);

                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star1));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star2));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star3));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star4));
                    holder.mRating.add((ImageView)v.findViewById(R.id.rating_star5));

                    holder.mText = (TextView) v.findViewById(R.id.review_text);
                }

                v.setTag(holder);


            } else {
                holder = (DetailsViewHolder)convertView.getTag();
            }

            //holder.mTitle.setText(mDummyData[position]);

            return v;
        }

        private class DetailsViewHolder {
            public TextView mTitle, mAddress, mDist;
            public ImageView mDairy, mGluten, mVegan, mVegetarian;
            public Vector<ImageView> mRating = new Vector<ImageView>();
            public Vector<ImageView> mUserRating = new Vector<ImageView>();
            public TextView mUrl;

            public TextView mText;
        }
}

