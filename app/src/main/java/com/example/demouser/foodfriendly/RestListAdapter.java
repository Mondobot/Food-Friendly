package com.example.demouser.foodfriendly;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Awesome Engineer on 01/08/2014.
 */
public class RestListAdapter extends BaseAdapter {

    private Context mContext;

    public RestListAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        Log.d("count", String.valueOf(RestaurantManager.getInstance().getCount()));
        return RestaurantManager.getInstance().getCount();
    }

    @Override
    public Object getItem(int position) {
        return RestaurantManager.getInstance().getItemByPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        RestaurantViewHolder holder;
        if (null == convertView) {
            //inflate the view from xml
            LayoutInflater inflater = LayoutInflater.from(mContext);
            v = inflater.inflate(R.layout.restlistitem, null, false);

            holder = new RestaurantViewHolder();
            holder.mImage = (ImageView)v.findViewById(R.id.restaurant_image);
            holder.mTitle = (TextView)v.findViewById(R.id.listview_rest_title);
            holder.mAddress = (TextView)v.findViewById(R.id.listview_address);
            holder.mDist = (TextView)v.findViewById(R.id.listview_dist);

            v.setTag(holder);

        } else {
            holder = (RestaurantViewHolder)convertView.getTag();
        }

        Restaurant r = RestaurantManager.getInstance().getItemByPosition(position);

        holder.mTitle.setText(r.getName());

        Log.d("address", r.getAddress());
        holder.mAddress.setText(r.getAddress());


        String rating = "";
        float rating_f = r.getRanking();
        for (int i = 0; i < rating_f; ++i) {
            rating += "*";
        }

      //  holder.mRating.setText(rating);
       // holder.mDis

        return v;
    }

    private class RestaurantViewHolder {
        public ImageView mImage;
        public TextView mTitle;
        public TextView mAddress;
        public TextView mDist;
        public TextView mRating;
    }
}


