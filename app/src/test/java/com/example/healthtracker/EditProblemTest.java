package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EditProblemTest {

    private java.util.Date date;
    private EditProblem e;

    @Before
    public void setUp() {
        date = new java.util.Date();
        e = new EditProblem();
    }

    @Test
    public void testDate() {
        assertTrue(e.testDate("2018-11-26") == true);
        assertTrue(e.testDate(date.toString()) == false);
    }
}
