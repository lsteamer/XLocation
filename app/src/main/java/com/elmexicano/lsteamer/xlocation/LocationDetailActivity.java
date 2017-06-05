package com.elmexicano.lsteamer.xlocation;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LocationDetailActivity extends AppCompatActivity {

    private LocationData locationData;
    private TextView tvTitle;
    private TextView tvCategory;
    private TextView tvAddress;
    private TextView tvPostalCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        //Obtaining the data
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

        String[] locationImagesSuffix = locationData.getImagesSuffix();

        /**
         * Below is the image code
         */
        if(locationImagesSuffix!=null&&locationImagesSuffix.length>0){
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.imageLinear);
                for(int i = 0; i<locationImagesSuffix.length; i++){
                    ImageView imageView = new ImageView(this);
                    imageView.setId(i);
                    imageView.setPadding(2,2,2,2);
                    Glide.with(this)
                            .load("https://igx.4sqi.net/img/general/300x300"+locationImagesSuffix[i])
                            .into(imageView);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    linearLayout.addView(imageView);
                }

        }





        //Setting the Values
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
