package com.g1g.cs102.storypad;

import java.io.Serializable;

/**
 * Created by Omer Ergun & Enes Kaya on 20.4.2016.
 */

public class Text extends DiaryComponent implements Serializable{

    //properties
    private String mainText;
    private int colorText;

    //constructor
    public Text(String mainText, int colorText){//, Typeface face){
        super();
        this.mainText = mainText;
        this.colorText = colorText;
    }

    //returning color
    public int getColor(){
        return colorText;
    }

    //getter for maintext
    public String getMainText() {
        return mainText;
    }
}
