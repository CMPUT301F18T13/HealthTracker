package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.CareProvider;
import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PatientTest {
    private Patient patient;
    private Problem problem;
    private Problem problem2;
    private ArrayList<Problem> problemList;

    @Before
    public void setUp() {
        this.patient = new Patient("7801234567", "abc@gmail.com", "abc", "CA15A");
        this.problem = new Problem("New Problem 1", new java.util.Date(( new java.util.Date()).getTime()), "This is a new problem1");
        this.problem2 = new Problem("New Problem 2", new java.util.Date(( new java.util.Date()).getTime()), "This is a new problem2");
        problemList = new ArrayList<Problem> ();
        problemList.add(problem);
        this.patient.addProblem(this.problem);
    }

    @Test
    public void getProblemList() {
        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void addProblem() {
        problemList.add(problem2);
        patient.addProblem(problem2);

        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void deleteProblem() {
        problemList.add(problem2);
        patient.addProblem(problem2);

        problemList.remove(0);
        patient.deleteProblem(problem);

        assertArrayEquals(patient.getProblemList().toArray(), problemList.toArray());
    }

    @Test
    public void noProblemsExist() {
        patient.deleteProblem(problem);

        assertEquals(true, patient.noProblemsExist());
    }

    @Test
    public void getProblem() {
        assertEquals(patient.getProblem(0), problem);
    }

    @Test
    public void addToCareProviderString() {
        patient.addToCareProviderString(new CareProvider("780-268-1234", "test@gmail.com", "Care Provider 1", "CKAA2"));
        assertEquals(patient.getCareProviderString(), "Care Provider 1");

        patient.addToCareProviderString(new CareProvider("780-123-1234", "test2@gmail,com", "Care Provider 2", "CKAAQ"));
        assertEquals(patient.getCareProviderString(), "Care Provider 1 | Care Provider 2");
    }

    @Test
    public void getCareProviderString() {
        assertEquals(patient.getCareProviderString(), "");

        patient.addToCareProviderString(new CareProvider("", "", "Care Provider 1", "CKAA2"));
        assertEquals(patient.getCareProviderString(), "Care Provider 1");
    }
}