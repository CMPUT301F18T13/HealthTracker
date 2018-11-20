package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CareProviderProblemViewTest {
    CareProviderProblemView c;

    @Before
    public void setup() {
        c = new CareProviderProblemView();

    }

    @Test
    public void dateToString() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String dateString = format.format(date);
        assertEquals(c.dateToString(date), dateString);

    }


}
