package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PatientTest {
    private Patient patient;
    private Problem problem;
    private Problem problem2;
    private ArrayList<Problem> problemList;

    @Before
    public void setUp() {
        this.patient = new Patient("7801234567", "abc@gmail.com", "abc", "CA15A");
        this.problem = new Problem("title","2018-07-12","descripton");
        this.problem2 = new Problem("title2","2018-07-13","descripton2");
        problemList = new ArrayList<>();
        problemList.add(problem);
        this.patient.addProblem(this.problem);
    }

    @Test
    public void getProblemList() {
        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void setProblem(){
        Problem problem2 = new Problem ("title","2018-07-12","descripton");
        problemList.set(0,problem2);
        assertEquals(problemList.get(0),problem2);
    }

    @Test
    public void setProblems(){
        ArrayList<Problem> problemList2 = new ArrayList<>();
        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void addProblem() {
        problemList.add(problem);
        patient.addProblem(problem);
        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void deleteProblem() {
        problemList.add(problem2);
        patient.addProblem(problem2);

        problemList.remove(1);
        patient.deleteProblem(problem2);
        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void noProblemsExist() {
        patient.deleteProblem(problem);
        assertTrue(patient.noProblemsExist());
    }

    @Test
    public void getProblem() {
        assertEquals(patient.getProblem(0), problem);
    }

    @Test
    public void addToCareProviderString() {
        patient.addToCareProviderString(new CareProvider("780-268-1234", "test@gmail.com", "Care Provider 1", "CKAA2"));
        assertEquals(patient.getCareProviderString(), "  ID: Care Provider 1\n" +
                "    phone: 780-268-1234\n" +
                "    email: test@gmail.com");

        patient.addToCareProviderString(new CareProvider("780-123-1234", "test2@gmail,com", "Care Provider 2", "CKAAQ"));
        assertEquals(patient.getCareProviderString(), "  ID: Care Provider 1\n" +
                "    phone: 780-268-1234\n" +
                "    email: test@gmail.com\n" +
                "  ID: Care Provider 2\n" +
                "    phone: 780-123-1234\n" +
                "    email: test2@gmail,com");
    }

    @Test
    public void getCareProviderString() {
        assertEquals(patient.getCareProviderString(), "");

        patient.addToCareProviderString(new CareProvider("", "", "Care Provider 1", "CKAA2"));
        assertEquals(patient.getCareProviderString(), "  ID: Care Provider 1\n" +
                "    phone: \n" +
                "    email: ");
    }
}
