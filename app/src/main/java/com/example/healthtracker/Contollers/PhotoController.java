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
    public static String saveToInternalStorage(Bitmap bitmapImage, ContextWrapper cw){
        // path to /data/user/0/com.example.healthtracker/app_imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CANADA).format(new Date());
        String imageName = "test_"+timeStamp+".jpg";
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
}
