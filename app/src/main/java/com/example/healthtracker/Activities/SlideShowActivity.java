package com.example.healthtracker.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.healthtracker.R;
import com.example.healthtracker.Contollers.ViewPageAdapterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* Code for testing the ability of a slideshow to operate on the emulator reused from the tutorial by 
*EDMT Dev, https://www.youtube.com/watch?v=SX8l9vv-N_4, 2016/07/13, viewed 2018/10/20*
*/

/*
 * SlideShowActivity will enable patients and careproviders to view a slidehsow of all of the photos
 * associated with a problem.
 */
public class SlideShowActivity extends AppCompatActivity {

    private static Map<String, ArrayList<String>> images1 = new HashMap<>();
    private static final String TAG = "SlideShow fuck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);
        ArrayList<String> image = new ArrayList<>();
        images1.put("test",image);

        getImageList();
        ViewPager viewPager = findViewById(R.id.ViewPager);

        update();
    }

    public static boolean add(String imageName, String arrayName) {
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
        }
    }

    public void update(){
        ViewPager viewPager = findViewById(R.id.ViewPager);
        ViewPageAdapterActivity adapter = new ViewPageAdapterActivity(SlideShowActivity.this, images1.get("123"));
        viewPager.setAdapter(adapter);
    }

    public ArrayList<String> getImageList(){
        Intent intent =getIntent();
        String problem =  intent.getStringExtra("ProblemTitle");
        return images1.get(problem);
    }
}
