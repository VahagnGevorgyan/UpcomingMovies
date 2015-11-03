package com.up.upmovies.service;

import com.up.upmovies.Config;
import com.up.upmovies.utils.Logger;
import com.up.upmovies.web.WebRequest;
import com.up.upmovies.web.WebRequestCaller;
import com.up.upmovies.web.WebResponseHandler;
import com.up.upmovies.web.handlers.UpMoviesHandler;
import com.up.upmovies.web.requests.UpMoviesRequest;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class UpIntentService extends IntentService {

	private static final String TAG = UpIntentService.class.getSimpleName();
	
	
	public UpIntentService() {
		super(TAG);
	}
	
	/**
	 * Service request statuses
	 */
	public interface ServiceStatus {
		/** Running Status returned to the caller */
		int RUNNING = 0;
		/** Error Status returned to the caller */
		int ERROR = 1;
		/** Finished Status returned to the caller */
		int FINISHED = 2;
		/** Result Status - result comes in bundle with it */
		int RESULT = 3;
	}
	
	
	/**
	 * 	Service Request Statuses 
	 */
	public interface RequestTypes {
		int DEF_REQUEST = 0;
		// MOVIES
		int UPCOMING_MOVIES = DEF_REQUEST + 1;
		// OTHER REQUEST TYPES
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		 
		if(Config.DEBUG)Logger.i(TAG, ":: UpIntentService.onHandleIntent()");
		
		final int requestType = intent.getIntExtra(Config.EXTRA_REQUEST_TYPE, RequestTypes.DEF_REQUEST);
		final ResultReceiver receiver = intent.getParcelableExtra(Config.EXTRA_STATUS_RECEIVER);
		
		if(receiver == null) {
			if(Config.DEBUG)Logger.w(TAG, ":: UpIntentService.onHandleIntent() : Abnormal situation request : " + requestType);
		}
		
		
		int serviceResultCode = ServiceStatus.ERROR;
		final Bundle bundle = new Bundle();
		Bundle rqExecuteResultBundle = null;
		bundle.putString(Intent.EXTRA_TEXT, String.valueOf(requestType));
		bundle.putInt(Config.EXTRA_REQUEST_TYPE, requestType);
		
		if (receiver != null) {
			receiver.send(ServiceStatus.RUNNING, bundle);
			if(Config.DEBUG)Logger.i(TAG, ":: UpIntentService.onHandleIntent() : RECEIVER : " + receiver.toString());
		}
		Bundle dataBundle = null;
		try {
			WebResponseHandler handler = null;
			WebRequest request = null;
			
			switch (requestType) {
				case RequestTypes.UPCOMING_MOVIES:
					dataBundle = intent.getBundleExtra(Config.EXTRA_REQUEST_DATA);
					handler = new UpMoviesHandler();
					request = new UpMoviesRequest(dataBundle.getString(Config.EXTRA_REQUEST_DATA_APIKEY),
							dataBundle.getInt(Config.EXTRA_REQUEST_DATA_PAGE_LIMIT),
							dataBundle.getString(Config.EXTRA_REQUEST_DATA_COUNTRY),
							dataBundle.getInt(Config.EXTRA_REQUEST_DATA_PAGE)
							);
					break;
				default:
					break;
			}
			
			WebRequestCaller.getCaller();
			rqExecuteResultBundle = WebRequestCaller.executeRequest(request, handler);

			if (Config.DEBUG)Logger.i(TAG, ":: UpIntentService.onHandleIntent() : request finished");

			if (rqExecuteResultBundle != null) {
				
				int errorCode = rqExecuteResultBundle
						.getInt(Config.ERROR_CODE_EXTRA_BUNDLE);
				
				if(Config.DEBUG)Logger.i(TAG, ":: UpIntentService.onHandleIntent() : BUNDLE : " + rqExecuteResultBundle.toString());
				if(Config.DEBUG)Logger.i(TAG, ":: UpIntentService.onHandleIntent() : ERROR CODE : " + errorCode);

				if (errorCode == ServiceErrorCodes.UP_REQUEST_SUCCESS) {
					serviceResultCode = ServiceStatus.FINISHED;
				}
			}
		} catch(Exception e) {
			if(Config.DEBUG)Logger.e(TAG,":: WebRequest : Error : Exception while request : " + e.toString());
		} finally {
			if (receiver != null) {
				if (rqExecuteResultBundle != null) {
					bundle.putAll(rqExecuteResultBundle);
				}

				receiver.send(serviceResultCode, bundle);
			}
		}

	}

}
