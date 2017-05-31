package com.g1g.cs102.storypad;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Boran1 on 22.4.2016.
 */
public class ImageAdapter extends PagerAdapter {

    //properties
    Context context;
    private int[] gallery;

    //constructor
    public ImageAdapter(Context context, int[] gallery){
        this.context = context;
        this.gallery = gallery;
    }

    @Override
    public int getCount() {
        return gallery.length;
    }

    //if view equals object with type casting
    // to ImageView returns true, otherwise return false.
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        ImageView imageView = new ImageView(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(gallery[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        ((ViewPager) container).removeView((ImageView) object);
    }
}