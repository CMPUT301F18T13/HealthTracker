package com.example.healthtracker;


import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Photo;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PhotoTest {
    private Photo photo;
    private String fileLocation;
    private PatientRecord parentRecord;


    @Before
    public void setUp() {
        fileLocation = "file location";
        Problem p1 = new Problem();
        photo = new Photo(fileLocation);
    }
    @Test
    public void getFile() {
        assertEquals(photo.getFile(),fileLocation);

    }

    @Test
    public void setFile() {
        String newfileLocation = "file location";
        photo.setFile(newfileLocation);
        assertEquals(photo.getFile(),newfileLocation);
    }

    @Test
    public void getRecord() {

    }


}
