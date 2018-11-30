package com.example.healthtracker.EntityObjects;

import android.support.annotation.NonNull;

import com.example.healthtracker.Contollers.NotificationsController;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Boolean.FALSE;

/**
 * The Problem object represents a patient's medical problem. A patient can enter the details of the
 * Problem and add records to show its progression. CareProviders and Patients will view these problems.
 *
 *  @author Michael Boisvert
 *  @version 1.0
 *  @since 2018-10-28
 */
public class Problem implements Serializable {

    private String ProblemTitle;
    private String dateStarted;
    private String description;
    // if we use the android built in notifications then they cant be here or elastic search will break
    private List<NotificationsController> notifications;
    private Boolean notificationsOn = FALSE;
    private ArrayList<PatientRecord> patientRecords = new ArrayList<PatientRecord>();
    private ArrayList<CareProviderComment> caregiverRecords = new ArrayList<>();

    /**
     * An empty constructor for Problem that requires no parameters.
     */
    public Problem(){

    }

    /**
     * A constructor for Problem that sets the title, date, and description of the Problem.
     *
     * @param title A string that will be the title of the Problem.
     * @param dateStarted A Date that will indicate when the Problem started.
     * @param description A string that will be the description of the Problem.
     */
    public Problem(String title,String dateStarted,String description){
        this.ProblemTitle = title;
        this.dateStarted = dateStarted;
        this.description = description;
    }

    /**
     * Get the title of the problem.
     *
     * @return A string that is the title of the problem.
     */
    public String getTitle(){
        return ProblemTitle;
    }

    /**
     * Get the date the problem started.
     *
     * @return A Date indicating when the problem started.
     */
    public String getDate(){
        return dateStarted;
    }

    /**
     * Get the description of the Problem.
     *
     * @return A string that is the description of the problem.
     */
    public String getDescription(){
        return description;
    }

    /**
     * Set a new title for the Problem.
     *
     * @param newTitle A string that will be the new title of the Problem.
     */
    public void setTitle(String newTitle){
        this.ProblemTitle = newTitle;
    }

    /**
     * Set a new date for the Problem.
     *
     * @param newDate The new Date that will indicate when the Problem began.
     */
    public void setDate(String newDate){
        this.dateStarted = newDate;
    }

    /**
     * Set a new description for the Problem.
     *
     * @param newDescription A string that will be the new description of the problem.
     */
    public void setDescription(String newDescription){
        this.description = newDescription;
    }

    /**
     * Update the basic details of the problem.
     *
     * @param title A new title for the problem.
     * @param date A new start date for the problem.
     * @param description A new description for the problem.
     */
    public void update(String title, String date, String description){
        setDate(date);
        setTitle(title);
        setDescription(description);
    }

    /**
     * Set a new list of record for the Problem.
     *
     * @param records The new ArrayList of PatientRecords for the problem.
     */
    public void setRecords(ArrayList<PatientRecord> records){
        this.patientRecords = records;
    }

    /**
     * Get the list of PatientRecords for the problem.
     *
     * @return An ArrayList of PatientRecords.
     */
    public ArrayList<PatientRecord> getRecords(){
        return this.patientRecords;
    }

    /**
     * Return the current number of records that the problem has.
     *
     * @return An Integer that represents how many records
     */
    public Integer countRecords(){
        return patientRecords.size();
    }

    /**
     * Set the list of comment records left by CareProviders on the Problem.
     *
     * @param caregiverRecords The new list of CareProvider comment records.
     */
    public void setCaregiverRecords(ArrayList<CareProviderComment> caregiverRecords){
        this.caregiverRecords = caregiverRecords;
    }

    /**
     * Get the list of comment records left by CareProviders.
     *
     * @return An ArrayList of CareProvderComments.
     */
    public ArrayList<CareProviderComment> getcaregiverRecords(){
        return this.caregiverRecords;
    }

    /**
     * Get a specific one of the PatientRecords belonging to the Problem.
     *
     * @param Index The index of the desired PatientRecord in the patientRecords list.
     * @return The PatienRecord corresponding to the Index input.
     */
    public PatientRecord getPatientRecord(int Index){
        return patientRecords.get(Index);
    }

    /**
     * Override the toString method to set what a string representation of a problem is.
     *
     * @return A string represneting the Problem.
     */
    @NonNull
    @Override
    public String toString(){
        return "title: "+ProblemTitle+"\n"+"start date: "+dateStarted+"\n"+"description: "+
                description+"\n"+"number of records: "+String.valueOf(patientRecords.size() + caregiverRecords.size());
    }
}
