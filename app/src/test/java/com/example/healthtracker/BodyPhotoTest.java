package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.BodyPhoto;
import com.example.healthtracker.EntityObjects.PatientRecord;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BodyPhotoTest {

    private PatientRecord parentRecord;
    private BodyPhoto bodyPhoto;

    @Before
    public void setUp() {
        String fileLocation = "file location";
        bodyPhoto = new BodyPhoto(fileLocation);
    }

    @Test
    public void setName() {
        String new_name = "name";
        bodyPhoto.setName(new_name);
        assertEquals(bodyPhoto.getName(),new_name);
    }

    @Test
    public void getName() {
        String new_name = "name";
        bodyPhoto.setName(new_name);
        assertEquals(bodyPhoto.getName(),new_name);
    }

    @Test
    public void setPinLocation() {
        int new_pinXLocation = 10;
        int new_pinYLocation = 11;
        bodyPhoto.setPinLocation(new_pinXLocation,new_pinYLocation);
        assertEquals(bodyPhoto.getPinXLocation(),new_pinXLocation);
        assertEquals(bodyPhoto.getPinYLocation(),new_pinYLocation);
    }

    @Test
    public void getPinXLocation() {
        int new_pinXLocation = 10;
        int new_pinYLocation = 11;
        bodyPhoto.setPinLocation(new_pinXLocation,new_pinYLocation);
        assertEquals(bodyPhoto.getPinXLocation(),new_pinXLocation);

    }

    @Test
    public void getPinYLocation() {
        int new_pinXLocation = 10;
        int new_pinYLocation = 11;
        bodyPhoto.setPinLocation(new_pinXLocation,new_pinYLocation);
        assertEquals(bodyPhoto.getPinYLocation(),new_pinYLocation);
    }

    @Test
    public  void removePin() {
        int new_pinXLocation = 10;
        int new_pinYLocation = 11;
        bodyPhoto.setPinLocation(new_pinXLocation,new_pinYLocation);
        bodyPhoto.removePin();
        assertEquals(bodyPhoto.getPinXLocation(),0);
        assertEquals(bodyPhoto.getPinYLocation(),0);
    }
    

}
