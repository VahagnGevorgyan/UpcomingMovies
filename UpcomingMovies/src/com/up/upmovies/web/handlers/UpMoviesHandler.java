package com.up.upmovies.web.handlers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.os.Bundle;

import com.up.upmovies.Config;
import com.up.upmovies.entities.MovieList;
import com.up.upmovies.service.ServiceErrorCodes;
import com.up.upmovies.web.WebResponseHandler;

public class UpMoviesHandler extends WebResponseHandler {
	
	private MovieList movieList = new MovieList();

	public UpMoviesHandler() {
		super();
	}

	@Override
	protected int processResponse(String entityString,
			ContentResolver resolver, Bundle responseData) throws JSONException {
		
		int serviceStatus = ServiceErrorCodes.UP_REQUEST_SUCCESS;
		
		try {
			serviceStatus = super.processResponse(entityString, resolver, responseData);
			
			if(entityString != null && !entityString.equals("")) {
				JSONObject jsonObject = new JSONObject(entityString);
				if(jsonObject != null && jsonObject.has("movies")) {
						JSONArray moviesArray = jsonObject.getJSONArray("movies");
						if(moviesArray != null && moviesArray.length() > 0) {
							this.movieList = new MovieList(moviesArray);
						}
				}
				// put data
				responseData.putParcelable(Config.UPCOMING_MOVIES_LIST, this.movieList);
			} 
		} catch (final JSONException e) {
			serviceStatus = ServiceErrorCodes.UP_REQUEST_PARSING_ERROR;
		}
		return serviceStatus;
		
	}
	
}
