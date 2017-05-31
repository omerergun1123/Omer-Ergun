package com.g1g.cs102.storypad;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class Paint extends Activity {

    static CanvasView customCanvas;
    //color buttons
    private Button clearAll, chooseColor,cyan, yellow, red, blue, green, white, black, eraser,chooseBack, blackBack, whiteBack, yellowBack, size, size1, size2, size3, size4  ;
    private int tmp, tmp1, tmp2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        //keepers
        tmp = 0;
        tmp1 = 0;
        tmp2 = 0;

        //button id's
        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        clearAll = (Button) findViewById(R.id.clearAll);
        chooseColor = (Button) findViewById(R.id.ChooseColor);
        size = (Button) findViewById(R.id.size);
        eraser = (Button) findViewById(R.id.eraser);
        blackBack = (Button) findViewById(R.id.blackBack);
        whiteBack = (Button) findViewById(R.id.whiteBack);
        yellowBack = (Button) findViewById(R.id.yellowBack);
        chooseBack = (Button) findViewById(R.id.chooseBack);
        cyan = (Button) findViewById(R.id.cyan);
        yellow = (Button) findViewById(R.id.yellow);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);
        white = (Button) findViewById(R.id.white);
        black = (Button) findViewById(R.id.black);
        size1 = (Button) findViewById(R.id.size1);
        size2 = (Button) findViewById(R.id.size2);
        size3 = (Button) findViewById(R.id.size3);
        size4 = (Button) findViewById(R.id.size4);

        //keeping buttons in array according to their category
        final Button[] colors = {cyan ,green, red , blue , yellow ,white , black };
        final Button[] sizes = {size1 ,size2 ,size3 , size4};
        final Button[] backs = {blackBack ,whiteBack ,yellowBack };

        //determining backgrounds for buttons
        cyan.setBackgroundResource(R.mipmap.cyan);
        yellow.setBackgroundResource(R.mipmap.yellow);
        red.setBackgroundResource(R.mipmap.red);;
        blue.setBackgroundResource(R.mipmap.blue);;
        green.setBackgroundResource(R.mipmap.green);;
        white.setBackgroundResource(R.mipmap.white1);;
        black.setBackgroundResource(R.mipmap.black1);;
        chooseColor.setBackgroundResource(R.mipmap.colors);
        size.setBackgroundResource(R.mipmap.size1);
        clearAll.setBackgroundResource(R.mipmap.rubbish);
        eraser.setBackgroundResource(R.mipmap.eraser);
        blackBack.setBackgroundResource(R.mipmap.black1);
        whiteBack.setBackgroundResource(R.mipmap.white1);
        yellowBack.setBackgroundResource(R.mipmap.yellow);
        chooseBack.setBackgroundResource(R.mipmap.ic_launcher);
        size1.setBackgroundResource(R.mipmap.black1);
        size2.setBackgroundResource(R.mipmap.black1);
        size3.setBackgroundResource(R.mipmap.black1);
        size4.setBackgroundResource(R.mipmap.black1);


        //making �nvisible background buttons
        for(int i = 0 ; i < backs.length ; i++) {
            backs[i].setVisibility(View.INVISIBLE);
        }

        //making �nvisible color buttons
        for(int i = 0 ; i < colors.length ; i++) {

            colors[i].setVisibility(View.INVISIBLE);
        }
        //making invisible size buttons
        for(int i = 0 ; i < sizes.length ; i++ )
        {
            sizes[i].setVisibility(View.INVISIBLE);
        }

        //changing background color
        for(int i = 0; i < backs.length ; i++) {
            final int keep = i;
            backs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customCanvas.changeBackgroundColor(keep);
                }
            });
        }



        //clearing all screen
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvas.clearCanvas();

            }
        });

        //clearing all screen
        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvas.erase();

            }
        });

        //choosing background
        chooseBack.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(tmp2 == 0)
                {
                    //making butons visible
                    for(int i = 0 ; i < backs.length ; i++ )
                    {
                        backs[i].setVisibility(View.VISIBLE);
                    }
                    tmp2 = 1;
                }

                else if(tmp2 == 1)
                {
                    //making butons invisible
                    for(int i = 0 ; i < backs.length ; i++ )
                    {
                        backs[i].setVisibility(View.INVISIBLE);
                    }
                    tmp2 = 0;
                }

            }
        });
        //choosing size and adding click listener to size buttons
        size.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(tmp1 == 0)
                {
                    //making butons visible
                    for(int i = 0 ; i < sizes.length ; i++ )
                    {
                        sizes[i].setVisibility(View.VISIBLE);
                    }
                    tmp1 = 1;
                }

                else if(tmp1 == 1)
                {
                    //making butons invisible
                    for(int i = 0 ; i < sizes.length ; i++ )
                    {
                        sizes[i].setVisibility(View.INVISIBLE);
                    }
                    tmp1 = 0;
                }

                for(int i = 0 ; i < sizes.length ; i++)
                {
                    final int  keep = i;
                    sizes[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            customCanvas.changeSize(keep);
                        }
                    });
                }
            }
        });


        //choosing color and adding click listener to color buttons
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tmp == 0)
                {
                    //making �nvisible
                    for(int i = 0 ; i < colors.length ; i++) {

                        colors[i].setVisibility(View.VISIBLE);
                    }
                    tmp = 1;
                }

                else if(tmp == 1)
                {
                    //making color button visible
                    for(int i = 0 ; i < colors.length ; i++)
                    {
                        colors[i].setVisibility(View.INVISIBLE);
                    }
                    tmp = 0;
                }

                for(int i = 0 ; i < colors.length ; i++)
                {
                    final int  keep = i;
                    colors[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            customCanvas.changeColor(keep);
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

