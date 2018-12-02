package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by caochenlin on 2018/10/28.
 */

public class ProblemTest {
    private Problem problem;
    private String dateStarted;
    private String description;

    @Before
    public void setUp() {
        // Create an instance of the Problem class
        String title = "Rash";
        dateStarted = "2018-07-06";
        description = "A lot of red spots on my skin.";
        problem = new Problem(title,dateStarted,description);
    }


    @Test
    public void getTitle() {
        assertEquals(problem.getTitle(),"Rash");
    }

    @Test
    public void getDate() {
        assertEquals(problem.getDate(),dateStarted);
    }

    @Test
    public void getDescription() {
        assertEquals(problem.getDescription(),description);
    }

    @Test
    public void setTitle() {
        problem.setTitle("Big Rash");
        assertEquals(problem.getTitle(),"Big Rash");
    }

    @Test
    public void setDate() {
        String date = "2016-12-01";
        problem.setDate(date);
        assertEquals(problem.getDate(),date);
    }

    @Test
    public void setDescription() {
        problem.setDescription("Red spots are gradually diminishing");
        assertEquals(problem.getDescription(),"Red spots are gradually diminishing");
    }

    @Test
    public void update() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = "2018-08-07";
        problem.update("Big Rash",dateString, "Red spots are gradually diminishing");
        assertEquals(problem.getTitle(),"Big Rash");
        assertEquals(problem.getDate(),dateString);
        assertEquals(problem.getDescription(),"Red spots are gradually diminishing");
    }

    @Test
    public void setRecords() {
        ArrayList<PatientRecord> patientRecords = new ArrayList<>();
        patientRecords.add(new PatientRecord("Record", "I'm a record"));
        problem.setRecords(patientRecords);

        assertArrayEquals(patientRecords.toArray(), problem.getRecords().toArray());
    }

    public void getRecords() {
        ArrayList<PatientRecord> patientRecords = new ArrayList<>();

        assertArrayEquals(patientRecords.toArray(), problem.getRecords().toArray());
    }

    @Test
    public void countRecords() {
        assertEquals(0, (int) problem.countRecords());

        ArrayList<PatientRecord> patientRecords = new ArrayList<>();
        patientRecords.add(new PatientRecord("Record", "I'm a record"));
        problem.setRecords(patientRecords);

        assertEquals(1, (int) problem.countRecords());
    }

    @Test
    public void setCaregiverRecords() {
        ArrayList<CareProviderComment> careGiverRecords = new ArrayList<>();
        careGiverRecords.add(new CareProviderComment("Record", "I'm a record"));
        problem.setCaregiverRecords(careGiverRecords);

        assertArrayEquals(careGiverRecords.toArray(), problem.getcaregiverRecords().toArray());
    }

    @Test
    public void getCaregiverRecords() {
        ArrayList<CareProviderComment> careGiverRecords = new ArrayList<>();

        assertArrayEquals(careGiverRecords.toArray(), problem.getcaregiverRecords().toArray());
    }

    @Test
    public void getPatientRecords() {

    }


    @Test
    public void addPatientRecord() {
    }

    @Test
    public void addCareGiverRecord() {
    }


    @Test
    public void deleteRecord() {
    }

    @Test
    public void getNotifications() {
        /*Notification myNotification = new Notification();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        notifications.add(myNotification);
        problem.addNotification(myNotification);
        assertEquals(notifications,problem.getNotifications()); */
    }

    @Test
    public void addNotification() {
        /*Notification myNotification = new Notification();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        notifications.add(myNotification);
        problem.addNotification(myNotification);
        assertEquals(notifications,problem.getNotifications()); */
    }

    @Test
    public void deleteNotification() {

    }

    @Test
    public void switchNotification() {
        /* Boolean myNotification = problem.notificationStatus();
        switchNotification();
        Boolean myNotification2 = problem.notificationStatus();
        assertFalse(myNotification == myNotification2); */
    }

    @Test
    public void notificationStatus() {

    }


    @Test
    public void getPatient() {
        /* Patient patient = new Patient("P001","P001","123-456-789","patient1@health.com", "Sara");
        problem.setPatient(patient);
        assertEquals(problem.getPatient(),patient); */
    }

    @Test
    public void setPatient() {
       /* Patient newPatient = new Patient("P002","P002","111-222-333","patient2@health.com", "Nick");
        problem.setPatient(newPatient);
        assertEquals(problem.getPatient(),newPatient); */
    }

}