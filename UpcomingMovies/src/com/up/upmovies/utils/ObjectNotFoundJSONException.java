package com.up.upmovies.utils;

import org.json.JSONException;

public class ObjectNotFoundJSONException extends JSONException {

	private static final long serialVersionUID = -7824311803141390188L;

	public ObjectNotFoundJSONException(String objectName) {
        super("Object " + objectName + " not found");
    }
}
