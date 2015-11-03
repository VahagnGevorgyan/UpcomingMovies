package com.up.upmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.up.upmovies.CommandHelper;
import com.up.upmovies.Config;
import com.up.upmovies.R;
import com.up.upmovies.UpApplication;
import com.up.upmovies.entities.MovieList;
import com.up.upmovies.service.UpIntentService.RequestTypes;
import com.up.upmovies.service.UpIntentService.ServiceStatus;
import com.up.upmovies.utils.Logger;
import com.up.upmovies.utils.Utils;

public class SplashActivity extends BaseActivity {
	
	private static final String TAG = SplashActivity.class.getSimpleName();
	private boolean noConnected = false;
	private Class<?> mClassActivityToStart = MainActivity.class;
	
	private final Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// set content view
		setContentView(R.layout.activity_splash);
		
		// check network status
		if (Utils.checkNetworkStatus()) {
			// request upcoming movies
			CommandHelper.getInstance().RequestUpcomingList(this, this.serviceReceiver);
		} else {
			Toast.makeText(this, getResources().getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show();
			startSubsequentActivity(3000);
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		
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
					// START SUBSEQUENT ACTIVITY
					startSubsequentActivity(3000);
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(noConnected) {
			super.onBackPressed();
		}
	}
	
	private void startSubsequentActivity(int timeOut) {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, mClassActivityToStart);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.activity_fade_in, R.anim.activity_fade_out);
			}
		}, timeOut);
	}

}
