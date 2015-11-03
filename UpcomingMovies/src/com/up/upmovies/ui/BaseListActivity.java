package com.up.upmovies.ui;

import java.util.LinkedList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.up.upmovies.Config;
import com.up.upmovies.service.DetachableResultsReceiver;
import com.up.upmovies.service.DetachableResultsReceiver.OnReceiveResultListener;
import com.up.upmovies.utils.Logger;

public class BaseListActivity extends ListActivity implements OnReceiveResultListener {
	
	static final String TAG = BaseActivity.class.getSimpleName();
	
	protected final DetachableResultsReceiver serviceReceiver = new DetachableResultsReceiver(new Handler());
	
	protected LinkedList<OnDataRetrievedListener> dataListeners = new LinkedList<OnDataRetrievedListener>();
	
	/**
	 * Interface used to notify activity's data fragments the data have been
	 * retrieved
	 */
	public interface OnDataRetrievedListener {
		public void OnDataRetrieved();
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onActivityResult ");
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onCreate ");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onDestroy ");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onPause ");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onResume ");
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		if(Config.DEBUG)Logger.i(TAG, ":: BaseActivity.onReceiveResult ");
	}

	@Override
	protected void onStart() {
		super.onStart();
		// set receiver
		this.serviceReceiver.setListener(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// clear receiver
		this.serviceReceiver.clearListener();
	}

}
