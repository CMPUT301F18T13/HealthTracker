package com.example.healthtracker.EntityObjects;

/**
 * Photo object that represents the photos that can be added to patient records.
 *
 * This class may be altered once user photo taking is implemented
 *
 * @author caochenlin
 * @version 1.0
 * @since 2018-10-28
 *
 */

public class Photo {
    private String fileLocation;

    /**
     * Constructor for creating a new photo. The photo object consists of a string which indicates where the image is stored.
     *
     * @param fileLocation location of file
     */
    public Photo(String fileLocation){
        this.fileLocation = fileLocation;
    }

    /**
     * Get the file location of the image.
     *
     * @return A string indicating the file location of the image that this object represents.
     */
    public String getFile(){
        return this.fileLocation;
    }

    /**
     * Set the fileLocation of the image.
     *
     * @param fileLocation this string input is the file location of the image that the photo object represents.
     */
    public void setFile(String fileLocation){
        this.fileLocation = fileLocation;
    }

}
