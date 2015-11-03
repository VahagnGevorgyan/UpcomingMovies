package com.up.upmovies.entities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.up.upmovies.utils.JsonParser;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie extends ParcelableObject {
	
	public static Parcelable.Creator<Movie> CREATOR = new ParcelCreator();
	
	private long id;
	private String title;
	private int year;
	private String mpaa_rating;
	private int runtime = 0;
	private String critics_consensus;
	private String synopsis;
	private RatingsList ratings;
	private PostersList posters;
	private AbridgedCastList abridged_cast;
	private AlternateIdList alternate_ids;
	private LinkList links;
	private ReleaseDatesList release_dates;
	
	
	private static class ParcelCreator implements Parcelable.Creator<Movie> {
		public ParcelCreator() { }

		@Override
		public Movie createFromParcel(final Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(final int size) {
			return new Movie[size];
		}
	}
	
	public Movie(final JSONObject obj) throws JSONException {
		if(obj.has("id") && !obj.isNull("id")) {
			this.id = Long.parseLong(JsonParser.getString(obj, "id"));
		}
		if(obj.has("title") && !obj.isNull("title")) {
			this.title = JsonParser.getString(obj, "title");
		}
		if(obj.has("year") && !obj.isNull("year")) {
			this.year = JsonParser.getInt(obj, "year");
		}
		if(obj.has("mpaa_rating") && !obj.isNull("mpaa_rating")) {
			this.mpaa_rating = JsonParser.getString(obj, "mpaa_rating");
		}
		if(obj.has("runtime") && !obj.isNull("runtime")) {
			Object aObj = obj.get("runtime");
			if(aObj instanceof Integer) {
				this.runtime = JsonParser.getInt(obj, "runtime");
			} 
		}
		if(obj.has("critics_consensus") && !obj.isNull("critics_consensus")) {
			this.critics_consensus = JsonParser.getString(obj, "critics_consensus");
		}
		if(obj.has("synopsis") && !obj.isNull("synopsis")) {
			this.synopsis = JsonParser.getString(obj, "synopsis");
		}
		if (obj.has("ratings") && !obj.isNull("ratings")) {
			JSONObject ratings_array = obj.getJSONObject("ratings");
			this.setRatings(new RatingsList(ratings_array));
		}
		if(obj.has("posters") && !obj.isNull("posters")) {
			JSONObject posters_array = obj.getJSONObject("posters");
			this.setPosters(new PostersList(posters_array));
		}
		if(obj.has("abridged_cast") && !obj.isNull("abridged_cast")) {
			JSONArray abridged_cast = JsonParser.getArray(obj, "abridged_cast");
			this.setAbridged_cast(new AbridgedCastList(abridged_cast));
		}
		if(obj.has("alternate_ids") && !obj.isNull("alternate_ids")) {
			JSONArray alternate_ids = JsonParser.getArray(obj, "alternate_ids");
			this.setAlternate_ids(new AlternateIdList(alternate_ids));
		}
		if(obj.has("links") && !obj.isNull("links")) {
			JSONArray links = JsonParser.getArray(obj, "links");
			this.setLinks(new LinkList(links));
		}
		if(obj.has("release_dates") && !obj.isNull("release_dates")) {
			JSONArray release_dates = JsonParser.getArray(obj, "release_dates");
			this.setRelease_dates(new ReleaseDatesList(release_dates));
		}
		
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMpaa_rating() {
		return mpaa_rating;
	}
	public void setMpaa_rating(String mpaa_rating) {
		this.mpaa_rating = mpaa_rating;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public String getCritics_consensus() {
		return critics_consensus;
	}
	public void setCritics_consensus(String critics_consensus) {
		this.critics_consensus = critics_consensus;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public RatingsList getRatings() {
		return ratings;
	}
	public void setRatings(RatingsList ratings) {
		this.ratings = ratings;
	}
	public PostersList getPosters() {
		return posters;
	}
	public void setPosters(PostersList posters) {
		this.posters = posters;
	}
	public AbridgedCastList getAbridged_cast() {
		return abridged_cast;
	}
	public void setAbridged_cast(AbridgedCastList abridged_cast) {
		this.abridged_cast = abridged_cast;
	}
	public AlternateIdList getAlternate_ids() {
		return alternate_ids;
	}
	public void setAlternate_ids(AlternateIdList alternate_ids) {
		this.alternate_ids = alternate_ids;
	}
	public LinkList getLinks() {
		return links;
	}
	public void setLinks(LinkList links) {
		this.links = links;
	}
	public ReleaseDatesList getRelease_dates() {
		return release_dates;
	}
	public void setRelease_dates(ReleaseDatesList release_dates) {
		this.release_dates = release_dates;
	}

	public Movie(final Parcel source) {
		readFromParcel(source);
	}

	@Override
	public void readFromParcel(Parcel in) {
		this.id = in.readLong();
		this.title = in.readString();
		this.year = in.readInt();
		this.mpaa_rating = in.readString();
		this.runtime = in.readInt();
		this.critics_consensus = in.readString();
		this.synopsis = in.readString();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeLong(this.id);
		out.writeString(this.title);
		out.writeInt(this.year);
		out.writeString(this.mpaa_rating);
		out.writeInt(this.runtime);
		out.writeString(this.critics_consensus);
		out.writeString(this.synopsis);
	}

}
