package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by caochenlin on 2018/11/2.
 */
public class CareProviderDataManagerTest {
    private CareProviderDataManager careProviderDataManager;

    @Before
    public void setUp() throws Exception {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        careProviderDataManager = new CareProviderDataManager(patients);
    }

    @Test
    public void elasticLoadPatientList() throws Exception {
    }

    @Test
    public void localLoadPatientList() throws Exception {
    }

    @Test
    public void savePatientList() throws Exception {
    }

    @Test
    public void elasticSearch() throws Exception {
    }

}