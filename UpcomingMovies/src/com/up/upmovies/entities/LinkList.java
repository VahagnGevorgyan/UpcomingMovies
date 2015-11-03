package com.up.upmovies.entities;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;


public class LinkList extends ParcelableObject {
	
	public static Parcelable.Creator<LinkList> CREATOR = new ParcelCreator();
	public ArrayList<Link> linkList = new ArrayList<Link>();
	
	private static class ParcelCreator implements Parcelable.Creator<LinkList> {
		
		public ParcelCreator() { }
		
		@Override
		public LinkList createFromParcel(final Parcel source) {
			return new LinkList(source);
		}
		
		@Override
		public LinkList[] newArray(final int size) {
			return new LinkList[size];
		}
	}
	
	public LinkList(final JSONArray arr) throws JSONException {
		if (arr != null) {
			final int len = arr.length();
			if (arr != null) {
				for (int index = 0; index < len; index++) {
					JSONObject jsonObject = arr.getJSONObject(index);
					Iterator<String> keysItr = jsonObject.keys();
					while(keysItr.hasNext()) {
				        String key = keysItr.next();
				        Object value = jsonObject.get(key);
				        this.linkList.add(new Link(key, value!=null ? value.toString() : ""));
			        }
				}
			}
		}
	}
	
	public boolean hasValue() {
		return this.linkList != null && this.linkList.size() > 0; 
	}
	
	public LinkList(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		in.readTypedList(this.linkList, Link.CREATOR);
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeTypedList(this.linkList);
	}

}
