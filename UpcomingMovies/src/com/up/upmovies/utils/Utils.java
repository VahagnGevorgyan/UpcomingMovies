package com.up.upmovies.utils;

import com.up.upmovies.Config;
import com.up.upmovies.UpApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Utils {

	/** For logging */
	private static final String TAG = Utils.class.getSimpleName();
	
	/**
	 * 	ConnectivityManager for checking network status.
	 */
	private static ConnectivityManager connectivityManager = null;
	
	/**
	 * Check network status.
	 */
	public static boolean checkNetworkStatus() {
		boolean status = false;

		if (null == connectivityManager) {
			connectivityManager = (ConnectivityManager) UpApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		}

		if (null != connectivityManager) {
			final NetworkInfo net = connectivityManager.getActiveNetworkInfo();

			if (null != net) {
				final int networkType = net.getType();

				if (networkType == ConnectivityManager.TYPE_WIFI
						|| networkType == ConnectivityManager.TYPE_MOBILE) {
					status = true;
				}
			}
		}
		if(Config.DEBUG)Logger.i(TAG, ":: Utils.checkNetworkStatus : Network Status : " + status);
		return status;
		
	} // end method checkNetworkStatus
	
	
	
}
