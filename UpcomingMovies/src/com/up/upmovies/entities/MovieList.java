package com.up.upmovies.entities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.up.upmovies.Config;
import com.up.upmovies.utils.Logger;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieList extends ParcelableObject {
	
	public static Parcelable.Creator<MovieList> CREATOR = new ParcelCreator();
	public ArrayList<Movie> movieList = new ArrayList<Movie>();
	
	private static class ParcelCreator implements Parcelable.Creator<MovieList> {
		
		public ParcelCreator() { }
		
		@Override
		public MovieList createFromParcel(final Parcel source) {
			return new MovieList(source);
		}
		
		@Override
		public MovieList[] newArray(final int size) {
			return new MovieList[size];
		}
	}
	
	public MovieList(final JSONArray arr) throws JSONException {
		if (arr != null) {
			final int len = arr.length();
			if (arr != null) {
				for (int index = 0; index < len; index++) {
					if(Config.DEBUG)Logger.i(TAG, ":: MovieList.Constructor : index : " + index);
					this.movieList.add(new Movie(arr.getJSONObject(index)));
				}
			}
		}
	}
	
	public boolean hasValue() {
		return this.movieList != null && this.movieList.size() > 0; 
	}
	
	public MovieList(final Parcel source) {
		readFromParcel(source);
	}

	public MovieList() {
		
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.movieList, Movie.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.movieList);
	}

}
