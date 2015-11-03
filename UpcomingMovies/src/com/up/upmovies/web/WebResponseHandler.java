package com.up.upmovies.web;

import java.net.HttpURLConnection;

import org.json.JSONException;

import android.content.ContentResolver;
import android.os.Bundle;

import com.up.upmovies.Config;
import com.up.upmovies.UpApplication;
import com.up.upmovies.service.ServiceErrorCodes;
import com.up.upmovies.utils.Logger;

public abstract class WebResponseHandler {
	static final String TAG = WebResponseHandler.class.getSimpleName();
	
	/** Authority string to be used for ContentResolver */
	private final ContentResolver resolver;

	/**
	 * Constructor
	 * 
	 * @param inAuthority
	 *            authority of this
	 */
	public WebResponseHandler(final String inAuthority) {
		this.resolver = UpApplication.getAppContext().getContentResolver();
	}

	/**
	 * Constructor
	 * 
	 */
	public WebResponseHandler() {
		this.resolver = UpApplication.getAppContext().getContentResolver();
	}

	/**
	 * 
	 * @param statusCode
	 * @param response
	 * @return
	 * @throws JSONException 
	 */
	public final Bundle handleResponse(int statusCode, WebResponse response) throws JSONException {

		final Bundle responseData = new Bundle();
		int serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;

		try {
			switch (statusCode) {
			case HttpURLConnection.HTTP_OK:
				if (null != response) {
					
					if(Config.DEBUG)Logger.i(TAG, ":: WebResponseHandler.composeEntityValues() : RESPONSE : " + response.entityString);
					
					serviceStatus = processResponse(response.entityString,
							this.resolver, responseData);

					if (ServiceErrorCodes.UP_REQUEST_SUCCESS == serviceStatus) {
						// We don't need response string longer
						response.entityString = null;
					}
				} else {
					if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Request was completed. But response is null");
				}
				break;

			case HttpURLConnection.HTTP_UNAUTHORIZED:
				if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Invalid user id");
				serviceStatus = ServiceErrorCodes.UP_REQUEST_INVALID_UID;
				break;
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
				if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Request was not executed. Internal service error");
				serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;
				break;
			case WebRequestStatus.UP_REQUEST_FAILED:
				if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Request was not executed.");
				serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;
				break;
			case WebRequestStatus.UP_REQUEST_FAILED_TIMEOUT:
				if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Request was not executed. Timeout");
				serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;
				break;
			case WebRequestStatus.UP_REQUEST_FAILED_NO_NETWORK:
				if(Config.DEBUG)Logger.w(TAG, ":: WebResponseHandler.composeEntityValues() : Request was not executed. No Network connection.");
				serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;
				break;
			default:
				serviceStatus = ServiceErrorCodes.UP_REQUEST_FAILED;
				break;
			}
		} finally {
			if (null != response) {
				responseData.putInt(Config.ERROR_CODE_EXTRA_BUNDLE,
						serviceStatus);
			}
		}
		return responseData;
	}

	/**
	 * 
	 * @param entityString
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("static-method")
	protected int processResponse(final String entityString,
			final ContentResolver resolver, Bundle responseData)
			throws JSONException {
		int requestResultErrorCode = ServiceErrorCodes.UP_REQUEST_SUCCESS;

		return requestResultErrorCode;
	}
	
}
