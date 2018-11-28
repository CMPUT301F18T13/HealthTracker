package com.example.healthtracker;

import android.content.Context;

import java.sql.Date;
import java.sql.Time;

/**
 * NotificationController handles alerting a Patient of when they should add another record to
 * one of their problems based on the notifications added to problems by the Patient.
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-10-20
 */
public class NotificationsController {
    private Context context;
    private String ID;
    private Boolean isOn;
    private String repeatType;
    private Time time;
    private Date startDate;

    /**
     * Constructor for NotificationController that establishes the frequency and time of the alert.
     *
     * @param startDate When the repeating notification will begin.
     * @param time When the user will be alerted.
     * @param repeatType How often the user will be alerted.
     */
    public NotificationsController(Date startDate, Time time, String repeatType, String ID){
        this.startDate = startDate;
        this.time = time;
        this.repeatType = repeatType;
        this.ID = ID;
    }

    /**
     * Get the startDate of the notification.
     *
     * @return A date indicating when the repeating notification will start
     */
    public Date getStartDate(){
        return this.startDate;
    }

    /**
     * Get the time that the notification is inteded to alert the patient.
     *
     * @return A Time indicating what time of day the notification will send an alert to the patient's
     * device.
     */
    public Time getTime(){
        return this.time;
    }

    /**
     * Get the type of repeating frequency associated with the notification.
     *
     * @return A string indicating how frequently the notification alerts the patient
     */
    public String getRepeatType(){
        return this.repeatType;
    }

    /**
     * Set the start date of the notification
     *
     * @param newDate The date that the notification should begin sending the patient alerts.
     */
    public void setStartDate(Date newDate) {
        this.startDate = newDate;
    }

    /**
     * Set the time at which the patient is notified.
     *
     * @param newTime A Time object indicating what time of day the user is notified.
     */
    public void setTime(Time newTime){
        this.time = newTime;
    }

    /**
     * Set a new repeat type for the notification.
     *
     * @param newRepeatType A string indicating the frequency with which this notification will notify
     *                      the patient.
     */
    public void setRepeatType(String newRepeatType){
        this.repeatType = newRepeatType;
    }

    /**
     * Turn the notification off so that no alerts are sent to the patient.
     */
    public void turnOff(){
        this.isOn = false;
    }

    /**
     * Turn the notification on so that the patient will be notified based on the Time, StartDate, and
     * RepeatType of the notification.
     */
    public void turnOn(){
        this.isOn = true;
    }

    /**
     * Indicates whether the notification has been dismissed.
     *
     * @return a boolean that is true if a notification is up or false after a notification is dismissed.
     */
    public Boolean notificationStatus(){
        return this.isOn;
    }
}
