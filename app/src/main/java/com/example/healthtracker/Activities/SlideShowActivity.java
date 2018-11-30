package com.example.healthtracker.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.healthtracker.R;
import com.example.healthtracker.Contollers.ViewPageAdapterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        setContentView(R.layout.slide_show);
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

    public class MyAdapter extends PagerAdapter {

        private ArrayList<Integer> images;
        private LayoutInflater inflater;
        private Context context;

        public MyAdapter(Context context, ArrayList<Integer> images) {
            this.context = context;
            this.images=images;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View myImageLayout = inflater.inflate(R.layout.slide, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.image);
            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}
