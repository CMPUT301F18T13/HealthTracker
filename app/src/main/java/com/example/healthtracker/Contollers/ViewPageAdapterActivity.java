package com.example.healthtracker.Contollers;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.healthtracker.R;

import java.util.ArrayList;

/* Code for testing the ability of a slideshow to operate on the emulator reused from the tutorial by
*EDMT Dev, https://www.youtube.com/watch?v=SX8l9vv-N_4, 2016/07/13, viewed 2018/10/20*
*/

/**
 * ViewPageAdapterActivity handles loading the arrayList containing the images to be displayed when a problem's
 * slideshow is desired to be seen. After the array list is loaded each image view is put into a container object
 * to be shown in a slideshow.
 *
 * @author Tyler Watson
 * @version 1.0
 * @since 2018-11-25
 */
public class ViewPageAdapterActivity extends PagerAdapter {
    private final Activity activity;
    private final ArrayList<String> images;

    /**
     * Constructor for the ViewPageAdapterActivity that handles slideshow functionality
     *
     * @param activity the activity from which adapter was called
     * @param images the arrayList of images to be displayed in the slideshow
     */
    public ViewPageAdapterActivity(Activity activity, ArrayList<String> images){
        this.activity = activity;
        this.images = images;
    }

    /**
     * Gets the number of images in the provided arrayList
     *
     * @return the number of images in the arrayList of images
     */
    @Override
    public int getCount() {
        try{
            return images.size();
        }
        catch (NullPointerException ne) {
            return 0;
        }
    }

    /**
     * Check if the view is from the object
     *
     * @param view the view called in method to be checked if it is from the object
     * @param object the object called in method to be checked if it is from the view
     * @return whether the object is equal to the view
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    /**
     * Modifies the images and stores them as a view in a container to be displayed in a slideshow
     *
     * @param container stores the images to be displayed in the slideshow
     * @param position position designating images location in slideshow
     * @return item view
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View itemView = inflater.inflate(R.layout.viewpager_item, container, false);

        ImageView image;
        image = itemView.findViewById(R.id.imageView);
        DisplayMetrics dis = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dis);
        // Adjust the size and dimensions of the image
        int height = dis.heightPixels;
        int width = dis.widthPixels;
        image.setMinimumHeight(height);
        image.setMinimumWidth(width);
        // set the modified image to the ImageView
        image.setImageBitmap(PhotoController.stringToImage(images.get(position)));
        // add the imageview to the container
        container.addView(itemView);
        return itemView;
    }

    /**
     * Removes an image from the container it was stored in
     *
     * @param container the container that the images are stored in
     * @param position the position of the image within the container
     * @param object the referenced object to be removed
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
