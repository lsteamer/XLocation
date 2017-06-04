package com.elmexicano.lsteamer.xlocation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LocationDetailActivity extends AppCompatActivity {

    private  LocationData locationData;
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvAddress;
    private TextView tvPostalCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        Bundle extras = getIntent().getExtras();
        locationData = (LocationData) extras.getSerializable("location data");

        //Running for the first time
        if(savedInstanceState==null){
            //Fragment showing the map
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            bundle.putFloat("Lat",locationData.getLatitude());
            bundle.putFloat("Lng",locationData.getLongitude());
            bundle.putString("Title",locationData.getTitle());
            mapFragment.setArguments(bundle);
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();


            transaction.replace(R.id.mapLayoutFragment, mapFragment);

            transaction.commit();
        }


        tvTitle = (TextView) findViewById(R.id.titleTextView);
        tvCategory = (TextView) findViewById(R.id.categoryTextView);
        tvAddress = (TextView) findViewById(R.id.addressTextView);
        tvPostalCode = (TextView) findViewById(R.id.postalTextView);

        tvTitle.setText(locationData.getTitle());
        tvCategory.setText(locationData.getCategory());
        tvAddress.setText(locationData.getAddress());
        tvPostalCode.setText(locationData.getPostalCode());


    }


}
