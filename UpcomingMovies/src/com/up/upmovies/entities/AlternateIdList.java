package com.up.upmovies.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class AlternateIdList extends ParcelableObject {
	
	public static Parcelable.Creator<AlternateIdList> CREATOR = new ParcelCreator();
	public ArrayList<AlternateId> alterList = new ArrayList<AlternateId>();
	
	private static class ParcelCreator implements Parcelable.Creator<AlternateIdList> {
		
		public ParcelCreator() { }
		
		@Override
		public AlternateIdList createFromParcel(final Parcel source) {
			return new AlternateIdList(source);
		}
		
		@Override
		public AlternateIdList[] newArray(final int size) {
			return new AlternateIdList[size];
		}
	}
	
	public AlternateIdList(final JSONArray arr) throws JSONException {
		if (arr != null) {
			final int len = arr.length();
			if (arr != null) {
				for (int index = 0; index < len; index++) {
					JSONObject jsonObject = arr.getJSONObject(index);
					Iterator<String> keysItr = jsonObject.keys();
					while(keysItr.hasNext()) {
				        String key = keysItr.next();
				        Object value = jsonObject.get(key);
				        this.alterList.add(new AlternateId(key, value!=null ? value.toString() : ""));
			        }
				}
			}
		}
	}
	
	public boolean hasValue() {
		return this.alterList != null && this.alterList.size() > 0; 
	}
	
	public AlternateIdList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.alterList, AlternateId.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.alterList);
	}

}
