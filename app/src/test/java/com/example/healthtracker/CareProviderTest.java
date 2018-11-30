<<<<<<< HEAD
package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


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
        patient1 = new Patient(phone, email, userName, password);
        patient2 = new Patient(phone2, email2, userName2, password2);
        patient3 = new Patient(phone3, email3, userName3, password3);
    }
    @Test
    public void addPatient(){
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);

        c.addPatient(patient2);

        ArrayList<Patient> arrayToTestAgainst = new ArrayList<Patient>();

        arrayToTestAgainst.add(patient2);

        assertArrayEquals(c.getPatientList().toArray(), arrayToTestAgainst.toArray());
    }

    @Test
    public void getPatientList() {
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);

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
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);

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
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);
        c.updateUserInfo(phone2, email2, userName2);

        assertEquals(c.getPhone(), phone2);
        assertEquals(c.getEmail(), email2);
        assertEquals(c.getUserID(), userName2);
    }

    @Test
    public void createAccountTest(){
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);
        assertEquals(userName3, c.getUserID());
        //assertEquals(password, c.getPassword());
        assertEquals(phone3, c.getPhone());
        assertEquals(email3, c.getEmail());
    }
/*
    @Test
    public void editProfileTest(){
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);
        c.setEmail(email);
        //c.setPassword(password);
        c.setPhone(phone);
        c.setUserID(userID);
        assertEquals(userID, c.getUserID());
        //assertEquals(password, c.getPassword());
        assertEquals(phone, c.getPhone());
        assertEquals(email, c.getEmail());
    }

    @Test
    public void addPatient_GetPatients(){
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);
        //Patient p = new Patient(userID2, password2, phone2, email2, userName2);
        assertEquals(c.getPatientList().size(), 0);
        c.addPatient(p);
        assertEquals(c.getPatientList().size(), 1);
        assertEquals(c.getPatientList().get(0), p);
    }

    @Test
    public void testCaretakerMap(){
        CareProvider c = new CareProvider(phone3, email3, userName3, password3);
        Patient patient1 = new Patient(phone2, email2, userName2, password2);
        Problem p1 = new Problem(title, date, description);
        patient1.addProblem(p1);
        Patient patient2 = new Patient(phone3, email3, userName3, password3);
        Problem p2 = new Problem(title2, date2, description2);
        patient2.addProblem(p2);
        //PatientRecord r1 = new PatientRecord("Day1", "Bruise is small", p1, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        //PatientRecord r2 = new PatientRecord("Day1", "Minor rash", p2, new Timestamp(date.getTime()).toString(), new ArrayList<Bitmap>());
        //p1.addPatientRecord(r1);
        //p2.addPatientRecord(r2);
        Bitmap data = c.createMap();
        assertNotNull(data);
    }

    @Test
    public void testCaretakerSearch(){
        //CareProvider c = new CareProvider(userID3, password3, phone3, email3, userName3);
        //Patient patient1 = new Patient(userID, password, phone, email, userName);
        //Patient patient2 = new Patient(userID2, password2, phone2, email2, userName2);
        Problem p1 = new Problem(title, date, description);
        Problem p2 = new Problem(title2, date2, description2);
        //patient2.addProblem(p2);
        //List<Problem> filteredProblems = c.search("Bruise", "keyword");
        //assertNotNull(filteredProblems);
        //assertEquals(filteredProblems.size(), 1);
        //assertEquals(filteredProblems.get(0), p1);
    }

*/
}
=======
package com.example.healthtracker;

import android.graphics.Bitmap;

import com.example.healthtracker.Contollers.ElasticsearchController;
import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class CareProviderTest {
    private String userID;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String userID2;
    private String userName2;
    private String password2;
    private String phone2;
    private String email2;
    private String userID3;
    private String userName3;
    private String password3;
    private String email3;
    private String phone3;
    private String title;
    private Date date;
    private String description;
    private String title2;
    private Date date2;
    private String description2;
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;

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
        patient1 = new Patient(phone, email, userName);
        patient2 = new Patient(phone2, email2, userName2);
        patient3 = new Patient(phone3, email3, userName3);
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
        Patient patient1 = new Patient(phone, email, userID);
        Patient patient2 = new Patient(phone2, email2, userID2);
        Problem p1 = new Problem(title, date, description);
        Problem p2 = new Problem(title2, date2, description2);
        patient2.addProblem(p2);
        List<Problem> filteredProblems = ElasticsearchController.search("Bruise", "keyword");
        assertNotNull(filteredProblems);
        assertEquals(filteredProblems.size(), 1);
        assertEquals(filteredProblems.get(0), p1);
    }

}
>>>>>>> tyler
