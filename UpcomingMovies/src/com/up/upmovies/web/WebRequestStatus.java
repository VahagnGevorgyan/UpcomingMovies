package com.up.upmovies.web;

public class WebRequestStatus {
	public static final int UP_REQUEST_SUCCESS = 1000;
	public static final int UP_REQUEST_FAILED = UP_REQUEST_SUCCESS + 1;
	public static final int UP_REQUEST_FAILED_TIMEOUT = UP_REQUEST_SUCCESS + 2;
	public static final int UP_REQUEST_FAILED_NO_NETWORK = UP_REQUEST_SUCCESS + 3;
	public static final int UP_REQUEST_INVALID_TOKEN = UP_REQUEST_SUCCESS + 4;
}
