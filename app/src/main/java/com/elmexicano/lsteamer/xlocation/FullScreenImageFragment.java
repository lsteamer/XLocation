package com.elmexicano.lsteamer.xlocation;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

/**
 * Created by lsteamer on 13/06/2017.
 */

public class FullScreenImageFragment extends PagerAdapter{

    private Context thisContext;
    private LayoutInflater layoutInflater;


    public FullScreenImageFragment(Context context){
        thisContext = context;
        layoutInflater = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return FullScreenImageActivity.imagesURL.length;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        Glide.with(thisContext)
                .load("https://igx.4sqi.net/img/general/original"+FullScreenImageActivity.imagesURL[position])
                .into(imageView);
        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout) object);
    }


}
