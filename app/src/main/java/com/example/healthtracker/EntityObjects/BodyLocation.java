package com.example.healthtracker.EntityObjects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class BodyLocation implements Serializable {
    private String locationText;
    private String bodyGraphic;

    public BodyLocation(String locationText, String bodyGraphic){
        this.locationText = locationText;
        this.bodyGraphic = bodyGraphic;
    }

    public BodyLocation(){
        locationText = "";
        bodyGraphic = "";
    }

    public void addGraphic(String newGraphic){
        bodyGraphic = newGraphic;
    }

    public void deleteGraphic(){
        bodyGraphic = null;
    }

    public String getGraphic(){
        return bodyGraphic;
    }

    public void setLoc(String newLoc){
        this.locationText = newLoc;
    }

    public String getLoc(){
        return this.locationText;
    }




    @Override
    public String toString(){
        return " BodyLocation: "+ getLoc() + "\n Graphic: " + getGraphic();
    }


}
