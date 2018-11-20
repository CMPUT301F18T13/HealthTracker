package com.example.healthtracker;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
/*
public class PatientTest {
    String userID;
    String userName;
    String password;
    String phone;
    String email;
    String userID2;
    String userName2;
    String password2;
    String phone2;
    String email2;
    String title;
    Date date;
    String description;
    String title2;
    Date date2;
    String description2;

    @Before
    public void setUp() {
        userName = "Nick";
        userID = "001";
        password = "nickB";
        phone = "780-777-2342";
        email = "nick@aol.ca";
        userID2 = "002";
        userName2 = "Sara";
        password2 = "password123";
        phone2 = "780-777-5555";
        email2 = "sara@hotmail.ca";
        title = "Bruise";
        date = new Date();
        description = "Painful bruise on my leg.";
        title2 = "Rash";
        date2 = new Date();
        description2 = "Uncomfortable rash on my arm.";
    }

    @Test
    public void createPatientTest(){
        Patient p = new Patient(userID, password, phone, email, userName);
        assertEquals(userID, p.getUserID());
        assertEquals(password, p.getPassword());
        assertEquals(phone, p.getPhone());
        assertEquals(email, p.getEmail());
    }

    @Test
    public void editProfileTest(){
        Patient p = new Patient(userID2, password2, phone2, email2, userName2);
        p.setEmail(email);
        p.setPassword(password);
        p.setPhone(phone);
        p.setUserID(userID);
        assertEquals(userID, p.getUserID());
        assertEquals(password, p.getPassword());
        assertEquals(phone, p.getPhone());
        assertEquals(email, p.getEmail());
    }

    @Test
    public void add_Remove_GetList_ProblemTest(){
        Patient p = new Patient(userID, password, phone, email, userName);
        Problem p1 = new Problem(title, date, description);
        p.addProblem(p1);
        assertEquals(p.getProblemList().get(0), p1);
        assertEquals(p.getProblemList().size(), 1);
        p.deleteProblem(p1);
        assertEquals(p.getProblemList().size(), 0);
    }

    @Test
    public void testPatientSearch(){
        Patient p = new Patient(userID, password, phone, email, userName);
        Problem p1 = new Problem(title, date, description);
        p.addProblem(p1);
        Problem p2 = new Problem(title2, date2, description2);
        p.addProblem(p2);
        List<Problem> filteredProblems = p.search("Bruise", "keyword");
        assertNotNull(filteredProblems);
        assertEquals(filteredProblems.size(), 1);
        assertEquals(filteredProblems.get(0), p1);

    }

    @Test
    public void testPatientMap(){
        Patient p = new Patient(userID, password, phone, email, userName);
        Problem p1 = new Problem(title, date, description);
        p.addProblem(p1);
        Problem p2 = new Problem(title2, date2, description2);
        p.addProblem(p2);
        PatientRecord r1 = new PatientRecord("Day1", "Bruise is small", p1, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        PatientRecord r2 = new PatientRecord("Day1", "Minor rash", p2, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        p1.addPatientRecord(r1);
        p2.addPatientRecord(r2);
        Bitmap data = p.createMap();
        assertNotNull(data);
    }
}
*/