package com.example.healthtracker;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

import io.searchbox.annotations.JestId;

/**
 * The User superclass class creates user objects and contains all the constructors, getters, and setters to modify user
 * objects and promote data access within both CareProvider and Patient subclasses
 *
 * @author
 * @version 1.0
 * @since 2018-10-20
 */
public class User implements Serializable {
    private String email;
    private String phone;
    @JestId
    private String userID;

    /**
     * a constructor for the user that takes several parameters that detail the user information
     *
     * @param phone  the phone number provided by the user which is associated with their account
     * @param email  the email address provided by the user which is associated with their account
     * @param userID the userID generated for the user which is associated with their account
     */
    public User(String phone, String email, String userID) {
        this.email = email;
        this.phone = phone;
        this.userID = userID;
    }


    @Override
    public String toString() {
        return "userId: "+userID+"|email: "+email+"|phone: "+phone;
    }

    /**
     * user singleton
     */
    public User(){

    }

    /**
     * gets the userID and returns it
     *
     * @return returns the userID
     */
    public String getUserID(){
        return this.userID;
    }

    /**
     * sets the users ID using the string provided by the user
     *
     * @param userID is generated automatically and set it for the user
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * gets the users email and returns it
     *
     * @return returns the userEmail
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * sets the users email address using the email address string provided by the user
     *
     * @param email takes a provided email address and sets it as a user's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * gets the users phone number and returns it
     *
     * @return returns the users phone number
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * sets the users phone number using the string provided by the user
     *
     * @param phone takes a provided phone number and sets it for the user
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *  TODO
     *  Is this even used?
     * @param phone phone number
     * @param email email address

     */
    public void updateUserInfo(String phone, String email){
        this.setEmail(email);
        this.setPhone(phone);
    }

    /**
     * TODO
     *
     * @param keyword keyword associated with the problem for searching?
     * @param type type of the problem?
     * @return returns null
     */
    public List<Problem> search(String keyword, String type){
        //TODO implement
        return null;
    }


    /**
     * TODO
     *
     * @return returns null
     */
    public Bitmap createMap(){
        //TODO implement
        return null;
    }
}