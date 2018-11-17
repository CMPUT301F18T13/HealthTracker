package com.example.healthtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by caochenlin on 2018/11/2.
 */

//todo cite student picker

public class CareProviderDataManager {


    private Context context;
    final private static String careProviderKey = "CareProvider";

    CareProviderDataManager(Context context){
        this.context = context;
    }
    // save serialized string of CareProvider object to shared preferences
    // calls method to convert CareProvider object  to serialized string
    public void saveCareProviderLocally(CareProvider careProvider) {
        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefs.edit();
        editor.putString(careProviderKey, careProviderToString(careProvider));
        editor.apply();
    }

    // load serialized string of CareProvider from shared preferences
    // call method to convert serialized string back to CareProvider object
    public CareProvider loadCareProviderLocally()  {
        SharedPreferences myPrefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String careProviderString = myPrefs.getString(careProviderKey, "");
        CareProvider careProvider = careProviderFromString(careProviderString);
        if(careProvider == null){
            return new CareProvider();
        }
        return careProvider;
    }

    // convert CareProvider object to serialized string
    private String careProviderToString(CareProvider careProvider) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(careProvider);
            oo.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        byte[] bytes = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // convert serialized string to CareProvider object
    private CareProvider careProviderFromString(String careProviderData) {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(careProviderData, Base64.DEFAULT));
        CareProvider careProvider = null;
        try {
            ObjectInputStream oi = new ObjectInputStream(bi);
            careProvider = (CareProvider) oi.readObject();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return careProvider;
    }

    public void elasticSearch(String keyword, String searchType){

    }

}
