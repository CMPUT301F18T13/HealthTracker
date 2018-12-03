package com.example.healthtracker.EntityObjects;

import android.graphics.Bitmap;
import android.location.Address;

import com.example.healthtracker.EntityObjects.Photo;

import org.elasticsearch.common.geo.GeoPoint;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * The patient record is a record added to a problem by a patient.
 *
 * @author Michael Boisvert
 * @since 2018-11-10
 * @version 1.0
 */
public class PatientRecord implements Serializable {

    @JestId
    private String RecordTitle;
    private String comment;
    private Timestamp timestamp;
    private Double Lon;
    private Double Lat;
    private ArrayList<Double> geoLocations = new ArrayList<>();
    private final ArrayList<Photo> photos;
    private BodyLocation bodyLoc;


    /**
     * Constructor for PatientRecord that sets the record title and comment.
     *
     * @param title A string for the title of the record
     * @param comment A string for description of the problem
     */
    public PatientRecord(String title, String comment, Double Lon, Double Lat,BodyLocation bodyLocation){
        this.RecordTitle = title;
        this.comment = comment;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.Lat = Lat;
        this.Lon = Lon;
        this.geoLocations.add(Lon);
        this.geoLocations.add(Lat);
        photos = new ArrayList<Photo> ();
        this.bodyLoc = bodyLocation;
    }

    /**
     * Constructor for PatientRecord that requires no parameters and initializes title and comment
     * as empty strings.
     */
    public PatientRecord(){
        RecordTitle = "";
        comment = "";
        timestamp = new Timestamp(System.currentTimeMillis());
        geoLocations = new ArrayList<Double>();
        photos = new ArrayList<Photo> ();
        bodyLoc = new BodyLocation();
    }

    /**
     * Add a bodyLocation to the record
     * @param newBodyLocation
     */

    public void setBodyLoc(BodyLocation newBodyLocation){
        this.bodyLoc = newBodyLocation;
    }

    /**
     * Get a bodyLocation of the record
     * @return BodyLocation
     */

    public BodyLocation getBodyLoc(){
        return this.bodyLoc;
    }

    /**
     * Add a geoLocation to the record.
     *
     * @param geoLocation The geoLocation to be added.
     */
    public void setGeoLocation(Double Lon, Double Lat){
        this.geoLocations.add(Lon);
        this.geoLocations.add(Lat);
    }

    /**
     * Delete a geoLocation from this record.
     *
     * @param geoLocation The geoLocation to be deleted.
     */
    public ArrayList<Double> getGeoLocation(){
        return this.geoLocations;
    }

    /**
     * Add a photo to the record.
     *
     * @param photo The photo to be added.
     */
    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    /**
     * Delete a photo from the record.
     *
     * @param index The index of the photo to be deleted from the photo list.
     */
    public void deletePhoto(int index){
        photos.remove(index);
    }

    /**
     * Get a photo from the record.
     *
     * @param index The index of the photo in the list of photos.
     * @return Returns the photo in the photo list that corresponds to the index input.
     */
    public Photo getPhoto(int index){
        return photos.get(index);
    }

    /**
     * Set the title of the record to a new title.
     *
     * @param newTitle A string that will be the new title.
     */
    public void setTitle(String newTitle){
        this.RecordTitle = newTitle;
    }

    /**
     * Get the current title of the record.
     *
     * @return A string that is the current title of the record.
     */
    public String getTitle(){
        return this.RecordTitle;
    }

    /**
     * Get the current description of the record.
     *
     * @return A string that is the current description of the record.
      */
    public String getComment(){
        return comment;
    }

    /**
     * Set a new description for the record.
     *
     * @param comment A string that will be the new description of the record.
     */
    public void setComment(String comment){
        this.comment = comment;
    }

    /**
     * Set the record's timestamp to the current time.
     */
    public void setTimestamp(){
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Get the Timestamp currently associated with the PatientRecord.
     *
     * @return The current PatientRecord TimeStamp.
     */
    public Timestamp getTimestamp(){
        return this.timestamp;
    }

    /**
     * Display the record as a string. Will be used in listviews.
     */
    @Override
    public String toString(){
        return " Title: " + getTitle() + "\n Comment: " + getComment() + "\n Timestamp: " + timestamp.toString();
    }


}
