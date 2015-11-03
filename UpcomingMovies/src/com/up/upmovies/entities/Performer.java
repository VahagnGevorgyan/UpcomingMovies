package com.up.upmovies.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.up.upmovies.utils.JsonParser;

import android.os.Parcel;
import android.os.Parcelable;


public class Performer extends ParcelableObject {
	
	public static Parcelable.Creator<Performer> CREATOR = new ParcelCreator();
	
	private String name;
	private String id;
	private String[] characters;
	
	private static class ParcelCreator implements Parcelable.Creator<Performer> {
		public ParcelCreator() { }

		@Override
		public Performer createFromParcel(final Parcel source) {
			return new Performer(source);
		}

		@Override
		public Performer[] newArray(final int size) {
			return new Performer[size];
		}
	}
	
	public Performer(final JSONObject obj) throws JSONException {
		if(obj.has("id") && !obj.isNull("id")) {
			this.id = JsonParser.getString(obj, "id");
		}
		if(obj.has("name") && !obj.isNull("name")) {
			this.name = JsonParser.getString(obj, "name");
		}
		if(obj.has("characters") && !obj.isNull("characters")) {
			JSONArray jsonArray = obj.getJSONArray("characters");
			if(jsonArray != null) {
				final int len = jsonArray.length();
				this.characters = new String[len];
				for (int index = 0; index < len; index++) {
					this.characters[index] = jsonArray.get(index).toString();
				}
			}
		}
	}
	
	public Performer(String name, String id) {
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getCharacters() {
		return characters;
	}
	public void setCharacters(String[] characters) {
		this.characters = characters;
	}

	public Performer(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		this.name = in.readString();
		this.id = in.readString();
		int size = in.readInt();
		this.characters = new String[size];
		for( int i=0; i<size; i++ ){
			this.characters[i] = in.readString();
		}
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(this.name);
		out.writeString(this.id);
		out.writeInt(this.characters.length);
		for( int i=0; i<this.characters.length; i++ ){
		    out.writeString(this.characters[i]);
		}
	}
	

}
