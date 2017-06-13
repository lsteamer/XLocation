package com.elmexicano.lsteamer.xlocation;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by lsteamer on 13/06/2017.
 */

public class FullScreenImageAdapter extends PagerAdapter{

    Context thisContext;
    LayoutInflater layoutInflater;


    int [] resources = {
            R.drawable.xberg,
            R.drawable.lacoon,
            R.drawable.max
    };

    public FullScreenImageAdapter(Context context){
        thisContext = context;
        layoutInflater = (LayoutInflater) thisContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        View itemView = layoutInflater.inflate(R.layout.image_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(resources[position]);

        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout) object);
    }


}
