package com.g1g.cs102.storypad;

import java.io.Serializable;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Omer Ergun & Enes Kaya on 25.4.2016.
 */

public class DiaryComponent implements TimeDate,Serializable {

    private Date date;
    private Time time;
    private Calendar c;
    private boolean favorite;

    //constructor
    public DiaryComponent(){
        //firtly creating calendar and implementing date and time
        c = Calendar.getInstance();
        date = new Date(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        time = new Time(c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),c.get(Calendar.SECOND));
        favorite = false;
    }

    //setfavorite
    public void setFavorite(boolean choise){
        favorite = choise;
    }

    public boolean getFavorite(){
        return favorite;
    }

    //for returning wrong day
    public int Day(){
        return c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public Time getTime() {
        return time;
    }

    //returning true written minute
    public String getMinute(){
        if(time.getMinutes() < 10)
            return  "0" + time.getMinutes();
        else
            return "" + time.getMinutes();
    }

    //returning true written hour
    public String getHour(){
        if(time.getHours() < 10)
            return  "0" + time.getHours();
        else
            return "" + time.getHours();
    }
}
