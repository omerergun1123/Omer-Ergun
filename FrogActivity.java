package com.g1g.cs102.storypad;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.facebook.FacebookSdk;

/**
 * Created by Boran1 on 20.4.2016.
 */
public class FrogActivity extends AppCompatActivity {

    //properties
    EditText text;
    private int[] gallery = new int[]{
            R.drawable.frog_1,
            R.drawable.frog_2,
            R.drawable.frog_3,
            R.drawable.frog_4,
            R.drawable.frog_5,
            R.drawable.frog_6,
            R.drawable.frog_7,
            R.drawable.frog_8,
            R.drawable.frog_9
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frog_page);
        FacebookSdk.sdkInitialize(getApplicationContext());

        text = (EditText) findViewById(R.id.frog_text_field);
        ViewPager viewPager = (ViewPager) findViewById(R.id.frog_view_pager);
        ImageAdapter adapter = new ImageAdapter(this, gallery);
        viewPager.setAdapter(adapter);
        loadSavedPreferences();
    }


    //load the saved text
    private void loadSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean check = preferences.getBoolean("text_value1", false);
        String field = preferences.getString("storedText1","yourText1");
        text.setText(preferences.getString("textString1", ""));
    }


    //save the written text
    private void savePreferences(String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveData(){
        savePreferences("textString1", text.getText().toString());
    }

    //saved when back button pressed
    @Override
    public void onBackPressed(){
        saveData();
        super.onBackPressed();
    }
}