package com.up.upmovies.utils;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.ImageView;

public class CommonHelper {

	/*
	 * Setting touch image
	 */
	public static boolean setOnTouchImage(ImageView view, MotionEvent event) {
		switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	view.setColorFilter(Color.argb(50, 0, 0, 0));
            	break;
            case MotionEvent.ACTION_MOVE:	
            	break;
            case MotionEvent.ACTION_UP:
            	view.performClick();
            	view.setColorFilter(Color.argb(0, 0, 0, 0));
            	break;
            default:
            	view.setColorFilter(Color.argb(0, 0, 0, 0));
            	break;
        }
        return false;
	}
}
