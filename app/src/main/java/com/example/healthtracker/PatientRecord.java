package com.example.healthtracker;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class PatientRecord implements Serializable {

    private String RecordTitle;
    private String comment;
    private Timestamp timestamp;
    private ArrayList<Bitmap> geoLocations;
    private ArrayList<Photo> photos;


    public PatientRecord(String title, String comment){
        this.RecordTitle = title;
        this.comment = comment;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    public PatientRecord(){
        RecordTitle = "";
        comment = "";
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void addGeoLocation(Bitmap geoLocation){
        geoLocations.add(geoLocation);
    }

    public void deleteGeoLocation(Bitmap geoLocation){
        geoLocations.remove(geoLocation);
    }


    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public void deletePhoto(int index){
        photos.remove(index);
    }

    public Photo getPhoto(int index){
        return photos.get(index);
    }

    public void setTitle(String newTitle){
        this.RecordTitle = newTitle;
    }

    public String getTitle(){
        return this.RecordTitle;
    }

    public String getComment(){ //This is different from Jori's UML
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setTimestamp(){
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String toString(){
        return " Title: " + getTitle() + "\n Comment: " + getComment() + "\n Timestamp: " + timestamp.toString();
    }


}
