package com.example.myalarm.dao;

import android.app.PendingIntent;

import java.io.Serializable;

/**
 * Created by Rakshith on 7/13/2015.
 */
public class AlamInfo implements Serializable {
      int Id;
      String date;
      String time;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
