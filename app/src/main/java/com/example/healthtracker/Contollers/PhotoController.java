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
    public static String saveToInternalStorage(Bitmap bitmapImage, ContextWrapper cw, String problemTitle, String recordTitle){
        // path to /data/user/0/com.example.healthtracker/app_imageDir
        //File directory = cw.getDir(cw.getDataDir(), Context.MODE_PRIVATE);
        File directory = new File(cw.getCacheDir().toString() + File.separator + "images");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        System.out.println(directory);
        // Create imageDir
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CANADA).format(new Date());
        String imageName = problemTitle + "_" + recordTitle + "_" + timeStamp+".jpg";
        File mypath=new File(directory,imageName);
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
}
