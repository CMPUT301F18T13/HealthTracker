package com.example.healthtracker;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CareProviderDataManagerTest {

    CareProvider c;
    CareProviderDataManager cd;
    private Context context;

    @Before
    public void setUp() {
        this.c = new CareProvider("7801234567", "abc@gmail.com", "c123", "c123");
    }

    @Test
    public void saveCareProviderLocally() {
        this.context = getApplicationContext();
        this.cd = new CareProviderDataManager(context);

        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        cd.saveCareProviderLocally(c);
        CareProvider loaded = cd.loadCareProviderLocally();

        assertEquals(c.getPhone(), loaded.getPhone());
        assertEquals(c.getEmail(), loaded.getEmail());
        assertEquals(c.getUserID(), loaded.getUserID());
    }

}
