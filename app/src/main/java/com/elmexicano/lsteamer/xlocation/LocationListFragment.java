package com.elmexicano.lsteamer.xlocation;


import android.os.Bundle;
import android.app.ListFragment;
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



        locations = new ArrayList<>();
        locations.add(new LocationData("Santa Maria EastSide", "Berlin",30));
        locations.add(new LocationData("Los Burritos de la esquina", "PHamburg",30));
        locations.add(new LocationData("Ahuevo", "Asperg",30));
        locations.add(new LocationData("Bahdag", "Cork",30));
        locations.add(new LocationData("OGI", "Puebla",30));
        locations.add(new LocationData("Thai", "PGuadalajara",30));
        locations.add(new LocationData("Döner", "Querétaro",30));
        locations.add(new LocationData("Indian", "Morelia",30));
        locations.add(new LocationData("Koreane", "Mocos",30));
        locations.add(new LocationData("Sushi", "Madrid",30));
        locations.add(new LocationData("Chinese", "Amsterdam",30));
        locations.add(new LocationData("Santa Maria EastSide", "London",30));
        locations.add(new LocationData("Santa Maria EastSide", "Porto",30));
        locations.add(new LocationData("Santa Maria EastSide", "Brussels",30));
        locations.add(new LocationData("Santa Maria EastSide", "Lisboa",30));

        locationAdapter = new LocationAdapter(getActivity(),locations);

        setListAdapter(locationAdapter);

    }

}
