package com.g1g.cs102.storypad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Omer Ergun & Enes Kaya on 23.4.2016.
 */

public class FavoriteViewer extends AppCompatActivity {

    //properties
    private DiaryComponentContainer favorite, keeper;
    private LinearLayout mainLinear, keeperLayout;
    private ScrollView scrollView;
    private RelativeLayout mainRelative;
    private TextView topText;
    private int widthDevice, heightDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_viewer);

        //background color
        getWindow().getDecorView().setBackgroundColor(Color.rgb(112 , 170, 130));

        //taking device display size
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        heightDevice = metrics.heightPixels;
        widthDevice =  metrics.widthPixels;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //keeping the original list
        keeper  = MainActivity.keepContainer;
        favorite = new DiaryComponentContainer();

        //taking favorite ones from keeper and implementing process
        for (int i = 0 ; i < keeper.size() ; i++){
            if(keeper.get(i).getFavorite() == true)
                favorite.add(keeper.get(i));
        }

        //reletive layout
        mainRelative = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlvPar = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mainRelative.setLayoutParams(rlvPar);
        setContentView(mainRelative);

        //scrolview and linearlayout
        scrollView = new ScrollView(this);
        scrollView.setLayoutParams(rlvPar);
        mainLinear = new LinearLayout(this);
        mainLinear.setOrientation(LinearLayout.VERTICAL);
        mainLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLinear.setY((int) ((double) 225 / 1080 * widthDevice));
        mainLinear.setPadding(0, 0, 0, (int) ((double) 225 / 1080 * widthDevice));
        scrollView.addView(mainLinear);

        //adding toptext (My Favorites)
        topText = new TextView(this);
        topText.setText("My Favorites");
        topText.setTextSize(25f);
        topText.setTypeface(Typeface.SERIF);
        topText.setY((int) ((double) 58 / 1080 * widthDevice));
        topText.setLayoutParams( new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        topText.setGravity(Gravity.CENTER_HORIZONTAL);
        topText.setTextColor(Color.WHITE);
        mainRelative.addView(topText);

        print();

    }

    //printing content
    public void print(){
        //printing texts and images
        for (int i = favorite.size() - 1; i >= 0; i--) {

            //keeper layout
            keeperLayout = new LinearLayout(this);
            keeperLayout.setOrientation(LinearLayout.HORIZONTAL);
            keeperLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 1000 / 1080 * widthDevice), (int) ((double) 440 / 1080 * widthDevice)));
            keeperLayout.setX((int) ((double) 165 / 1080 * widthDevice));

            //printing days
            TextView textDay = new TextView(this);
            textDay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            textDay.setText(favorite.get(i).Day() + " / " + (favorite.get(i).getDate().getMonth() + 1) + " / " + favorite.get(i).getDate().getYear());
            textDay.setTextColor(Color.BLACK);
            textDay.setTextSize(18f);
            textDay.setX((int) ((double) 160 / 1080 * widthDevice));
            textDay.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            //putting line under day
            ImageView line1 = new ImageView(this);
            line1.setBackgroundResource(R.mipmap.line_thick);
            line1.setX((int) ((double) 115 / 1080 * widthDevice));
            line1.setLayoutParams(new RelativeLayout.LayoutParams((int)((double)400/1080*widthDevice),(int)((double)85/1080*widthDevice)));

            //checking need for new day textVİew
            if (i == favorite.size() - 1) {
                mainLinear.addView(textDay);
                mainLinear.addView(line1);
            }
            else if (i >= 0 && i < favorite.size() - 1 && favorite.get(i).getDate().compareTo(favorite.get(i + 1).getDate()) != 0) {
                mainLinear.addView(textDay);
                mainLinear.addView(line1);
            }

            //textTime
            TextView textTime = new TextView(this);
            textTime.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 200 / 1080 * widthDevice), (int) ((double) 200 / 1080 * widthDevice)));

            //taking hour and minute from component
            textTime.setText(favorite.get(i).getHour() + "\n" + favorite.get(i).getMinute());
            textTime.setTextColor(Color.WHITE);
            textTime.setTextSize(21f);
            textTime.setGravity(Gravity.CENTER);

            //adding it to keeper layout and determining its shape
            keeperLayout.addView(textTime);
            textTime.setBackgroundResource(R.mipmap.time);

            //checking is the component text or not?
            if (favorite.get(i) instanceof Text ){

                //creating new text object for every text ind Favorite
                TextView textView = new TextView(this);
                textView.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 425 / 1080 * widthDevice), (int) ((double) 425 / 1080 * widthDevice)));

                textView.setText( ((Text) favorite.get(i)).getMainText());
                textView.setTextColor(((Text) favorite.get(i)).getColor());
                textView.setX((int) ((double) 80 / 1080 * widthDevice));

                //determininig te text and its color to view in textviewer
                final String keepText = ((Text) favorite.get(i)).getMainText();
                final int colorKeep = (((Text) favorite.get(i)).getColor());
                //add click listener to to textViewer
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MainActivity.GOTO_TEXTROOM = keepText;
                        MainActivity.color = colorKeep;
                        startActivity(new Intent(FavoriteViewer.this, TextViewer.class));
                    }
                });

                //adding text to keeplayout
                keeperLayout.addView(textView);

            } else if (favorite.get(i) instanceof Image){

                //TAKİNG Byte array parameter from container object converting to bitmap
                Bitmap bitmapFromImageComponent = BitmapFactory.decodeByteArray(((Image) favorite.get(i)).getView(), 0, ((Image) favorite.get(i)).getView().length);

                //creating new imageview for every image in favorite class
                ImageView imageShow = new ImageView(this);
                //setting bitmap to image
                imageShow.setImageBitmap(bitmapFromImageComponent);
                imageShow.setX((int) ((double) 80 / 1080 * widthDevice));
                imageShow.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 425 / 1080 * widthDevice), (int) ((double) 425 / 1080 * widthDevice)));

                //keeping bitmap to be viewed in imageviewer
                final Bitmap viewkeep = BitmapFactory.decodeByteArray(((Image) favorite.get(i)).getView(), 0, ((Image) favorite.get(i)).getView().length);
                //clicklistener
                imageShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.GotoImageViever = viewkeep;
                        startActivity(new Intent(FavoriteViewer.this, ImageViewer.class));
                    }
                });

                //adding image to keepLayout
                keeperLayout.addView(imageShow);
            }

            //adding keepLayout to mainLinear
            keeperLayout.setGravity(Gravity.CENTER_VERTICAL);
            mainLinear.addView(keeperLayout);

            //creating and adding line between posts
            ImageView line = new ImageView(this);
            line.setBackgroundResource(R.mipmap.line_thick);
            line.setX(0);
            line.setLayoutParams(new RelativeLayout.LayoutParams((int)((double)1080/1080*widthDevice),(int)((double)75/1080*widthDevice)));
            mainLinear.addView(line);
        }
        //adding scrollview which contains mainLinear to mainRelative
        mainRelative.addView(scrollView);
    }
}
