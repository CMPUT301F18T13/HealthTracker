package com.example.healthtracker.EntityObjects;

import java.io.Serializable;

import io.searchbox.annotations.JestId;

/**
 * The User superclass class creates user objects and contains all the constructors, getters, and setters to modify user
 * objects and promote data access within both CareProvider and Patient subclasses
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018-10-20
 */
public class User implements Serializable {
    private String email;
    private String phone;
    @JestId
    private String userID;
    private String code;

    /**
     * a constructor for the user that takes several parameters that detail the user information
     *
     * @param phone  the phone number provided by the user which is associated with their account
     * @param email  the email address provided by the user which is associated with their account
     * @param userID the userID generated for the user which is associated with their account
     * @param code the code generated for the user which is associated with their specific account
     */
    User(String phone, String email, String userID, String code) {
        this.email = email;
        this.phone = phone;
        this.userID = userID;
        this.code = code;
    }

    /**
     * Concatenates the users contact information into a single string
     *
     * @return a concatenated string of the user's information
     */
    @Override
    public String toString() {
        return "userId: "+userID+"|email: "+email+"|phone: "+phone+"|code: "+code;
    }

    /**
     * user constructor that requires no parameters
     *
     */
    public User(){
    }

    /**
     * gets the user's account code and returns it
     *
     * @return account code
     */
    public String getCode(){
        return this.code;
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
     * Update the user's contact information.
     *
     * @param phone A string that will be the user's new listed phone number.
     * @param email A string that will be the user's new listed email address.

     */
    public void updateUserInfo(String phone, String email){
        this.setEmail(email);
        this.setPhone(phone);
    }
}