package com.g1g.cs102.storypad;

import java.io.Serializable;

/**
 * Created by Omer Ergun & Enes Kaya on 21.4.2016.
 */

public class Image extends DiaryComponent implements Serializable {
    //properties
    private byte[] view;

    //constructor
    public Image( byte[]  view){
        this.view = view;
    }

    //getter
    public  byte[]  getView() {
        return view;
    }
}


