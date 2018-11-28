package com.example.healthtracker;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class CareProviderTest {
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
    Patient patient1;
    Patient patient2;
    Patient patient3;

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
        patient1 = new Patient(phone, email, userName, "XXXX");
        patient2 = new Patient(phone2, email2, userName2, "AAAA");
        patient3 = new Patient(phone3, email3, userName3, "BBBB");
    }
    @Test
    public void addPatient(){
        CareProvider c = new CareProvider(phone3, email3, userName3);

        c.addPatient(patient2);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<Patient>();

        arrayToTestAgainst.add(patient2);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());

        assertEquals(c.getPatient(0), patient2);
    }

    @Test
    public void getPatientList() {
        CareProvider c = new CareProvider(phone3, email3, userName3);

        c.addPatient(patient2);
        c.addPatient(patient1);
        c.addPatient(patient3);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<Patient>();

        arrayToTestAgainst.add(patient2);
        arrayToTestAgainst.add(patient1);
        arrayToTestAgainst.add(patient3);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());

    }

    @Test
    public void setPatient(){
        CareProvider c = new CareProvider(phone3, email3, userName3);

        c.addPatient(patient2);
        c.addPatient(patient1);
        c.addPatient(patient3);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<Patient>();

        arrayToTestAgainst.add(patient2);
        arrayToTestAgainst.add(patient3);
        arrayToTestAgainst.add(patient3);

        c.setPatient(patient3, 1);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());
    }

    @Test
    public void updateUserInfo(){
        CareProvider c = new CareProvider(phone3, email3, userName3);
        c.updateUserInfo(phone2, email2);

        assertEquals(c.getPhone(), phone2);
        assertEquals(c.getEmail(), email2);
    }

    @Test
    public void createAccountTest(){
        CareProvider c = new CareProvider(phone3, email3, userName3);
        assertEquals(userName3, c.getUserID());
        //assertEquals(password, c.getPassword());
        assertEquals(phone3, c.getPhone());
        assertEquals(email3, c.getEmail());
    }


    @Test
    public void testCaretakerMap(){
        CareProvider c = new CareProvider(phone3, email3, userName3);
        Bitmap data = c.createMap(patient1);
        assertFalse(data == null);
    }

    @Test
    public void testCaretakerSearch(){
        CareProvider c = new CareProvider(phone3, email3, userID3);
        Patient patient1 = new Patient(phone, email, userID, "BBBB");
        Patient patient2 = new Patient(phone2, email2, userID2, "XXXX");
        Problem p1 = new Problem(title, date, description);
        Problem p2 = new Problem(title2, date2, description2);
        patient2.addProblem(p2);
        List<Problem> filteredProblems = UserDataController.searchForProblem("problemList", "Bruise");
        assertNotNull(filteredProblems);
        assertEquals(filteredProblems.size(), 1);
        assertEquals(filteredProblems.get(0), p1);
    }

}
