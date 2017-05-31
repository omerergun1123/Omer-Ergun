package com.g1g.cs102.storypad;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.facebook.FacebookSdk;

import com.facebook.share.widget.ShareButton;

/**
 * Created by Boran1 on 20.4.2016.
 */
public class AlienActivity extends AppCompatActivity {

    //properties
    EditText text;
    private int[] gallery = new int[]{
            R.drawable.uzayli_1,
            R.drawable.uzayli_2,
            R.drawable.uzayli_3,
            R.drawable.uzayli_4,
            R.drawable.uzayli_5,
            R.drawable.uzayli_6
    };

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alien_page);

        text = (EditText) findViewById(R.id.alien_text_field);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this, gallery);
        viewPager.setAdapter(adapter);
        loadSavedPreferences();
    }


    //load the saved text
    private void loadSavedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean check = preferences.getBoolean("text_value", false);
        String field = preferences.getString("storedText","yourText");
        text.setText(preferences.getString("textString", ""));
    }


    //save the written text
    private void savePreferences(String key, String value){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveData(){
        savePreferences("textString", text.getText().toString());
    }

    //saved when back button pressed
    @Override
    public void onBackPressed(){
        saveData();
        super.onBackPressed();
    }
}

