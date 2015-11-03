package com.up.upmovies.web.requests;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;

import com.up.upmovies.Config;
import com.up.upmovies.web.WebRequest;


public class UpMoviesRequest extends WebRequest {
	
	private String apikey;
	private int page_limit;
	private String country;
	private int page;

	public UpMoviesRequest(String apikey, int page_limit, String country, int page) {
		super();
		this.apikey = apikey;
		this.page_limit = page_limit;
		this.country = country;
		this.page = page;
		this.uri = URI.create(makeRequestUriString());
		this.httpMethod = HttpMethod.GET;
    	// create entity
		composeEntityValues();
	}
	
	@Override
	protected void composeEntityFullValues(JSONObject jsonObject)
			throws JSONException {	}

	@Override
	protected String makeRequestUriString() {
		StringBuilder stbuilder = new StringBuilder();
		return stbuilder
				.append(Config.BUILD_TYPE.getHost())
				.append("lists/movies/upcoming.json")
				.append("?apikey=" + apikey)
				.append("&page_limit=" + page_limit)
				.append("&country=" + country)
				.append("&page=" + page)
				.toString();
	}
	
}
