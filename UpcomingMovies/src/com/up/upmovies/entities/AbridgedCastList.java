package com.up.upmovies.entities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.Parcel;
import android.os.Parcelable;


public class AbridgedCastList extends ParcelableObject {
	
	public static Parcelable.Creator<AbridgedCastList> CREATOR = new ParcelCreator();
	public ArrayList<Performer> castList = new ArrayList<Performer>();
	
	private static class ParcelCreator implements Parcelable.Creator<AbridgedCastList> {
		
		public ParcelCreator() { }
		
		@Override
		public AbridgedCastList createFromParcel(final Parcel source) {
			return new AbridgedCastList(source);
		}
		
		@Override
		public AbridgedCastList[] newArray(final int size) {
			return new AbridgedCastList[size];
		}
	}
	
	public AbridgedCastList(final JSONArray arr) throws JSONException {
		if (arr != null) {
			final int len = arr.length();
			if (arr != null) {
				for (int index = 0; index < len; index++) {
					this.castList.add(new Performer(arr.getJSONObject(index)));
				}
			}
		}
	}
	
	public boolean hasValue() {
		return this.castList != null && this.castList.size() > 0; 
	}
	
	public AbridgedCastList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.castList, Performer.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.castList);
	}

}