package com.up.upmovies.ui;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ListView;

import com.up.upmovies.CommandHelper;
import com.up.upmovies.Config;
import com.up.upmovies.R;
import com.up.upmovies.UpApplication;
import com.up.upmovies.entities.MovieList;
import com.up.upmovies.service.DetachableResultsReceiver.OnReceiveResultListener;
import com.up.upmovies.service.UpIntentService.RequestTypes;
import com.up.upmovies.service.UpIntentService.ServiceStatus;
import com.up.upmovies.ui.adapters.MovieAdapter;
import com.up.upmovies.ui.utils.PullToRefreshBase;
import com.up.upmovies.ui.utils.PullToRefreshBase.OnLastItemVisibleListener;
import com.up.upmovies.ui.utils.PullToRefreshBase.OnRefreshListener;
import com.up.upmovies.ui.utils.PullToRefreshListView;
import com.up.upmovies.utils.Logger;


public class MainActivity extends BaseListActivity implements OnReceiveResultListener {


	private PullToRefreshListView mPullRefreshListView;
	private MovieAdapter mAdapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				// request upcoming movies
				CommandHelper.getInstance().RequestUpcomingList(MainActivity.this, MainActivity.this.serviceReceiver);
			}
		});

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				
			}
		});

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		
		mAdapter = new MovieAdapter(this, UpApplication.getMovieList().movieList);
		setListAdapter(mAdapter);

    }
	
	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		super.onReceiveResult(resultCode, resultData);
		
		final int reqType = resultData.getInt(Config.EXTRA_REQUEST_TYPE);
		
		switch (resultCode) {
		case ServiceStatus.RUNNING:
			if(Config.DEBUG)Logger.i(TAG, ":: SplashActivity.onReceiveResult : SERVICE STATUS : RUNNING");
			break;
		case ServiceStatus.RESULT:
			if(Config.DEBUG)Logger.i(TAG, ":: SplashActivity.onReceiveResult : SERVICE STATUS : RESULT");
			break;
		case ServiceStatus.FINISHED:
			if(Config.DEBUG)Logger.i(TAG, ":: SplashActivity.onReceiveResult : SERVICE STATUS : FINISHED");
			if(reqType == RequestTypes.UPCOMING_MOVIES) {
				if(resultData != null) {
					MovieList movieList = resultData.getParcelable(Config.UPCOMING_MOVIES_LIST);
					UpApplication.setMovieList(movieList);
					// refresh list
					mAdapter.notifyDataSetChanged();
					// Call onRefreshComplete when the list has been refreshed.
					mPullRefreshListView.onRefreshComplete();
				}
			}
			break;
		default:
			break;
		}
	}

}
