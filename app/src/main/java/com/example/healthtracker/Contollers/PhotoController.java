package com.example.healthtracker.Contollers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

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

public class PhotoController extends AppCompatActivity {


    // Save a photo to locally in internal storage
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

    // Load a locally stored photo
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

        ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();

        File dir = new File(cw.getCacheDir().toString() + File.separator + "images");
        File[] directoryListing = dir.listFiles();

        if (directoryListing != null) {
            for (File child : directoryListing) {
                //System.out.println("Here's the difference...\n" + child.toString().split("_")[0] + "_" + child.toString().split("_")[1] + '\n' + dir.toString() + problemName + File.separator + record);
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

    // Encode a bitmap to a string
    public static String imageToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    // Convert an encoded string to an image to be displayed
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
