package com.up.upmovies.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class RatingsList extends ParcelableObject {
	
	public static Parcelable.Creator<RatingsList> CREATOR = new ParcelCreator();
	public ArrayList<Rating> ratingList = new ArrayList<Rating>();
	
	private static class ParcelCreator implements Parcelable.Creator<RatingsList> {
		
		public ParcelCreator() { }
		
		@Override
		public RatingsList createFromParcel(final Parcel source) {
			return new RatingsList(source);
		}
		
		@Override
		public RatingsList[] newArray(final int size) {
			return new RatingsList[size];
		}
	}
	
	public RatingsList(final JSONObject obj) throws JSONException {
		if (obj != null) {
			Iterator<String> keysItr = obj.keys();
			while(keysItr.hasNext()) {
		        String key = keysItr.next();
		        Object value = obj.get(key);
		        this.ratingList.add(new Rating(key, value!=null ? value.toString() : ""));
	        }
		}
	}
	
	public boolean hasValue() {
		return this.ratingList != null && this.ratingList.size() > 0; 
	}
	
	public RatingsList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.ratingList, Rating.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.ratingList);
	}

}
