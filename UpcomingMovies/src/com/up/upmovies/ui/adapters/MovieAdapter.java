package com.up.upmovies.ui.adapters;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.up.upmovies.Config;
import com.up.upmovies.R;
import com.up.upmovies.entities.AbridgedCastList;
import com.up.upmovies.entities.Movie;
import com.up.upmovies.entities.RatingsList;
import com.up.upmovies.ui.utils.DynamicHeightImageView;
import com.up.upmovies.utils.ImageHelper;
import com.up.upmovies.utils.Logger;


public class MovieAdapter extends ArrayAdapter<Movie> {
	
	private final static String TAG = MovieAdapter.class.getSimpleName(); 
	
	private final List<Movie> movies;
	private final Activity context;
	private int bgColor;

	@SuppressWarnings("deprecation")
	public MovieAdapter(Activity context, List<Movie> movies) {
		super(context, R.layout.list_item_movie, movies);
		this.context = context;
		this.movies = movies;
		this.bgColor = context.getResources().getColor(R.color.image_background);
	}

	// Class to save to an external class and to 
	// limit the access of the descendants of the class
	static class ViewHolder {
		public DynamicHeightImageView imageViewThumb;
		public TextView textViewTitleYear;
		public TextView textViewRatings;
		public TextView textViewCharacters;
		public TextView textViewSynopsis;
	}
	
	public final int getCount() {
    	return movies.size();
    }

    public final Movie getItem(int position) {
        return movies.get(position);
    }

    public final long getItemId(int position) {
        return position;
    }

	@SuppressLint("InflateParams") @Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.list_item_movie, null, true);
			holder = new ViewHolder();
			holder.imageViewThumb = (DynamicHeightImageView) rowView.findViewById(R.id.imageViewThumb);
			holder.textViewTitleYear = (TextView) rowView.findViewById(R.id.textViewTitleYear);
			holder.textViewRatings = (TextView) rowView.findViewById(R.id.textViewRatings);
			holder.textViewCharacters = (TextView) rowView.findViewById(R.id.textViewCharacters);
			holder.textViewSynopsis = (TextView) rowView.findViewById(R.id.textViewSynopsis);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}

		holder.textViewTitleYear.setText(movies.get(position).getTitle() + " (" + String.valueOf(movies.get(position).getYear()) + ")");

		String ratings = "";
		RatingsList rList = movies.get(position).getRatings();
		if(rList != null && rList.hasValue()) {
			for(int i = 0; i<rList.ratingList.size(); i++) {
				if(i>0)
					ratings += ", ";
				ratings += rList.ratingList.get(i).getName() + ":" + rList.ratingList.get(i).getValue();
			}
		}
		if(Config.DEBUG)Logger.i(TAG,":: MovieAdapter.getView : Ratings : " + ratings);
		holder.textViewRatings.setText(ratings);
		
		String characters = "";
		AbridgedCastList cList = movies.get(position).getAbridged_cast();
		if(cList != null && cList.hasValue()) {
			for(int i = 0; i<cList.castList.size(); i++) {
				if(i>3)
					break;
				if(i>0)
					characters += ", ";
				characters += cList.castList.get(i).getName();
			}
		}
		if(Config.DEBUG)Logger.i(TAG,":: MovieAdapter.getView : characters : " + characters);
		holder.textViewCharacters.setText(characters);
		String syn = movies.get(position).getSynopsis();
		if(syn != null && syn.length() > Config.MAX_TEXT_LENGTH) {
			syn = syn.substring(0, Math.min(syn.length(), Config.MAX_TEXT_LENGTH));
			syn += "...";
		}
		holder.textViewSynopsis.setText(syn + "\n");
		
		
		holder.imageViewThumb.setImageBitmap(null);
		holder.imageViewThumb.setBackgroundColor(bgColor);
		holder.imageViewThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
		
		String urlThumb = movies.get(position).getPosters().posterList.get(0).getValue(); 
		if(Config.DEBUG)Logger.i(TAG,":: MovieAdapter.getView : urlThumb : " + urlThumb);
		Bitmap bitmap = ImageHelper.memCache.getBitmapFromMemCache(urlThumb);
		if (bitmap != null) {
			holder.imageViewThumb.setImageBitmap(bitmap);
		} else {
			ImageHelper.loadImagiesByLoader(context, urlThumb, holder.imageViewThumb);
		}
		
		return rowView;
	}
	
}
