package com.up.upmovies.web;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

import com.up.upmovies.Config;
import com.up.upmovies.utils.Logger;


public abstract class WebRequest {
	
	public static enum HttpMethod {
		GET, POST, HEAD, 
	}
	
	private static final String TAG = WebRequest.class.getSimpleName();
	
	public WebRequest() {
		this.uri = URI.create(Config.BUILD_TYPE.getHost());
		this.httpMethod = HttpMethod.POST;
	}
	
	public URI uri;
	public HttpMethod httpMethod;
	public boolean encodingGzipDeflate;
	public int statusCode;
	public JSONObject entityObject;
	
	
	/** Check request validity */
	public boolean isValid() {
		return (this.uri != null) && (this.httpMethod != null);
	}
	
	protected void composeEntityValues() {
		JSONObject jsonObject = new JSONObject();
		try {
			composeEntityFullValues(jsonObject);
			this.entityObject = jsonObject;
			
			if(Config.DEBUG)Logger.i(TAG, ":: WebRequest.composeEntityValues : entity object : " + jsonObject.toString());
		} catch (JSONException e) {
			if(Config.DEBUG)Logger.e(TAG,":: WebRequest : Error : " + e.toString());
		}
	}

	protected void composeEntityFullValues(JSONObject jsonObject)
			throws JSONException {
		// TODO Auto-generated method stub
	}

	protected String makeRequestUriString() {
		return null;
	}
}
