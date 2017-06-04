package com.elmexicano.lsteamer.xlocation;


import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        Log.e("wat",MainActivity.DATE);
    }

    //Launching a new Activity
    private void launchLocationDetailActivity(int position){
        LocationData locationData = (LocationData) getListAdapter().getItem(position);


        //Images are being retrieved ONLY if the method hasn't been called
        if(locationData.getImagesSuffix()==null){
            DownloadImagesJSON imagesAsyncTask = new DownloadImagesJSON();
            try {
                locationData.setImagesSuffix(imagesAsyncTask.execute(locationData.getLocationID(),MainActivity.DATE,MainActivity.CLIENT_ID,MainActivity.CLIENT_SECRET).get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }



        Intent intent = new Intent(getActivity(), LocationDetailActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("location data",locationData);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}
