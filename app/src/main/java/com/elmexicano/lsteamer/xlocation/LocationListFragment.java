package com.elmexicano.lsteamer.xlocation;


import android.os.Bundle;
import android.app.ListFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.ArrayAdapter;

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

}
