package com.elmexicano.lsteamer.xlocation;


import android.os.Bundle;
import android.app.ListFragment;
import android.widget.ArrayAdapter;

/**
 * ListFragment that will hold the List
 */
public class LocationListFragment extends ListFragment {


    public LocationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        String[] values = new String[]{"Berlin","Hamburg","Asperg",
                "Cork","Dublin",
                "CDMX","Puebla","Guadalajara"
                };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);

        setListAdapter(adapter);

    }

}
