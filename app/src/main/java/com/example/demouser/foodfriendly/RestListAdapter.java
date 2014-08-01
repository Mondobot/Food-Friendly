package com.example.demouser.foodfriendly;

import android.content.Context;
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

    public RestListAdapter(Context context){
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
            holder.mAddress = (TextView)v.findViewById(R.id.restaurant_address);
            holder.mDist = (TextView)v.findViewById(R.id.restaurant_address);

            v.setTag(holder);

        } else {
            holder = (RestaurantViewHolder)convertView.getTag();
        }

        holder.mTitle.setText(mDummyData[position]);

        return v;
    }

    private class RestaurantViewHolder {
        public ImageView mImage;
        public TextView mTitle;
        public TextView mAddress;
        public TextView mDist;
    }
}


