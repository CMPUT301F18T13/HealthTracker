package com.example.healthtracker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class Problem implements Serializable {

    private String ProblemTitle;
    private Date dateStarted;
    private String description;
    // if we use the android built in notifications then they cant be here or elastic search will break
    private List<NotificationsController> notifications;
    private Boolean notificationsOn = FALSE;
    private ArrayList<PatientRecord> patientRecords = new ArrayList<PatientRecord>();
    private ArrayList<CareProviderComment> caregiverRecords = new ArrayList<>();

    public Problem(){

    }

    public Problem(String title,Date dateStarted,String description){
        this.ProblemTitle = title;
        this.dateStarted = dateStarted;
        this.description = description;
    }

    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(dateStarted);
        return "title: "+ProblemTitle+"\n"+"start date: "+dateStr+"\n"+"description: "+
                description+"\n"+"number of records: "+String.valueOf(patientRecords.size());
    }

    public String getTitle(){

        return ProblemTitle;
    }

    public Date getDate(){

        return dateStarted;
    }

    public String getDescription(){

        return description;
    }

    private void setTitle(String newTitle){
        this.ProblemTitle = newTitle;

    }

    private void setDate(Date newDate){
        this.dateStarted = newDate;
    }

    private void setDescription(String newDescription){
        this.description = newDescription;
    }

    public void update(String title, Date date, String description){
        setDate(date);
        setTitle(title);
        setDescription(description);
    }

    public void setRecords(ArrayList<PatientRecord> records){
        this.patientRecords = records;
    }

    public ArrayList<PatientRecord> getRecords(){
        return this.patientRecords;
    }

    public Integer countRecords(){
        return patientRecords.size();
    }

    public void setCaregiverRecords(ArrayList<CareProviderComment> caregiverRecords){
        this.caregiverRecords = caregiverRecords;
    }

    public ArrayList<CareProviderComment> getcaregiverRecords(){
        return this.caregiverRecords;
    }
}
