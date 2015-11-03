package com.up.upmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonParser {
	
	/**
	 * Parse JSON
	 */
	public static Object parse(final String entity) throws JSONException {
		final JSONTokener tokener = new JSONTokener(entity);

		return tokener.nextValue();
	}

	/**
	 * Parse JSON
	 */
	public static JSONTokener getTokener(final String entity)
			throws JSONException {
		return new JSONTokener(entity);
	}

	/**
	 * 
	 * @return
	 */
	public static JSONObject getObject(final JSONObject obj, final String name) {
		final Object result = get(obj, name);

		if (result != null && result instanceof JSONObject) {
			return (JSONObject) result;
		}

		return null;
	}

	public static JSONArray getArray(final JSONObject obj, final String name) {
		final Object result = get(obj, name);

		if (result != null && result instanceof JSONArray) {
			return (JSONArray) result;
		}

		return null;
	}

	/**
     *
     */
	public static int getInt(final JSONObject obj, final String name,
			final int defaultValue) {

		final Object result = get(obj, name);

		if (result != null && result instanceof Number) {
			return ((Number) result).intValue();
		}

		return defaultValue;
	}

	/**
     *
     */
	public static double getDouble(final JSONObject obj, final String name,
			final double defaultValue) {

		final Object result = get(obj, name);

		if (result != null && result instanceof Number) {
			return ((Number) result).doubleValue();
		}

		return defaultValue;
	}

	/**
     *
     */
	public static String getString(final JSONObject obj, final String name,
			final String defaultValue) {

		final Object result = get(obj, name);

		if (result != null && result instanceof String) {
			return (String) result;
		}

		return defaultValue;
	}
	
	/**
    *
    */
	public static Boolean getBoolean(final JSONObject obj, final String name) {

		final Object result = get(obj, name);

		if (result != null && result instanceof Boolean) {
			return (Boolean) result;
		}

		return null;
	}


	/**
     *
     */
	public static int getInt(final JSONObject obj, final String name)
			throws JSONException {

		final Object result = getOrThrow(obj, name);

		if (!(result instanceof Number)) {
			throw new JSONException("Not a Number");
		}

		return ((Number) result).intValue();
	}
	
	/**
    *
    */
	public static long getLong(final JSONObject obj, final String name)
			throws JSONException {

		final Object result = getOrThrow(obj, name);

		if (!(result instanceof Number)) {
			throw new JSONException("Not a Number");
		}

		return ((Number) result).longValue();
	}

	/**
     *
     */
	public static int getStringAsInt(final JSONObject obj, final String name)
			throws JSONException {

		final Object result = getOrThrow(obj, name);

		try {
			return Integer.valueOf((String) result);
		} catch (NumberFormatException nfe) {
			throw new JSONException("Not a Number");
		}
	}

	/**
     *
     */
	public static double getDouble(final JSONObject obj, final String name)
			throws JSONException {

		final Object result = getOrThrow(obj, name);

		if (!(result instanceof Number)) {
			throw new JSONException("Not a Number");
		}

		return ((Number) result).doubleValue();
	}

	public static double getStringAsDouble(final JSONObject obj,
			final String name) throws JSONException {

		final Object result = getOrThrow(obj, name);

		try {
			return Double.valueOf((String) result);
		} catch (NumberFormatException nfe) {
			throw new JSONException("Not a Number");
		}
	}

	public static double getStringAsDoubleDef(final JSONObject obj,
			final String name, double defaultValue) {
		try {
			return getStringAsDouble(obj, name);
		} catch (JSONException nfe) {
			return defaultValue;
		}
	}

	/**
     *
     */
	public static String getString(final JSONObject obj, final String name)
			throws JSONException {

		final Object result = getOrThrow(obj, name);

		if (!(result instanceof String)) {
			throw new JSONException("Not a String. Param=" + name);
		}

		return (String) result;
	}

	/**
     *	
     */
	private static Object get(final JSONObject obj, final String name) {
		Object result = null;
		try {
			result = obj.get(name);
			if (result != null && result.equals(JSONObject.NULL)) {
				result = null;
			}
		} catch (final JSONException e) {
			// nothing to do here
		}
		return result;
	}

	/**
     * 	
     */
	private static Object getOrThrow(final JSONObject obj, final String name)
			throws JSONException {

		Object result = null;

		result = obj.get(name);

		if (result == null || result.equals(JSONObject.NULL)) {
			throw new ObjectNotFoundJSONException(name);
		}

		return result;
	}

	public static JSONArray getArrayOrThrow(final JSONObject obj,
			final String name) throws JSONException {

		final Object result = getOrThrow(obj, name);

		if (!(result instanceof JSONArray)) {
			throw new JSONException("Not a JSONArray");
		}

		return (JSONArray) result;
	}
	
	
}
