package com.example.healthtracker.Contollers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.example.healthtracker.EntityObjects.Patient;
import com.example.healthtracker.EntityObjects.PatientRecord;
import com.example.healthtracker.EntityObjects.Problem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * PhotoController handles saving images taken or selected by the Patient to both local and remote storage.
 * <p></p>
 * Also manages converting the image to and from a string to ease storage abilities
 *
 * @author Tyler Watson
 * @version 1.0
 * @since 2018-11-25
 */
public class PhotoController extends AppCompatActivity {


    /**
     * Save a photo to locally in internal storage
     *
     * @param bitmapImage bitmap object passed in method call
     * @param cw Context wrapper passed in method on call
     * @return the path of where the image was stored
     */
    public static String saveToInternalStorage(Bitmap bitmapImage, ContextWrapper cw, String problemTitle, String recordTitle, String timeStamp){
        // path to /data/user/0/com.example.healthtracker/app_imageDir
        //File directory = cw.getDir(cw.getDataDir(), Context.MODE_PRIVATE);
        File directory = new File(cw.getCacheDir().toString() + File.separator + "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Create imageDir
        String imageName = problemTitle + "_" + recordTitle + "_" + timeStamp+".jpg";
        File mypath=new File(directory,imageName);
        System.out.println(mypath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    // Used to hold photos without titles
    public static String saveToTemporaryStorage(Bitmap bitmapImage, ContextWrapper cw, String timeStamp) {
        // path to /data/user/0/com.example.healthtracker/app_imageDir
        //File directory = cw.getDir(cw.getDataDir(), Context.MODE_PRIVATE);
        File directory = new File(cw.getCacheDir().toString() + File.separator + "temporaryRecord");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Create imageDir
        String imageName = timeStamp+".jpg";
        File mypath=new File(directory,imageName);
        System.out.println(mypath);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return timeStamp;
    }


    /**
     * Loads an image stored locally within the app
     *
     * @param path the path to where the photo is stored
     * @param imageName the image name at the end of the path
     * @return returns null on image not found or returns a bitmap if image found
     */
    public static Bitmap loadImageFromStorage(String path, String imageName) {
        try {
            File f=new File(path, imageName);
            return BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Bitmap> loadImagesByProblem(ContextWrapper cw, String problemName) {

        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.toString().split("_")[0].equals(dir.toString() + File.separator + problemName)) {
                    System.out.println("Found match");
                    try {
                        bitmapArray.add(BitmapFactory.decodeStream(new FileInputStream(child)));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bitmapArray;
    }

    public static ArrayList<String> getFilesNamesOfProblemPhotos(ContextWrapper cw, String problemName) {
        ArrayList<String> files = new ArrayList<String> ();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.toString().split("_")[0].equals(dir.toString() + File.separator + problemName)) {
                    System.out.println("Found file");
                    files.add(child.toString());
                }
            }
        }
        return files;
    }

    public static void renamePhotosByProblem(ContextWrapper cw, String oldProblemTitle, String newProblemTitle) {
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Found an old problem title
                if (child.toString().split("_")[0].equals(dir.toString() + File.separator + oldProblemTitle)) {
                    File newFile = new File(dir.toString() + File.separator + newProblemTitle + "_" + child.toString().split("_")[1] + "_" + child.toString().split("_")[2] + "_" + child.toString().split("_")[3]);
                    System.out.println("Old file was " + child.toString() + '\n' + "New file is " + newFile.toString());
                    child.renameTo(newFile);
                }
            }
        }
    }

    public static ArrayList<Bitmap> loadImagesByRecord(ContextWrapper cw, String problemName, String record) {

        System.out.println("Trying to load images...");
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        System.out.println("Directory is " + dir.toString());
        if (directoryListing != null) {
            System.out.println("There is stuff in the directory");
            for (File child : directoryListing) {
                System.out.println("Here's the difference...\n" + child.toString().split("_")[0] + "_" + child.toString().split("_")[1] + '\n' + dir.toString() + problemName + File.separator + record);
                if ((child.toString().split("_")[0] + "_" + child.toString().split("_")[1]).equals(dir.toString() + File.separator + problemName + "_" + record)) {
                    System.out.println("Found match");
                    try {
                        bitmapArray.add(BitmapFactory.decodeStream(new FileInputStream(child)));
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bitmapArray;
    }

    public static ArrayList<String> getTimestampsByRecord(ContextWrapper cw, String problemName, String record) {
        ArrayList<String> timeStamps = new ArrayList<String>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child: directoryListing) {
                if ((child.toString().split("_")[0] + "_" + child.toString().split("_")[1]).equals(dir.toString() + File.separator + problemName + "_" + record)) {
                    String timeStamp = child.toString().split("_")[2] + "_" + child.toString().split("_")[3];
                    timeStamps.add(timeStamp.substring(0, timeStamp.length() - 4));

                    System.out.println("Found match " + timeStamp.substring(0, timeStamp.length() - 4));
                }
            }
        }

        return timeStamps;
    }

    public static ArrayList<Bitmap> loadTemporaryRecordImages(ContextWrapper cw) {
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "temporaryRecord");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println("Found match");
                try {
                    bitmapArray.add(BitmapFactory.decodeStream(new FileInputStream(child)));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmapArray;
    }

    public static void savePatientPhotos(ContextWrapper cw, Patient patient) {
        ArrayList<Problem> problems = patient.getProblemList();
        ArrayList<PatientRecord> records;
        ArrayList<String> photos;
        ArrayList<String> timestamps;
        for (Problem problem: problems) {
            records = problem.getRecords();
            for (PatientRecord record: records) {
                photos = record.getPhotos();
                timestamps = record.getPhotoTimestamps();
                for (int i = 0; i < photos.size(); i++) {
                    PhotoController.removePhotosFromInternalStorage(cw, problem.getTitle(), record.getTitle());
                    PhotoController.saveToInternalStorage(PhotoController.stringToImage(photos.get(i)), cw, problem.getTitle(), record.getTitle(), timestamps.get(i));
                }
            }
        }
    }

    /**
     * Converts a provided bitmap of an image to a string object
     *
     * @param bitmap the bitmap provided on method call to be converted to a string
     * @return returns the encoded bitmap as a string object
     */
    public static String imageToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * Converts a provided string of an image to a bitmap object
     *
     * @param encodedImage the string provided on method call to be converted to an image
     * @return returns the decoded string as a bitmap
     */
    public static Bitmap stringToImage(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    // Removes photos for the given record
    public static void removePhotosFromInternalStorage(ContextWrapper cw, String problemName, String record) {
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                if ((child.toString().split("_")[0] + "_" + child.toString().split("_")[1]).equals(dir.toString() + File.separator + problemName + "_" + record)) {
                    System.out.println("Found match");
                    child.delete();
                }
            }
        }
    }

    public static void removePhotosByProblem(ContextWrapper cw, String problemName) {
        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                // Found an old problem title
                if (child.toString().split("_")[0].equals(dir.toString() + File.separator + problemName)) {
                    System.out.println("Deleted file is " + child.toString());
                    child.delete();
                }
            }
        }
    }

    // Removes photos for the given record
    public static void removePhotosFromTemporaryStorage(ContextWrapper cw) {
        File dir = new File(cw.getCacheDir().toString() + File.separator + "temporaryRecord");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                System.out.println("Found match");
                child.delete();
            }
        }
    }

}
