package com.example.healthtracker;

import android.graphics.Bitmap;

import com.example.healthtracker.Contollers.UserDataController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class CareProviderTest {
    private String phone2;
    private String email2;
    private String userName3;
    private String email3;
    private String phone3;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

    @Before
    public void setUp() {

        String userName = "Nick";
        String userID = "001";
        String password = "nickB";
        String phone = "780-777-2342";
        String email = "nick@aol.ca";
        String userName2 = "Sara";
        String userID2 = "001";
        String password2 = "password123";
        phone2 = "780-777-5555";
        email2 = "sara@hotmail.ca";
        String title = "Bruise";
        Date date = new Date();
        String description = "Painful bruise on my leg.";
        String title2 = "Rash";
        Date date2 = new Date();
        String description2 = "Uncomfortable rash on my arm.";
        userName3 = "Dr. Dave";
        String userID3 = "003";
        String password3 = "Dave123";
        phone3 = "780-777-777";
        email3 = "Dave@ualberta.ca";
        String code1 = "CKAC9";
        String code2 = "CKAC1";
        String code3 = "CKAC2";
        patient1 = new Patient(phone, email, userName, code1);
        patient2 = new Patient(phone2, email2, userName2, code2);
        patient3 = new Patient(phone3, email3, userName3, code3);
    }
    @Test
    public void addPatient(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");

        c.addPatient(patient2);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<>();

        arrayToTestAgainst.add(patient2);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());

        assertEquals(c.getPatient(0), patient2);
    }

    @Test
    public void getPatientList() {
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");

        c.addPatient(patient2);
        c.addPatient(patient1);
        c.addPatient(patient3);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<>();

        arrayToTestAgainst.add(patient2);
        arrayToTestAgainst.add(patient1);
        arrayToTestAgainst.add(patient3);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());

    }

    @Test
    public void setPatient(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");

        c.addPatient(patient2);
        c.addPatient(patient1);
        c.addPatient(patient3);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<>();

        arrayToTestAgainst.add(patient2);
        arrayToTestAgainst.add(patient3);
        arrayToTestAgainst.add(patient3);

        c.setPatient(patient3, 1);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());
    }

    @Test
    public void setPatientList(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");
        ArrayList<Patient> arrayToTestAgainst = new ArrayList<>();
        arrayToTestAgainst.add(patient2);
        arrayToTestAgainst.add(patient3);
        arrayToTestAgainst.add(patient3);
        c.setPatientList(arrayToTestAgainst);
        assertArrayEquals(c.getPatientList().toArray(),arrayToTestAgainst.toArray());

    }


    @Test
    public void updateUserInfo(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");
        c.updateUserInfo(phone2, email2);

        assertEquals(c.getPhone(), phone2);
        assertEquals(c.getEmail(), email2);
    }

    @Test
    public void createAccountTest(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");
        assertEquals(userName3, c.getUserID());
        //assertEquals(password, c.getPassword());
        assertEquals(phone3, c.getPhone());
        assertEquals(email3, c.getEmail());
    }


    @Test
    public void testCaretakerMap(){
        CareProvider c = new CareProvider(phone3, email3, userName3, "CKAC2");
        Bitmap data = c.createMap();
        Assert.assertNull(data);
    }


}
