package com.example.healthtracker.EntityObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BodyLocation implements Serializable {
    private String locationText;
    //private float pinX;
    //private float pinY;
    private Bitmap bodyGraphic;

    public BodyLocation(String locationText, Bitmap bodyGraphic){
        this.locationText = locationText;
        //this.pinX = x;
        //this.pinY = y;
        this.bodyGraphic = bodyGraphic;
    }

    public BodyLocation(){
        locationText = "";
        //pinX = 0;
        //pinY = 0;
        bodyGraphic = null;
    }

    public void addGraphic(Bitmap newGraphic){
        bodyGraphic = newGraphic;
    }

    public void deleteGraphic(){
        bodyGraphic = null;
    }

    public Bitmap getGraphic(){
        return bodyGraphic;
    }

    public void setLoc(String newLoc){
        this.locationText = newLoc;
    }

    public String getLoc(){
        return this.locationText;
    }



/*
    @Override
    public String toString(){
        return " Title: " + getTitle() + "\n Comment: " + getComment() + "\n Timestamp: " + timestamp.toString();
    }
*/

}
