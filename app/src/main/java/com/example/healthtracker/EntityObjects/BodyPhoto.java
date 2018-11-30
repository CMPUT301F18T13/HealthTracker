package com.example.healthtracker.EntityObjects;

import com.example.healthtracker.EntityObjects.Photo;

/**
 * A body photo which is used by a Patient to indicate where on their problem is located on their body.
 *
 * @author Michael Boisvert
 * @version 1.0
 * @since 2018/11/2
 */
public class BodyPhoto extends Photo {
    private String name;
    private int pinXLocation;
    private int pinYLocation;

    /**
     * The constructor for BodyPhoto requires that the file location of the photo be specified.
     *
     * @param fileLocation A string which indicates the file location of the photo.
     */
    public BodyPhoto(String fileLocation){
        super(fileLocation);
    }

    /**
     * Set the name of the bodyphoto.
     *
     * @param name A string that will be the name of the bodyphoto.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the name of the body photo.
     *
     * @return A string that is the name of the bodyphoto.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Set the location of the pin that indicates where the patients problem is on the image of their body.
     *
     * @param xLocation Location of the pin in the x-axis.
     * @param yLocation Location of the pin in the y-axis.
     */
    public void setPinLocation(int xLocation, int yLocation){
        this.pinXLocation = xLocation;
        this.pinYLocation = yLocation;
    }

    /**
     * Get the location of the pin in the x axis.
     *
     * @return an integer indicating the x-axis value of the pin's location.
     */
    public int getPinXLocation(){
        return this.pinXLocation;
    }

    /**
     * Get the location of the pin in the y axis.
     *
     * @return an integer indicating the y-axis value of the pin's location.
     */
    public int getPinYLocation(){
        return this.pinYLocation;
    }

    /**
     * Remove the location pin from the bodyphoto.
     */
    public  void removePin(){
        this.pinXLocation = 0;
        this.pinYLocation = 0;
    }



}
