package com.example.healthtracker.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.healthtracker.R;
import com.example.healthtracker.Contollers.ViewPageAdapterActivity;

import java.util.ArrayList;

/* Code for testing the ability of a slideshow to operate on the emulator reused from the tutorial by 
*EDMT Dev, https://www.youtube.com/watch?v=SX8l9vv-N_4, 2016/07/13, viewed 2018/10/20*
*/

/*
 * SlideShowActivity will enable patients and careproviders to view a slidehsow of all of the photos
 * associated with a problem.
 */
public class SlideShowActivity extends AppCompatActivity {

    private static ArrayList<String> images = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        ViewPager viewPager = findViewById(R.id.ViewPager);
        update();
    }

    public static void add(String imageName){
        images.add(imageName);
    }

    public void update(){
        ViewPager viewPager = findViewById(R.id.ViewPager);
        ViewPageAdapterActivity adapter = new ViewPageAdapterActivity(SlideShowActivity.this, images);
        viewPager.setAdapter(adapter);
    }
}
