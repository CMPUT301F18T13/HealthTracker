/* package com.example.healthtracker;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class CaretakerTest {
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
    String userID3;
    String userName3;
    String password3;
    String email3;
    String phone3;
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
        userName2 = "Sara";
        userID2 = "001";
        password2 = "password123";
        phone2 = "780-777-5555";
        email2 = "sara@hotmail.ca";
        title = "Bruise";
        date = new Date();
        description = "Painful bruise on my leg.";
        title2 = "Rash";
        date2 = new Date();
        description2 = "Uncomfortable rash on my arm.";
        userName3 = "Dr. Dave";
        userID3 = "003";
        password3 = "Dave123";
        phone3 = "780-777-777";
        email3 = "Dave@ualberta.ca";
    }
    @Test
    public void createAccountTest(){
        Caretaker c = new Caretaker(userID3, password3, phone3, email3, userName3);
        assertEquals(userID, c.getUserID());
        assertEquals(password, c.getPassword());
        assertEquals(phone, c.getPhone());
        assertEquals(email, c.getEmail());
    }

    @Test
    public void editProfileTest(){
        Caretaker c = new Caretaker(userID3, password3, phone3, email3, userName3);
        c.setEmail(email);
        c.setPassword(password);
        c.setPhone(phone);
        c.setUserID(userID);
        assertEquals(userID, c.getUserID());
        assertEquals(password, c.getPassword());
        assertEquals(phone, c.getPhone());
        assertEquals(email, c.getEmail());
    }

    @Test
    public void addPatient_GetPatients(){
        Caretaker c = new Caretaker(userID3, password3, phone3, email3, userName3);
        Patient p = new Patient(userID2, password2, phone2, email2, userName2);
        assertEquals(c.getPatientList().size(), 0);
        c.addPatient(p);
        assertEquals(c.getPatientList().size(), 1);
        assertEquals(c.getPatientList().get(0), p);
    }

    @Test
    public void testCaretakerMap(){
        Caretaker c = new Caretaker(userID3, password3, phone3, email3, userName3);
        Patient patient1 = new Patient(userID2, password2, phone2, email2, userName2);
        Problem p1 = new Problem(title, date, description);
        patient1.addProblem(p1);
        Patient patient2 = new Patient(userID3, password3, phone3, email3, userName3);
        Problem p2 = new Problem(title2, date2, description2);
        patient2.addProblem(p2);
        PatientRecord r1 = new PatientRecord("Day1", "Bruise is small", p1, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        PatientRecord r2 = new PatientRecord("Day1", "Minor rash", p2, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        p1.addPatientRecord(r1);
        p2.addPatientRecord(r2);
        Bitmap data = c.createMap();
        assertNotNull(data);
    }

    @Test
    public void testCaretakerSearch(){
        Caretaker c = new Caretaker(userID3, password3, phone3, email3, userName3);
        Patient patient1 = new Patient(userID, password, phone, email, userName);
        Patient patient2 = new Patient(userID2, password2, phone2, email2, userName2);
        Problem p1 = new Problem(title, date, description);
        Problem p2 = new Problem(title2, date2, description2);
        patient2.addProblem(p2);
        List<Problem> filteredProblems = c.search("Bruise", "keyword");
        assertNotNull(filteredProblems);
        assertEquals(filteredProblems.size(), 1);
        assertEquals(filteredProblems.get(0), p1);
    }


}*/
