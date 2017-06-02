package com.elmexicano.lsteamer.xlocation;


import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * ListFragment that will hold the List
 */
public class LocationListFragment extends ListFragment {

    private ArrayList<LocationData> locations;
    private LocationAdapter locationAdapter;


    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){

        super.onActivityCreated(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();

        locations = (ArrayList<LocationData>) extras.getSerializable("list");


        locationAdapter = new LocationAdapter(getActivity(),locations);

        setListAdapter(locationAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        launchLocationDetailActivity(position);
    }

    //Launching a new Activity
    private void launchLocationDetailActivity(int position){
        LocationData locationData = (LocationData) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",locations);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}
