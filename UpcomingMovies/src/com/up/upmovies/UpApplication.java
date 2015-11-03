package com.up.upmovies;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.up.upmovies.entities.MovieList;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

public class UpApplication extends Application {
	
	private static Context appContext = null;
	private static String deviceId = "";
	
	private static MovieList moviesList = new MovieList();

	@Override
	public void onCreate() {
		super.onCreate();
		
		UpApplication.appContext = getApplicationContext();
		
		// get device id
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		UpApplication.deviceId = (telephonyManager.getDeviceId() == null) ? Secure.getString(appContext.getContentResolver(), Secure.ANDROID_ID)
							: telephonyManager.getDeviceId(); 
		
		// package signatures
		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.up.upmovies", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		
		 // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
	}
	
	/**
	 * common getter for application context
	 * @return - application context
	 */
	public static Context getAppContext() {
		
		return UpApplication.appContext;
	} 
	
	/**
	 * common getter for device ID
	 * @return - device ID
	 */
	public static String getDeviceId() {
		
		return UpApplication.deviceId;
	}

	
	public static MovieList getMovieList() {
		return moviesList;
	}

	public static void setMovieList(MovieList moviesList) {
		UpApplication.moviesList = moviesList;
	}
	
}
