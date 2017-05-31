package com.g1g.cs102.storypad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Boran1 on 20.4.2016.
 */
public class StoryBuilderActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_builder_page);

        //buttons
        ImageButton alienButton = (ImageButton) findViewById(R.id.alien_button);
        alienButton.setOnClickListener(this);
        ImageButton dragonButton = (ImageButton) findViewById(R.id.dragon_button);
        dragonButton.setOnClickListener(this);
        ImageButton frogButton = (ImageButton) findViewById(R.id.frog_button);
        frogButton.setOnClickListener(this);
    }

    //decides which button clicked
    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.alien_button:
                intent = new Intent(this, AlienActivity.class);
                break;
            case R.id.dragon_button:
                intent = new Intent(this, DragonActivity.class);
                break;
            case R.id.frog_button:
                intent = new Intent(this, FrogActivity.class);
                break;
        }

        if (intent != null) {
            intent.setAction(intent.ACTION_VIEW);
            startActivity(intent);
        }
    }
}
