package com.g1g.cs102.storypad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageButton diaryButton = (ImageButton) findViewById(R.id.diary_button);
        diaryButton.setOnClickListener(this);
        ImageButton storyBuilderButton = (ImageButton) findViewById(R.id.story_builder_button);
        storyBuilderButton.setOnClickListener(this);
        ImageButton hintsButton = (ImageButton) findViewById(R.id.hint_button);
        hintsButton.setOnClickListener(this);
        Button aboutButton = (Button) findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        System.gc();
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.diary_button:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.story_builder_button:
                intent = new Intent(this, StoryBuilderActivity.class);
                break;
            case R.id.hint_button:
                intent = new Intent(this, HintsActivity.class);
                break;
            case R.id.about_button:
                intent = new Intent(this, AboutActivity.class);
                break;
        }

        if (intent != null){
            intent.setAction(intent.ACTION_VIEW);
            startActivity(intent);
        }
    }
}
