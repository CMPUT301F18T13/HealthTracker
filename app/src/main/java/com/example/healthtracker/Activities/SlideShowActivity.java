package com.example.healthtracker.Activities;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.healthtracker.Contollers.PhotoController;
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
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class SlideShowActivity extends Activity {

    private ViewFlipper myViewFlipper;
    private float initialXPoint;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Am i even being created?");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slide_show);
        myViewFlipper = (ViewFlipper) findViewById(R.id.myflipper);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        if (this.isProblem()) {
            images = PhotoController.loadImagesByProblem(cw, this.getExtraString());
        }
        else {
            System.out.println("Made it here, good");
            images = PhotoController.loadTemporaryRecordImages(cw);
            System.out.println("Not crashing here, also good");
        }
        for (Bitmap image: images) {
            ImageView imageView = new ImageView(SlideShowActivity.this);
            imageView.setImageBitmap(image);
            myViewFlipper.addView(imageView);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialXPoint = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalx = event.getX();
                if (initialXPoint > finalx) {
                    if (myViewFlipper.getDisplayedChild() == images.size())
                        break;
                    myViewFlipper.showNext();
                } else {
                    if (myViewFlipper.getDisplayedChild() == 0)
                        break;
                    myViewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }

    public String getExtraString(){
        Intent intent = getIntent();
        return intent.getExtras().getString("ProblemTitle");
    }

    public Boolean isProblem() {
        Intent intent = getIntent();
        String isProblem = intent.getStringExtra("isProblem");
        if(isProblem == null){
            return false;
        }
        if (intent.getStringExtra("isProblem").equals("Problem")) {
            return true;
        }
        return false;
    }
}

