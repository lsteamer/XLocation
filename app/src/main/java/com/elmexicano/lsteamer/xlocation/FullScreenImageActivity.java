package com.elmexicano.lsteamer.xlocation;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FullScreenImageActivity extends AppCompatActivity {


    FullScreenImageAdapter imageAdapter;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        imageAdapter = new FullScreenImageAdapter(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(imageAdapter);

    }
}
