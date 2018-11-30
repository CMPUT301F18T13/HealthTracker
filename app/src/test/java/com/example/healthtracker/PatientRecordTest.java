<<<<<<< HEAD
package com.example.healthtracker;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PatientRecordTest {

    private String title;
    private String comment;
    private Problem parentProblem;
    private String timestamp;
    private ArrayList<Bitmap> geoLocations;
    private ArrayList<Photo> photos;
    private Photo photo;
    private PatientRecord patientRecord;

    @Before
    public void setUp() {
        title = "PatientRecord";
        comment = "My finger is hurt.";
        photo = new Photo("file location");
        Date dateStarted = new Date(( new java.util.Date()).getTime());
        timestamp = "16:00:00";
        parentProblem = new Problem("SaveMyFinger",dateStarted,"I felt bad on my finger.");

        patientRecord = new PatientRecord(title, comment);
    }

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
    }


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

}


=======
package com.example.healthtracker;

import android.graphics.Bitmap;

import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Photo;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PatientRecordTest {

    private String title;
    private String comment;
    private Problem parentProblem;
    private String timestamp;
    private ArrayList<Bitmap> geoLocations;
    private ArrayList<Photo> photos;
    private Photo photo;
    private PatientRecord patientRecord;

    @Before
    public void setUp() {
        title = "PatientRecord";
        comment = "My finger is hurt.";
        photo = new Photo("file location");
        Date dateStarted = new Date(( new java.util.Date()).getTime());
        timestamp = "16:00:00";
        parentProblem = new Problem("SaveMyFinger",dateStarted,"I felt bad on my finger.");

        patientRecord = new PatientRecord(title, comment);
    }

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
    }


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



}


>>>>>>> tyler
