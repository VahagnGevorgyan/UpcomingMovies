package com.up.upmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class ReleaseDate extends ParcelableObject {
	
	public static Parcelable.Creator<ReleaseDate> CREATOR = new ParcelCreator();
	
	private String name;
	private String value;
	
	private static class ParcelCreator implements Parcelable.Creator<ReleaseDate> {
		public ParcelCreator() { }

		@Override
		public ReleaseDate createFromParcel(final Parcel source) {
			return new ReleaseDate(source);
		}

		@Override
		public ReleaseDate[] newArray(final int size) {
			return new ReleaseDate[size];
		}
	}
	
	public ReleaseDate(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public ReleaseDate(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		this.name = in.readString();
		this.value = in.readString();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(this.name);
		out.writeString(this.value);
	}
	

}
