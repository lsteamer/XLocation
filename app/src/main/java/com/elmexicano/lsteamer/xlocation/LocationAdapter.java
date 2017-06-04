package com.elmexicano.lsteamer.xlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.ArrayList;

/**
 * Created by lsteamer on 30/05/2017.
 */

public class LocationAdapter extends ArrayAdapter<LocationData> {

    /**
     * The View Holder will hold the id's
     */
    public static class ViewHolder{
        TextView title;
        TextView distance;
        TextView address;
        ImageView image;

    }

    public LocationAdapter(Context context, ArrayList<LocationData> locations){
        super(context, 0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Gets the data item for this position
        LocationData locations = getItem(position);

        //creating a ViewHolder
        ViewHolder viewHolder;

        //Checking if an existing view is being reused, otherwise inflate a new view from custom row layout
        if(convertView == null){

            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_row, parent, false);

            //Set the references to the viewHolder
            viewHolder.title = (TextView) convertView.findViewById(R.id.locationTitle);
            viewHolder.distance = (TextView) convertView.findViewById(R.id.locationDistance);
            viewHolder.address = (TextView) convertView.findViewById(R.id.locationAddress);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.locationIcon);

            //Using this tag to extract the references
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Fills each new referenced view with data associated with the note it's referencing
        viewHolder.title.setText(locations.getTitle());
        viewHolder.address.setText(locations.getAddress());
        viewHolder.distance.setText(String.valueOf(locations.getDistace())+"m");
        Glide.with(getContext())
                .load(locations.getIcon())
                .into(viewHolder.image);

        //returning the view
        return convertView;
    }

}
