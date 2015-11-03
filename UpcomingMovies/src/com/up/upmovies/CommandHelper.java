package com.up.upmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.up.upmovies.service.DetachableResultsReceiver;
import com.up.upmovies.service.UpIntentService;
import com.up.upmovies.service.UpIntentService.RequestTypes;
import com.up.upmovies.utils.Logger;

public class CommandHelper {

	private static final String TAG = CommandHelper.class.getSimpleName();
	
	private static CommandHelper mInstance = null;
	
	public synchronized static void init() {
		if (null == mInstance) {
			mInstance = new CommandHelper();
		}
	}

	public static CommandHelper getInstance() {
		if (null == mInstance) {
			init();
		}

		return mInstance;
	}
	
	
	/**
	 * 	Method for requesting upcoming movies
	 */
	public void RequestUpcomingList(Context context, DetachableResultsReceiver serviceReceiver) {
		
		if(Config.DEBUG)Logger.i(TAG, ":: SplashActivity.RequestTariffs ");
		
		// start service with intent
		final Intent intent = new Intent(Intent.ACTION_SYNC, null, 
				UpApplication.getAppContext(), UpIntentService.class);
		intent.putExtra(Config.EXTRA_STATUS_RECEIVER, serviceReceiver);
		intent.putExtra(Config.EXTRA_REQUEST_TYPE, RequestTypes.UPCOMING_MOVIES);
		final Bundle dataBundle = new Bundle();
		dataBundle.putString(Config.EXTRA_REQUEST_DATA_APIKEY, Config.API_KEY);
		dataBundle.putInt(Config.EXTRA_REQUEST_DATA_PAGE_LIMIT, 16);
		dataBundle.putString(Config.EXTRA_REQUEST_DATA_COUNTRY, "us");
		dataBundle.putInt(Config.EXTRA_REQUEST_DATA_PAGE, 1);
		intent.putExtra(Config.EXTRA_REQUEST_DATA, dataBundle);
		context.startService(intent);
		
	} // end method RequestUpcomingList
	
}
