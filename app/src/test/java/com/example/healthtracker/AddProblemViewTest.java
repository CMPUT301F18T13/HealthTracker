package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddProblemViewTest {

    private java.util.Date date;
    private AddProblemView a;

    @Before
    public void setUp() {
        date = new java.util.Date();
        a = new AddProblemView();
    }

    @Test
    public void testDate() {
        assertTrue(a.testDate("2018-11-26") == true);
        assertTrue(a.testDate(date.toString()) == false);
    }
}
