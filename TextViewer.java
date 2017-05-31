package com.g1g.cs102.storypad;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class TextViewer extends ActionBarActivity {

    private TextView viewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_viewer);

        // class background color
        getWindow().getDecorView().setBackgroundColor(Color.rgb(241, 220, 170));
    }

   @Override
    protected void onResume() {
        super.onResume();
       showText();
    }

    //showing text
    public void showText(){
    //taking text from main activity
        String keep = MainActivity.GOTO_TEXTROOM;
        viewer = (TextView) findViewById(R.id.viewer);
        viewer.setText(keep);
        viewer.setTextColor(MainActivity.color);
        viewer.setTextSize(17f);
    }
}
