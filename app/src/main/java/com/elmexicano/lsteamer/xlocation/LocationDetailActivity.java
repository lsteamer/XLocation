package com.elmexicano.lsteamer.xlocation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LocationDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        Bundle extras = getIntent().getExtras();
        LocationData locationData = (LocationData) extras.getSerializable("location data");



        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(locationData.getTitle());


    }


}
