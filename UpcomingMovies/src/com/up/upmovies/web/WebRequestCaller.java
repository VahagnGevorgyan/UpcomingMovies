package com.up.upmovies.web;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.up.upmovies.Config;
import com.up.upmovies.utils.Logger;

import android.os.Bundle;


public class WebRequestCaller {

	private static final String TAG = WebRequestCaller.class.getSimpleName();
	
	private static WebRequestCaller caller = null;
	
	public synchronized static void init() {
		if (null == caller) {
			caller = new WebRequestCaller();
		}
	}
	
	public static WebRequestCaller getCaller() {
		if(null == caller) {
			init();
		}
		return caller;
	}
	
	/**
	 * Executes the request
	 * 
	 * @param request
	 *            - request
	 * @param handler
	 *            - Listener for handle response
	 * @param httpContext
	 *            - the context to use for the execution, or null to use the
	 *            default context
	 * @throws Exception
	 */
	public static Bundle executeRequest(final WebRequest request,
			final WebResponseHandler handler)
			throws Exception {
		int requestStatusCode = WebRequestStatus.UP_REQUEST_FAILED;
		
		final WebResponse response = new WebResponse();
		Bundle executeResultBundle = null;
		final long startRemote = System.currentTimeMillis();
		long startRemoteCurr = startRemote;

		if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : START EXECUTION");

		try {
			if (request == null || !request.isValid()) {
				throw new IllegalArgumentException("Invalid request");
			}

			if (null == caller) {
				throw new IllegalStateException("Caller is not inited");
			}

			switch (request.httpMethod) {
				case GET:
					sendGet(response, request.uri.toURL());
					break;
				case POST:
					sendPost(response, request);
					break;
				default:
					throw new IllegalArgumentException("Invalid HTTP method");
			}

			// get request status
			requestStatusCode = response.statusLine;

			long l = System.currentTimeMillis();
			if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : create req took : " + (l - startRemoteCurr) + "ms");
			startRemoteCurr = l;
			
			l = System.currentTimeMillis();

			if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : execute took  : " + (l - startRemoteCurr) + "ms");
			if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : execute took from start : " + (l - startRemote) + "ms");
			
			startRemoteCurr = l;
			
			if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : RESPONSE: " + response.entityString);

		} catch (final InterruptedIOException e) {
			requestStatusCode = WebRequestStatus.UP_REQUEST_FAILED_TIMEOUT;
			if(Config.DEBUG)Logger.e(TAG,":: WebRequestCaller : Error : " + e.toString());
			throw new Exception(e.toString());
		} catch (final IOException e) {
			requestStatusCode = WebRequestStatus.UP_REQUEST_FAILED;
			if(Config.DEBUG)Logger.e(TAG,":: WebRequestCaller : Error : " + e.toString());
			throw new Exception(e.toString());
		} catch (final IllegalArgumentException e) {
			requestStatusCode = WebRequestStatus.UP_REQUEST_FAILED;
			if(Config.DEBUG)Logger.e(TAG,":: WebRequestCaller : Error : " + e.toString());
			throw new Exception(e.toString());
		} catch (final Exception e) {
			requestStatusCode = WebRequestStatus.UP_REQUEST_FAILED;
			if(Config.DEBUG)Logger.e(TAG,":: WebRequestCaller : Error : " + e.toString());
			throw new Exception(e.toString());
		}

		if(Config.DEBUG)Logger.i(TAG, ":: WebRequestCaller.executeRequest : finish execute took from start: " 
				+(System.currentTimeMillis() - startRemoteCurr)
				+ "ms");

		if (null != handler) {
			executeResultBundle = handler.handleResponse(requestStatusCode, response);
		}
		return executeResultBundle;
		
	} // end method executeRequest
	
	public static void closeQuietly(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			if(Config.DEBUG)Logger.e(TAG,":: WebRequestCaller : Error : could not close stream : " + e.toString());
		}
	}
	
	// HTTP GET request
	private static void sendGet(WebResponse res, URL url) throws Exception {

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept-Charset", "UTF-8");

		int responseCode = con.getResponseCode();
		res.headers = con.getHeaderFields();
		res.statusLine = responseCode;
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		res.entityString = response.toString();

	} // end method sendGet
		
	// HTTP POST request
	private static void sendPost(WebResponse res, WebRequest req) throws Exception {

		HttpURLConnection con = (HttpURLConnection) req.uri.toURL().openConnection();
		
		//add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept-Charset", "UTF-8");
		con.setRequestProperty("Content-type", "application/json");
		
		// Send post request
		con.setDoOutput(true);
		con.setDoInput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		
        wr.writeBytes(req.entityObject.toString());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		res.headers = con.getHeaderFields();
		res.statusLine = responseCode;

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		res.entityString = response.toString();

	} // end method sendPost
	
}
