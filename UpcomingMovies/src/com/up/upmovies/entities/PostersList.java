package com.up.upmovies.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class PostersList extends ParcelableObject {
	
	public static Parcelable.Creator<PostersList> CREATOR = new ParcelCreator();
	public ArrayList<Poster> posterList = new ArrayList<Poster>();
	
	private static class ParcelCreator implements Parcelable.Creator<PostersList> {
		
		public ParcelCreator() { }
		
		@Override
		public PostersList createFromParcel(final Parcel source) {
			return new PostersList(source);
		}
		
		@Override
		public PostersList[] newArray(final int size) {
			return new PostersList[size];
		}
	}
	
	public PostersList(final JSONObject obj) throws JSONException {
		if (obj != null) {
			Iterator<String> keysItr = obj.keys();
			while(keysItr.hasNext()) {
		        String key = keysItr.next();
		        Object value = obj.get(key);
		        this.posterList.add(new Poster(key, value!=null ? value.toString() : ""));
	        }
		}
	}
	
	public boolean hasValue() {
		return this.posterList != null && this.posterList.size() > 0; 
	}
	
	public PostersList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.posterList, Poster.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.posterList);
	}

}
