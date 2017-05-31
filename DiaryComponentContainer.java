package com.g1g.cs102.storypad;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Omer Ergun & Enes Kaya on 25.4.2016.
 */

public class DiaryComponentContainer implements  Serializable {

    //properties
    private ArrayList<DiaryComponent> diaryComponentContainer= new ArrayList<>();

    //adding method for arraylist
    public void add(DiaryComponent component){
        diaryComponentContainer.add(component);
    }

    //returning size of arraylist
    public int size(){
        return diaryComponentContainer.size();
    }

    //getting particular index of the container arraylist
    public DiaryComponent get(int index){
        if(diaryComponentContainer.size() != 0)
            return diaryComponentContainer.get(index);
        else
            return null;
    }

    //removim method
    public void remove(int index){
        diaryComponentContainer.remove(index);
    }
}
