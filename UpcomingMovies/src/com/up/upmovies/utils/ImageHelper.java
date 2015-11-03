package com.up.upmovies.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.up.upmovies.Config;
import com.up.upmovies.ui.utils.DynamicHeightImageView;


public class ImageHelper {
	
	private static final String TAG = ImageHelper.class.getSimpleName();
	
	public static MemCache memCache = new MemCache();
	
	/**
	 * LoadImagiesByLoader
	 * 
	 * @param urldisplay
	 * @param imgViewItem2
	 */
	public static void loadImagiesByLoader(final Context context, String urldisplay, final DynamicHeightImageView imgViewItem2) {
		ImageLoader.getInstance().displayImage(urldisplay, imgViewItem2, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {	}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {	}
			
			@Override
			public void onLoadingComplete(String arg0, final View view, final Bitmap bitmap) {
				
				if(Config.DEBUG)Logger.i(TAG, ":: ImageHelper.onLoadingComplete : url : " + arg0);
				
				memCache.addBitmapToMemoryCache(arg0, bitmap);
				
				if(view != null && context != null) {
					((Activity) context).runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							((DynamicHeightImageView)view).setImageBitmap(bitmap);
						}
					});
				}				
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}

		});
	}

}
