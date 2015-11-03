package com.up.upmovies.service;

public class ServiceErrorCodes {
	
	public static final int UP_REQUEST_SUCCESS = 0;
	public static final int UP_REQUEST_FAILED = UP_REQUEST_SUCCESS + 1;
	public static final int UP_REQUEST_FAILED_TIMEOUT = UP_REQUEST_SUCCESS + 2;
	public static final int UP_REQUEST_FAILED_NO_NETWORK = UP_REQUEST_SUCCESS + 3;
	public static final int UP_REQUEST_INVALID_UID = UP_REQUEST_SUCCESS + 4;
	public static final int UP_REQUEST_PARSING_ERROR = UP_REQUEST_SUCCESS + 5;

}
