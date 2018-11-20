package com.example.healthtracker;


import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class PhotoTest {
    private Photo photo;
    private String fileLocation;
    private PatientRecord parentRecord;


    @Before
    public void setUp() {
        fileLocation = "file location";
        Problem p1 = new Problem("Rash", new Date(),"Uncomfortable rash on arm.");
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
