package com.elmexicano.lsteamer.xlocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lsteamer on 30/05/2017.
 */

public class LocationAdapter extends ArrayAdapter<LocationData> {

    public LocationAdapter(Context context, ArrayList<LocationData> locations){
        super(context, 0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Gets the data item for this position
        LocationData locations = getItem(position);

        //Checking if an existing view is being reused, otherwise inflate a new view from custom row layout
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_list_row, parent, false);
        }

        //Grab references of views so we can populate them with specific note row data
        TextView locationTitle = (TextView) convertView.findViewById(R.id.locationTitle);
        TextView locationDistance = (TextView) convertView.findViewById(R.id.locationDistance);
        TextView locationAddress = (TextView) convertView.findViewById(R.id.locationAddress);
        ImageView locationIcon = (ImageView) convertView.findViewById(R.id.locationIcon);

        //Fills each new referenced view with data associated with the note it's referencing
        locationTitle.setText(locations.getTitle());
        locationAddress.setText(locations.getAddress());
        locationDistance.setText(String.valueOf(locations.getDistace())+"m");
        locationIcon.setImageResource(locations.getImageIconDEPRECATE());

        //returning the view
        return convertView;
    }

}
