package com.elmexicano.lsteamer.xlocation;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FullScreenImageActivity extends AppCompatActivity {


    private FullScreenImageFragment imageAdapter;

    private ViewPager viewPager;
    public static int position;
    public static String[] imagesURL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        position = getIntent().getIntExtra("position",0);
        imagesURL = getIntent().getStringArrayExtra("imagesURL");


        imageAdapter = new FullScreenImageFragment(this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(imageAdapter);
        viewPager.setCurrentItem(position);

    }
}
