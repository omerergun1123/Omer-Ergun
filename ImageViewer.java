package com.g1g.cs102.storypad;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class ImageViewer extends ActionBarActivity {
    //properties
    private ImageView view;
    private Bitmap keep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        view = (ImageView) findViewById(R.id.imageView);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //android:theme="@android:style/Theme.NoTitleBar"
        showImage();
    }

    //showing
    public void showImage(){
        //taking image drom main activity
        keep = MainActivity.GotoImageViever;

        //increasing again the size of image
        view.setImageBitmap(Bitmap.createScaledBitmap(
                keep, (int) (keep.getWidth() * 3), (int) (keep.getHeight() * 3), false));

        getWindow().getDecorView().setBackgroundColor(Color.BLACK);
    }
}
