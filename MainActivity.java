package com.g1g.cs102.storypad;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;

/**
 * Created by Omer Ergun & Enes Kaya on 19.4.2016.
 */

public class MainActivity extends AppCompatActivity {

    private Button buttonWrite, buttonGallery, buttonDelete, buttonFavorite, buttonGoFavorite, buttonPaint;
    private RelativeLayout mainRelative;
    private ScrollView scrollView;
    private LinearLayout mainLinear,keeperLayout, keeperLayoutButtons;
    private DiaryComponentContainer componentContainer;
    private boolean checkSave, checkSave1, checkSave2;
    private Bitmap bitmap;
    private String fileName = "texts1";
    static String GOTO_TEXTROOM = "";
    static int color;
    static Bitmap GotoImageViever;
    static DiaryComponentContainer keepContainer;
    private int widthDevice, heightDevice, r, g, b, keep1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //properties for do not save same story, paint ır image again and again
        checkSave = true;
        checkSave1 = true;
        checkSave2 = true;

        //starting of colorful background
        r = 94;
        g = 150;
        b = 200;
        keep1 = 1;
        colorfulBackground();

        //taking device sizes
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        heightDevice = metrics.heightPixels;
        widthDevice = metrics.widthPixels;

    }

    //loading
    public void load() {

        try {
            //reaing opened file and revising componentContainer object
            FileInputStream fis = openFileInput(fileName);
            ObjectInputStream is = new ObjectInputStream(fis);
            componentContainer = (DiaryComponentContainer) is.readObject();
            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //if there is no componentContainer
        if (componentContainer == null)
            componentContainer = new DiaryComponentContainer();
    }

    //saving
    public void save() {

        try {
            //opening file to write object
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(componentContainer);
            os.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmap = null;

        if (bitmap != null) {
            bitmap.recycle();
        }
        try {
            //banning the error if user does not take any imag from gallery by if statement
            if (data != null) {
                InputStream stream = getContentResolver().openInputStream(data.getData());

                bitmap = BitmapFactory.decodeStream(stream);
                Bitmap tmp = bitmap;

                //changing size according to original size
                if (bitmap.getWidth() <= 1080) {
                    bitmap = Bitmap.createScaledBitmap(
                            tmp, (int) (tmp.getWidth() * 0.40), (int) (tmp.getHeight() * 0.40), false);
                } else if (bitmap.getWidth() >= 1080) {//&& bitmap.getWidth() <= 2160 ){
                    bitmap = Bitmap.createScaledBitmap(
                            tmp, (int) (tmp.getWidth() * 0.2), (int) (tmp.getHeight() * 0.2), false);

                }
                //saying "OK" to save process of taken image by making checksave false
                checkSave1 = false;
                stream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onResume() {
        super.onResume();
        load();
        reviseContainer();

        //reletive layout for keeping all components
        mainRelative = new RelativeLayout(this);
        RelativeLayout.LayoutParams rlvPar = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mainRelative.setLayoutParams(rlvPar);
        setContentView(mainRelative);

        //scrolview and maintLinearlayout
        scrollView = new ScrollView(this);
        scrollView.setLayoutParams(rlvPar);
        mainLinear = new LinearLayout(this);
        mainLinear.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        mainLinear.setOrientation(LinearLayout.VERTICAL);
        mainLinear.setY((int) ((double) 300 / 1080 * widthDevice));
        if(componentContainer.size() <= 0)
            mainLinear.setPadding((int) ((double) 1080 / 1080 * widthDevice), 0, 0, (int) ((double) 300 / 1080 * widthDevice));
        else
            mainLinear.setPadding((int) ((double) 0 / 1080 * widthDevice), 0, 0, (int) ((double) 300 / 1080 * widthDevice));
        scrollView.addView(mainLinear);



        //BUTTONS
        //write a story button
        buttonWrite = new Button(this);
        buttonWrite.setX((int) ((double) 110 / 1080 * widthDevice));
        buttonWrite.setY((int) ((double) 90 / 1080 * widthDevice));
        buttonWrite.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 150 / 1080 * widthDevice), (int) ((double) 150 / 1080 * widthDevice)));
        //adding and shape
        mainRelative.addView(buttonWrite);
        buttonWrite.setBackgroundResource(R.mipmap.write);
        //click listener
        buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WritingActivity.class));
                //in order to do not save  same story again
                checkSave = false;
            }
        });

        //gallery button
        buttonGallery = new Button(this);
        buttonGallery.setX((int) ((double) 340 / 1080 * widthDevice));
        buttonGallery.setY((int) ((double) 90 / 1080 * widthDevice));
        buttonGallery.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 155 / 1080 * widthDevice), (int) ((double) 155 / 1080 * widthDevice)));
        //adding and shape
        mainRelative.addView(buttonGallery);
        buttonGallery.setBackgroundResource(R.mipmap.gallery);
        //clicklistener
        buttonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 44);

            }
        });

        //going favorite ones button
        buttonGoFavorite = new Button(this);
        buttonGoFavorite.setX((int) ((double) 570 / 1080 * widthDevice));
        buttonGoFavorite.setY((int) ((double) 90 / 1080 * widthDevice));
        buttonGoFavorite.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 150 / 1080 * widthDevice), (int) ((double) 150 / 1080 * widthDevice)));
        //adding and shape
        mainRelative.addView(buttonGoFavorite);
        buttonGoFavorite.setBackgroundResource(R.mipmap.favorites);
        //clicklistener
        buttonGoFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteViewer.class));
            }
        });

        //going paint bbutton
        buttonPaint = new Button(this);
        buttonPaint.setX((int) ((double) 800 / 1080 * widthDevice));
        buttonPaint.setY((int) ((double) 90 / 1080 * widthDevice));
        buttonPaint.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 145 / 1080 * widthDevice), (int) ((double) 145 / 1080 * widthDevice)));
        //adding and shape
        mainRelative.addView(buttonPaint);
        buttonPaint.setBackgroundResource(R.mipmap.paint);
        //clicklistener
        buttonPaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Paint.class));
                //preparing onresume() to save taken image and in order to do not save  same image again
                checkSave2 = false;
            }
        });

        print();

    }

    //printing method
    public void print(){
        //loading before print
        load();
        //printing texts and images
        for (int i = componentContainer.size() - 1; i >= 0; i--) {

            //keeper layout which contains time, image or text, del and fav buttons
            keeperLayout = new LinearLayout(this);
            keeperLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams keepLayputParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            keepLayputParam.setMargins(0, ((int) ((double) 0 / 1080 * widthDevice)), ((int) ((double) 10 / 1080 * widthDevice)), ((int) ((double) 0 / 1080 * widthDevice)));
            keeperLayout.setLayoutParams(keepLayputParam);

            //buttonKeeper vertical layout
            keeperLayoutButtons = new LinearLayout(this);
            keeperLayoutButtons.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 400 / 1080 * widthDevice), (int) ((double) 260 / 1080 * widthDevice)));
            keeperLayoutButtons.setOrientation(LinearLayout.VERTICAL);

            //button delete
            buttonDelete = new Button(this);
            RelativeLayout.LayoutParams buttonDelFavParam = new RelativeLayout.LayoutParams((int) ((double) 120 / 1080 * widthDevice), (int) ((double) 120 / 1080 * widthDevice));
            buttonDelete.setLayoutParams(buttonDelFavParam);

            //button favorite
            buttonFavorite = new Button(this);
            buttonFavorite.setLayoutParams(buttonDelFavParam);

            //textview for days
            TextView textDay = new TextView(this);
            textDay.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            textDay.setText(componentContainer.get(i).Day() + " / " + (componentContainer.get(i).getDate().getMonth() + 1) + " / " + componentContainer.get(i).getDate().getYear());
            textDay.setTextColor(Color.BLACK);
            textDay.setTextSize(18f);
            textDay.setX((int) ((double) 417 / 1080 * widthDevice));
            textDay.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            //creating lines for days
            ImageView line1 = new ImageView(this);
            line1.setBackgroundResource(R.mipmap.line_thick);
            line1.setX((int) ((double) 370 / 1080 * widthDevice));
            line1.setLayoutParams( new RelativeLayout.LayoutParams((int)((double)400/1080*widthDevice),(int)((double)85/1080*widthDevice)));

            if (i == componentContainer.size() - 1) {
                //adding days and lines under it
                mainLinear.addView(textDay);
                mainLinear.addView(line1);

            } else if (i >= 0 && i < componentContainer.size() - 1 && componentContainer.get(i).getDate().compareTo(componentContainer.get(i + 1).getDate()) != 0) {
                //adding days and lines under it
                mainLinear.addView(textDay);
                mainLinear.addView(line1);
            }

            //creating timeFor every post in diary
            TextView textTime = new TextView(this);
            textTime.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 200 / 1080 * widthDevice), (int) ((double) 200 / 1080 * widthDevice)));

            //taking hour and minute from component
            textTime.setText(componentContainer.get(i).getHour() + "\n" + componentContainer.get(i).getMinute());

            //features
            textTime.setTextColor(Color.WHITE);
            textTime.setTextSize(21f);
            textTime.setGravity(Gravity.CENTER);

            //adding timeText to keeperlayout for every post and determining its shape
            keeperLayout.addView(textTime);
            textTime.setBackgroundResource(R.mipmap.time);

            //checking is the component text or not?
            if (componentContainer.get(i) instanceof Text) {

                //creating new text object for every text post
                TextView textView = new TextView(this);
                textView.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 425 / 1080 * widthDevice), (int) ((double) 425 / 1080 * widthDevice)));
                textView.setText(((Text) componentContainer.get(i)).getMainText());
                textView.setTextColor(((Text) componentContainer.get(i)).getColor());
                textView.setX((int) ((double) 80 / 1080 * widthDevice));

                //keeping text color and mainText to send textViever
                final String keepText = ((Text) componentContainer.get(i)).getMainText();
                final int colorKeep = (((Text) componentContainer.get(i)).getColor());
                //add click listener to to textViewer
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GOTO_TEXTROOM = keepText;
                        color = colorKeep;
                        startActivity(new Intent(MainActivity.this, TextViewer.class));
                    }
                });
                //adding textView to keeperLayout
                keeperLayout.addView(textView);

            } else if (componentContainer.get(i) instanceof Image) {

                //TAKİNG Byte array parameter from container object converting to bitmap
                Bitmap bitmapFromImageComponent = BitmapFactory.decodeByteArray(((Image) componentContainer.get(i)).getView(), 0, ((Image) componentContainer.get(i)).getView().length);

                //creating new image for every post
                ImageView imageShow = new ImageView(this);

                //setting bitmap to image
                imageShow.setImageBitmap(bitmapFromImageComponent);
                imageShow.setX((int) ((double) 80 / 1080 * widthDevice));
                imageShow.setLayoutParams(new RelativeLayout.LayoutParams((int) ((double) 425 / 1080 * widthDevice), (int) ((double) 425 / 1080 * widthDevice)));

                //taking byteArray from particular image object and converting to bitmap to send imageViever
                final Bitmap viewkeep = BitmapFactory.decodeByteArray(((Image) componentContainer.get(i)).getView(), 0, ((Image) componentContainer.get(i)).getView().length);
                //clicklistener
                imageShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GotoImageViever = viewkeep;
                        startActivity(new Intent(MainActivity.this, ImageViewer.class));
                    }
                });
                //adding image to keeperLayout
                keeperLayout.addView(imageShow);

            }

            //deleting process
            final int indexKeep1 = i;
            //button delete function clicklistener
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //removing particular one
                    componentContainer.remove(indexKeep1);
                    //saving and printing again by onResume()
                    save();
                    onResume();
                }
            });


            //button favorite clicklistener
            buttonFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (componentContainer.get(indexKeep1).getFavorite()) {
                        //chaning selection condition and shape of favorite button
                        componentContainer.get(indexKeep1).setFavorite(false);
                        buttonFavorite.setBackgroundResource(R.mipmap.fav_empty);
                    } else {
                        //chaning selection condition and shape of favorite button
                        componentContainer.get(indexKeep1).setFavorite(true);
                        buttonFavorite.setBackgroundResource(R.mipmap.fav);
                    }
                    //and saving after selection process
                    save();
                    //priting again by calling onresume again
                    onResume();
                }
            });

            //adding buttons to their own vertical linearlayout
            keeperLayoutButtons.addView(buttonDelete, buttonDelFavParam);
            keeperLayoutButtons.addView(buttonFavorite, buttonDelFavParam);

            //determining delete and favorite button's shape
            if (componentContainer.get(i).getFavorite()) {
                buttonFavorite.setBackgroundResource(R.mipmap.fav);
            } else {
                buttonFavorite.setBackgroundResource(R.mipmap.fav_empty);
            }
            buttonDelete.setBackgroundResource(R.mipmap.delete_main);

            //layout features
            keeperLayoutButtons.setX((int) ((double) 150 / 1080 * widthDevice));
            keeperLayout.setX((int) ((double) 80 / 1080 * widthDevice));
            keeperLayout.setGravity(Gravity.CENTER);

            //adding buttons to keeper
            keeperLayout.addView(keeperLayoutButtons);

            //adding keeper layout which contains one post to main vertical layout
            mainLinear.addView(keeperLayout);

            //line betveen keeperLayouts
            ImageView line = new ImageView(this);
            line.setBackgroundResource(R.mipmap.line_thick);
            line.setX(0);
            line.setLayoutParams( new RelativeLayout.LayoutParams((int)((double)1080/1080*widthDevice),(int)((double)75/1080*widthDevice)));

            //adding line after keeperLayout(posts)
            mainLinear.addView(line);

        }
        //adding scrolview which contains mainLinear to mainRelative
        mainRelative.addView(scrollView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //keeping current component container to be read from favoriteviewew
        keepContainer = componentContainer;
    }

    //compress method
    public byte[] compress(Bitmap bitmap){
        //changing taken bitmap to bitmap array to save process
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //colorful background
    public void colorfulBackground(){
        //Timer for changing colors
        new CountDownTimer(999999999,150){
            public void onTick(long l){
                getWindow().getDecorView().setBackgroundColor(Color.rgb(r, g, b));
                {
                    if(keep1 % 2 == 1 ) {
                        if (r < 200 ) {
                            r = r + 1;
                        } else if (b > 94) {
                            b = b - 1;
                        } else if (g < 200) {
                            g = g + 1;
                        }
                        else if(g == 200)
                            keep1 = 0;
                    }
                    else {
                        if (r > 94)
                            r = r - 1;
                        else if (b < 200)
                            b = b + 1;
                        else if (g > 150) {
                            g = g - 1;
                        }
                        else if (g == 150)
                            keep1 = 1;
                    }
                }
            }
            public void onFinish(){
            }
        }.start();
    }

    public void reviseContainer(){
        //initializing image again and again and equalizing with bitmap
        ImageView imageview = new ImageView(this);
        //checking canvas is empty or not
        if (Paint.customCanvas != null && checkSave2 == false && Paint.customCanvas.getPaths().size() >= 1) {

            //resizing taken bitmap from paint
            Bitmap keeper = Bitmap.createScaledBitmap(
                    Paint.customCanvas.getDrawingCache(), (int) (Paint.customCanvas.getDrawingCache().getWidth() * 0.75), (int) (Paint.customCanvas.getDrawingCache().getHeight() * 0.75), false);
            //adding to new image component
            Image image1 = new Image(compress(keeper));
            checkSave2 = true;
            componentContainer.add(image1);
            save();
        }

        //Taken image from gallery and saving
        if (bitmap != null && checkSave1 == false) {

            //adding to new image component
            Image image = new Image(compress(bitmap));;
            componentContainer.add(image);
            save();
            checkSave1 = true;
        }

        //Story save
        //in order to do not save  same story again
        if (checkSave == false) {

            //checking save type and not adding written story to container if it is empty
            if (WritingActivity.container.size() != 0  && !((Text) WritingActivity.container.get(WritingActivity.container.size() - 1)).getMainText().equals("") &&
                    !((Text) WritingActivity.container.get(WritingActivity.container.size() - 1)).getMainText().equals(" ") && !((Text) WritingActivity.container.get(WritingActivity.container.size() - 1)).getMainText().equals("  ")) {
                componentContainer.add(WritingActivity.container.get(WritingActivity.container.size() - 1));
            }
            save();
            checkSave = true;
        }
    }
}
