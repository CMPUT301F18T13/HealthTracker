package com.example.healthtracker;

import com.example.healthtracker.Contollers.NotificationsController;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class NotificationsControllerTest {
    private String repeatType;
    private Time time;
    private Date startDate;
    private NotificationsController n;

    @Before
    public void setUp() {
        java.util.Date ud = new java.util.Date();
        startDate = new Date(ud.getTime());
        java.util.Date ud_time = new java.util.Date();
        time = new Time(ud_time.getTime());
        repeatType = "week";
        Boolean isOn = true;
        Problem parentProblem = new Problem();
       // n = new NotificationsController(startDate, time, repeatType);

    }

    @Test
    public void getStartDate() {
        Date testDate = n.getStartDate();
        assertEquals(startDate, testDate);
    }

    @Test
    public void getTime() {
        Time testTime = n.getTime();
        assertEquals(time, testTime);
    }

    @Test
    public void getRepeatType() {
        String testRepeat = n.getRepeatType();
        assertEquals(repeatType, testRepeat);
    }

    @Test
    public void setStartDate() {
        java.util.Date ud = new java.util.Date();
        Date newDate = new Date(ud.getTime());

        n.setStartDate(newDate);
        assertEquals(newDate, n.getStartDate());
    }

    @Test
    public void setTime() {
        java.util.Date ud_time = new java.util.Date();
        Time newTime = new Time(ud_time.getTime());

        n.setTime(newTime);
        assertEquals(newTime, n.getTime());
    }

    @Test
    public void setRepeatType() {
        String newType = "Daily";

        n.setRepeatType(newType);
        assertEquals(newType, n.getRepeatType());
    }

    @Test
    public void turnOff() {
        n.turnOff();

        assertEquals(false, n.notificationStatus());
    }

    @Test
    public void turnOn() {
        n.turnOn();

        assertEquals(true, n.notificationStatus());
    }

    @Test
    public void notificationStatus() {
        n.turnOn();
        assertEquals(true, n.notificationStatus());

        n.turnOff();
        assertEquals(false, n.notificationStatus());
    }


}
