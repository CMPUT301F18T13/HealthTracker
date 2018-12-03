package com.example.healthtracker;

import android.graphics.Bitmap;

import com.example.healthtracker.EntityObjects.BodyLocation;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Photo;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PatientRecordTest {

    private String title;
    private String comment;
    private ArrayList<Bitmap> geoLocations;
    private ArrayList<Photo> photos;
    private PatientRecord patientRecord;
    private Double lon;
    private Double lat;
    private Timestamp testTime;
    private BodyLocation bodyLocation;

    @Before
    public void setUp() {
        title = "PatientRecord";
        comment = "My finger is hurt.";
        Photo photo = new Photo("file location");
        Date dateStarted = new Date(( new java.util.Date()).getTime());
        String timestamp = "16:00:00";
        Problem parentProblem = new Problem();
        lat = 52.301293 ;
        lon = 43.321341;
        bodyLocation = new BodyLocation();

        patientRecord = new PatientRecord(title, comment, lon, lat,bodyLocation);
    }

    @Test
    public void setGeoLocation() {
        patientRecord.setGeoLocation(lon,lat);
        assertEquals(patientRecord.getGeoLocation().toString(),"[43.321341, 52.301293]");
    }

    @Test
    public void getGeoLocation() {
        assertEquals(patientRecord.getGeoLocation().toString(),"[43.321341, 52.301293]");
    }

    /*
    @Test
    public void addPhoto() {
        int index = 0;
        Photo newPhoto = new Photo("file location 1");
        patientRecord.addPhoto(newPhoto);
        assertEquals(newPhoto, patientRecord.getPhoto(index));
    }

    @Test
    public void getPhoto() {
        Photo newPhoto = new Photo("file location 1");
        patientRecord.addPhoto(newPhoto);

        Photo newPhoto2 = new Photo("file location 2");
        patientRecord.addPhoto(newPhoto2);
        assertEquals(patientRecord.getPhoto(1),newPhoto2);
    }

    @Test
    public void deletePhoto() {
        Photo newPhoto = new Photo("file location 1");
        patientRecord.addPhoto(newPhoto);

        Photo newPhoto2 = new Photo("file location 2");
        patientRecord.addPhoto(newPhoto2);

        patientRecord.deletePhoto(0);
        assertEquals(patientRecord.getPhoto(0), newPhoto2);
    }*/

    @Test
    public void getTitle() {
        assertEquals(title, patientRecord.getTitle());
    }

    @Test
    public void setTitle() {
        patientRecord.setTitle("New title");

        assertNotEquals(title, patientRecord.getTitle());
        assertEquals("New title", patientRecord.getTitle());
    }

    @Test
    public void getComment() {
        assertEquals(comment, patientRecord.getComment());
    }

    @Test
    public void setComment() {
        patientRecord.setComment("New Comment");

        assertNotEquals(comment, patientRecord.getComment());
        assertEquals("New Comment", patientRecord.getComment());
    }

    @Test
    public void setTimeStamp(){
        patientRecord.setTimestamp();
        assertNotEquals(patientRecord.getTimestamp(),null);
    }

    @Test
    public void getTimeStamp(){
       assertNotEquals(patientRecord.getTimestamp(),null);
    }
}
