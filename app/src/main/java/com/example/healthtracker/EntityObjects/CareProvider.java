package com.example.healthtracker.EntityObjects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The CareProvider class creates CareProvider objects/users and their data
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-10-20
 */
public class CareProvider extends User implements Serializable {

    private ArrayList<Patient> patientList;


    /**
     * constructor for creating a new CareProvider user and their appropriate profile information designated by parameter values
     *
     *
     */
    public CareProvider() {
        super();
        this.patientList = new ArrayList<>();
    }

    /**
     * gets a list object of the current CareProvider's assigned patients
     *
     * @return returns the CareProviders patient list
     */
    public ArrayList<Patient> getPatientList() {
        return this.patientList;
    }

    /**
     * add's a patient to the current CareProvider's assigned patient list
     *
     * @param newPatient the newpatient to be added to the CareProviders patient list
     */
    public void addPatient(Patient newPatient){
        this.patientList.add(newPatient);
    }

    /**
     * Update one of the CareProvider's patients.
     *
     * @param updatedPatient Patient to replace previous Patient object
     * @param patientIndex The index of the Patient to be replace in the PatientList
     */
    public void setPatient(Patient updatedPatient, int patientIndex){
        patientList.set(patientIndex, updatedPatient);
    }

    /**
     * Update the CareProvider's entire PatientList with a new list.
     *
     * @param patientList The new Patient list.
     */
    public void setPatientList(ArrayList<Patient> patientList){
        this.patientList = patientList;
    }

    /**
     * Get a specific one of the CareProvider's patients.
     *
     * @param Index The index of the patient to return in the PatientList.
     * @return The patient corresponding to the Index input.
     */
    public Patient getPatient(int Index){
        return patientList.get(Index);
    }

    /**
     * Create map visual of all the geolocation records for a patient.
     *
     * @return byte code of map visual.
     */
    public Bitmap createMap(){
        return null;
    }
    @NonNull
    @Override
    public String toString() {
        if(getUserID() == null) {
            return "";
        }
        return "  ID: " + getUserID() + "\n    phone: " + getPhone() + "\n    email: " + getEmail();
    }
}
