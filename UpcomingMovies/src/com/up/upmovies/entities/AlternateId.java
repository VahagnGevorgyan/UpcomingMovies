package com.up.upmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;


public class AlternateId extends ParcelableObject {
	
	public static Parcelable.Creator<AlternateId> CREATOR = new ParcelCreator();
	
	private String name;
	private String value;
	
	private static class ParcelCreator implements Parcelable.Creator<AlternateId> {
		public ParcelCreator() { }

		@Override
		public AlternateId createFromParcel(final Parcel source) {
			return new AlternateId(source);
		}

		@Override
		public AlternateId[] newArray(final int size) {
			return new AlternateId[size];
		}
	}
	
	public AlternateId(String name, String value) {
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
	
	
	public AlternateId(final Parcel source) {
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
