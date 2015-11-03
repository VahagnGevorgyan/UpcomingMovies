package com.up.upmovies.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class ReleaseDatesList extends ParcelableObject {
	
	public static Parcelable.Creator<ReleaseDatesList> CREATOR = new ParcelCreator();
	public ArrayList<ReleaseDate> releaseDatesList = new ArrayList<ReleaseDate>();
	
	private static class ParcelCreator implements Parcelable.Creator<ReleaseDatesList> {
		
		public ParcelCreator() { }
		
		@Override
		public ReleaseDatesList createFromParcel(final Parcel source) {
			return new ReleaseDatesList(source);
		}
		
		@Override
		public ReleaseDatesList[] newArray(final int size) {
			return new ReleaseDatesList[size];
		}
	}
	
	public ReleaseDatesList(final JSONArray arr) throws JSONException {
		if (arr != null) {
			final int len = arr.length();
			if (arr != null) {
				for (int index = 0; index < len; index++) {
					JSONObject jsonObject = arr.getJSONObject(index);
					Iterator<String> keysItr = jsonObject.keys();
					while(keysItr.hasNext()) {
				        String key = keysItr.next();
				        Object value = jsonObject.get(key);
				        this.releaseDatesList.add(new ReleaseDate(key, value!=null ? value.toString() : ""));
			        }
				}
			}
		}
	}
	
	public boolean hasValue() {
		return this.releaseDatesList != null && this.releaseDatesList.size() > 0; 
	}
	
	public ReleaseDatesList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.releaseDatesList, ReleaseDate.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.releaseDatesList);
	}

}
