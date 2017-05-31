package com.g1g.cs102.storypad;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class WritingActivity extends ActionBarActivity {

    private EditText text;
    private DiaryComponent addingText;
    static DiaryComponentContainer container = new DiaryComponentContainer();
    private int tmp1, tmp;
    private Button chooseColor , chooseStyle, black ,red ,blue ,style1,style2,style3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        text = (EditText) findViewById(R.id.editText);

        // class background color
        getWindow().getDecorView().setBackgroundColor(Color.rgb(241, 220, 170));

        //button id's
        chooseColor =(Button)findViewById(R.id.chooseColor);
        chooseStyle =(Button)findViewById(R.id.chooseStyle);
        black =(Button)findViewById(R.id.black);
        red =(Button)findViewById(R.id.red);
        blue =(Button)findViewById(R.id.blue);
        style1 =(Button)findViewById(R.id.style1);
        style2 =(Button)findViewById(R.id.style2);
        style3 =(Button)findViewById(R.id.style3);

        //button bacgrounds
        black.setBackgroundResource(R.mipmap.black);
        red.setBackgroundResource(R.mipmap.green);
        blue.setBackgroundResource(R.mipmap.blue);
        chooseColor.setBackgroundResource(R.mipmap.color_wheel);
        style1.setBackgroundResource(R.mipmap.default1_style);
        style2.setBackgroundResource(R.mipmap.bold1_style);
        style3.setBackgroundResource(R.mipmap.italic1_style);
        chooseStyle.setBackgroundColor(Color.WHITE);
        chooseStyle.setBackgroundColor(Color.TRANSPARENT);
        chooseStyle.setTextSize(18f);

        //text size
        text.setTextSize(20f);

        tmp1 = 0;
        tmp = 0;

        final Button[] colors = {black, red , blue};
        final Button[] styles = {style1,style2,style3};

        //making invisible in creation
        for(int i = 0 ; i < colors.length ; i++) {
            colors[i].setVisibility(View.INVISIBLE);
        }
        //making invisible in creation
        for(int i = 0 ; i < styles.length ; i++) {

            styles[i].setVisibility(View.INVISIBLE);
        }

        //choosing color
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Visibilty
                if(tmp1 == 0)
                {
                    //making butons visible
                    for(int i = 0 ; i < colors.length ; i++ ) {
                        colors[i].setVisibility(View.VISIBLE);
                    }
                    tmp1 = 1;
                }

                else if(tmp1 == 1)
                {
                    //making butons invisible
                    for(int i = 0 ; i < colors.length ; i++ ) {
                        colors[i].setVisibility(View.INVISIBLE);
                    }
                    tmp1 = 0;
                }

                //choosing color black
                for(int i = 0 ; i < colors.length ; i++)
                {
                    final int  keep = i;
                    colors[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            setColor(keep);
                        }
                    });
                }
            }
        });

        //choosing style
        chooseStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Visibilty
                if(tmp == 0)
                {
                    //making butons visible
                    for(int i = 0 ; i < styles.length ; i++ )
                    {
                        styles[i].setVisibility(View.VISIBLE);
                    }
                    tmp = 1;
                }

                else if(tmp == 1)
                {
                    //making butons invisible
                    for(int i = 0 ; i < styles.length ; i++ )
                    {
                        styles[i].setVisibility(View.INVISIBLE);
                    }
                    tmp = 0;
                }

                //choosing style
                for(int i = 0 ; i < styles.length ; i++)
                {
                    final int  keep = i;
                    styles[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            setStyle(keep);
                        }
                    });
                }
            }
        });
    }

    //setting styles
    public void setStyle(int i)
    {
        if(i == 0){
            text.setTypeface(Typeface.DEFAULT);
        }
        else if(i == 1){
            text.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
        else if(i == 2){
            text.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }
    //setting Colors
    public void setColor(int i)
    {
        if(i == 0){
            text.setTextColor(Color.BLACK);
        }
        else if(i == 1){
            text.setTextColor(Color.rgb(12, 127, 23));
        }
        else if(i == 2){
            text.setTextColor(Color.rgb(12, 23, 127));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //creating text by written story
        addTexttoComponent();
    }

    //adding text to component
    public void addTexttoComponent(){
        addingText = new Text(text.getText().toString(), text.getCurrentTextColor());//,text.getTypeface());
        //adding this text toDiaryComponentContainer
        container.add(addingText);
    }
}