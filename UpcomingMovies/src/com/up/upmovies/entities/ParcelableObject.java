package com.up.upmovies.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableObject implements Parcelable {

	public final static int DEFAULT_VALUE_INT = -1;
    public final static String TAG = ParcelableObject.class.getSimpleName();

    public ParcelableObject() { }

    public ParcelableObject(final Parcel source) {
        readFromParcel(source);
    }

    @Override
    public void writeToParcel(final Parcel dest, final int flags) { }

    @Override
    public int describeContents() {
        return hashCode();
    }

    public void readFromParcel(Parcel in) { }

}