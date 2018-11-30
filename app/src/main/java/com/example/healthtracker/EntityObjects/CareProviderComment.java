package com.example.healthtracker.EntityObjects;

import java.io.Serializable;


/**
 * CareProviderComment represents the comment records that careproviders can leave on patient problems.
 * It is useful to have these separated from records added by patients.
 *
 * @author caochenlin
 * @version 1.0
 * @since 2018-10-28
 *
 */
public class CareProviderComment implements Serializable{

    private String messageTitle;
    private String message;

    /**
     * Constructor for CareProvider that sets the title and comment.
     *
     * @param title Title of the comment that careprovider enters
     * @param comment Main body of the comment that the careprovider enters
     */
    public CareProviderComment(String title, String comment){
        this.messageTitle = title;
        this.message = comment;
    }

    /**
     * Constructor for CareProvider that takes no inputs and initializes messageTitle and message as
     * empty strings.
     */
    public CareProviderComment(){
        messageTitle = "";
        message = "";
    }

    /**
     * Set the title of the CareProvider comment
     *
     * @param newTitle input what the new title will be.
     */
    public void setTitle(String newTitle){
        this.messageTitle = newTitle;
    }

    /**
     * Get the current title of the careprovider comment.
     *
     * @return The title of the careprovider comment.
     */
    public String getTitle(){
        return this.messageTitle;
    }

    /**
     * Get the main body of the CareProvider comment.
     *
     * @return A string of the main body of the comment.
     */
    public String getComment(){ //This is different from Jori's UML
        return message;
    }

    /**
     * Set the main body of the CareProvider comment
     *
     * @param comment A string of the new body of the comment.
     */
    public void setComment(String comment){
        this.message = comment;
    }


    @Override
    public String toString(){
        return "Title: " + getTitle() + "\n\nComment: " + getComment();
    }
}
