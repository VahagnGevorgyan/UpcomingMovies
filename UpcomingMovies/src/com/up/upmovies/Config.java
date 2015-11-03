package com.up.upmovies;

public class Config {
//	public static final BuildType BUILD_TYPE = BuildType.RELEASE;
	public static final BuildType BUILD_TYPE = BuildType.DEBUGGING;
	
	public static final boolean DEBUG = true;
	
	
	public static final String UP_HOST_DEBUG = "http://api.rottentomatoes.com/api/public/v1.0/";
	public static final String UP_HOST_RELEASE = "http://api.rottentomatoes.com/api/public/v1.0/";
	
	public static final String EXTRA_STATUS_RECEIVER = "com.up.upmovies.service.STATUS_RECEIVER";
	public static final String EXTRA_REQUEST_TYPE = "com.up.upmovies.service.REQUEST_TYPE";
	public static final String EXTRA_REQUEST_DATA = "com.up.upmovies.service.REQUEST_DATA";
	
	public static final String EXTRA_REQUEST_DATA_APIKEY = "API_KEY";
	public static final String EXTRA_REQUEST_DATA_PAGE_LIMIT = "PAGE_LIMIT";
	public static final String EXTRA_REQUEST_DATA_COUNTRY = "COUNTRY";
	public static final String EXTRA_REQUEST_DATA_PAGE = "PAGE";
	
	public static final String DEFAULT_FONT = "fonts/RobotoRegular.ttf";
	public static final String CUSTOM_ATTR_SCHEMAS = "http://schemas.android.com/apk/res/com.up.upmovies";
	
	public static final String ERROR_MESSAGE_EXTRA_BUNDLE = "error";
	public static final String ERROR_CODE_EXTRA_BUNDLE = "error_code";
	
	public static final String UPCOMING_MOVIES_LIST = "upcoming_movies_list";
	
	public static final String SERVER_API_VERSION = "";
	
	public static final int CONNECTION_TIMEOUT = 20 * 1000;
	
	public static final String API_KEY = "dd2z5y4mdxzbu6744ne2xnv4";
	
	public static final int MAX_TEXT_LENGTH = 200;
	
}
