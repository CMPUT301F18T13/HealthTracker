package com.example.healthtracker.Activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.healthtracker.R;
import com.example.healthtracker.Contollers.ViewPageAdapterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* Code for testing the ability of a slideshow to operate on the emulator reused from the tutorial by 
*EDMT Dev, https://www.youtube.com/watch?v=SX8l9vv-N_4, 2016/07/13, viewed 2018/10/20*
*/

/*
 * SlideShowActivity will enable patients and careproviders to view a slidehsow of all of the photos
 * associated with a problem.
 */
public class SlideShowActivity extends AppCompatActivity {

    private static Map<String, ArrayList<String>> images1 = new HashMap<>();
    private static ArrayList<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        ViewPager viewPager = findViewById(R.id.ViewPager);
        //String test = getImageList();
        //String test = "test1";
        try {
            update();
        }
        catch (NullPointerException ne){
            Toast.makeText(this, "No photos associated with this problem", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean add(String imageName, String arrayName) {
        List<String> itemsList = images1.get(arrayName);
        // if list does not exist create it
        if(itemsList == null) {
            itemsList = new ArrayList<>();
            itemsList.add(imageName);
            images1.put(arrayName, (ArrayList<String>) itemsList);
        } else {
            // add if item is not already in list
            if(!itemsList.contains(imageName)) itemsList.add(imageName);
        }
        return true;

        /*

        if (images1.get(arrayName) == null) {
            ArrayList<String> image = new ArrayList<>();
            image.add(imageName);
            images1.put(arrayName,image);
            return true;
        } else {
            ArrayList<String> image2 = images1.get(arrayName);
            image2.add(imageName);
            images1.put(arrayName,image2);
            if (image2.size() == 10) {
                return false;
            } else {
                image2.add(imageName);
                return true;
            }
        }*/
    }

    public void update(){
        ViewPager viewPager = findViewById(R.id.ViewPager);
        ViewPageAdapterActivity adapter = new ViewPageAdapterActivity(SlideShowActivity.this, images1.get(getImageList()));
        viewPager.setAdapter(adapter);
    }

    public String getImageList(){
        Intent intent =getIntent();
        return intent.getStringExtra("ProblemTitle");
    }
}
