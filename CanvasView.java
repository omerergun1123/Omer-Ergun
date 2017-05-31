package com.g1g.cs102.storypad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class CanvasView extends View {

    private int width, height, backgroundColor, control;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private Paint mPaint, eraser;
    private Path mPath;

    //arraylists for paints and paths
    private ArrayList<Path> paths1 = new ArrayList<>();
    private ArrayList<Paint> paints1 = new ArrayList<>();

    //constructor
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        this.setDrawingCacheEnabled(true);

        // we set a new Path and paint to start
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(10f);

        // and we set a new Paint with the desired attributes
        eraser = new Paint();
        backgroundColor = Color.WHITE;
        eraser.setColor(backgroundColor);
        eraser.setColor(backgroundColor);
        eraser.setAntiAlias(true);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeWidth(40f);

        control = 0;
    }

    //arraylist getter
    public ArrayList<Path> getPaths(){
        return paths1;
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(backgroundColor);

        //drawing all paths
        for (int i = 0; i < paths1.size() ; i++) {
            canvas.drawPath(paths1.get(i), paints1.get(i));
        }
    }

    // when ACTION_DOWN start touch according to the x,y values
    private void startTouch(float x, float y) {
        if(control == 0) {
            paths1.add(mPath);
            paints1.add(mPaint);
            control = control + 1;
        }
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    // when ACTION_MOVE move touch according to the x,y values
    private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }
    //clearing all canvas
    public void clearCanvas() {
        //removing all paths
        for (int i = 0; i < paths1.size() ; i++) {
            paths1.get(i).reset();
        }
        invalidate();
    }

    // when ACTION_UP stop touch
    private void upTouch() {
        mPath.lineTo(mX, mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch();
                invalidate();
                break;
        }
        return true;
    }

    //changing color
    public void changeColor(int i)
    {
        //keeping old strokeround
        float keepF = mPaint.getStrokeWidth();
        //creating new paths after color change
        mPath = new Path();
        mPaint = new Paint();
        //adding them to arraylist
        paints1.add(mPaint);
        paths1.add(mPath);
        //features
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(keepF);

        //checking process
        if (i == 0) {
            mPaint.setColor(Color.CYAN);
        } else if (i == 1) {
            mPaint.setColor(Color.GREEN);
        } else if (i == 2) {
            mPaint.setColor(Color.RED);
        } else if (i == 3) {
            mPaint.setColor(Color.BLUE);
        } else if (i == 4) {
            mPaint.setColor(Color.YELLOW);
        }
         else if (i == 5) {
            mPaint.setColor(Color.WHITE);
        }
         else if (i == 6) {
            mPaint.setColor(Color.BLACK);
        }
    }

    //size of pencil
    public void changeSize(int i)
    {
        //keeping old color
        int keep = mPaint.getColor();
        //creating new path and paint affter sizechange
        mPath = new Path();
        mPaint = new Paint();
        //adding them to arraylist
        paints1.add(mPaint);
        paths1.add(mPath);
        //features
        mPaint.setColor(keep);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        //checking for determine a size
        if(i == 0)
        {
            mPaint.setStrokeWidth(10f);
        }
        else if(i == 1)
        {
            mPaint.setStrokeWidth(16f);
        }
        else if(i == 2)
        {
            mPaint.setStrokeWidth(23f);
        }
        else if(i == 3)
        {
            mPaint.setStrokeWidth(35f);
        }
    }

    //changing bacground color
    public void changeBackgroundColor(int i)
    {
        //checking
        if(i == 0)
        {
            backgroundColor = Color.BLACK;
            invalidate();
        }

        else if (i == 1)
        {
            backgroundColor = Color.WHITE;
            invalidate();
        }
        else if(i == 2)
        {
            backgroundColor = Color.YELLOW;
            invalidate();
        }

        //equilizing erase color with background and basic features
        eraser.setColor(backgroundColor);
        eraser.setAntiAlias(true);
        eraser.setStyle(Paint.Style.STROKE);
        eraser.setStrokeJoin(Paint.Join.ROUND);
        eraser.setStrokeWidth(40f);
    }

    //method to erase
    public void erase()
    {
        //creating new way to implment erasing process
        mPath = new Path();
        //adding new path and paint of eraser
        paths1.add(mPath);
        paints1.add(eraser);
    }
}