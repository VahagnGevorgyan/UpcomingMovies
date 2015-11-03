package com.up.upmovies.utils;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

@SuppressLint("NewApi") public class MemCache {
	
	private LruCache<String, byte[]> mMemoryBitmapCache;
	private LruCache<String, Bitmap> mBitmapCache;
	private Map<String, Object> mMemorySimpleCache;

	public MemCache() {
		
		// Get max available VM memory, exceeding this amount will throw an
	    // OutOfMemory exception. Stored in kilobytes as LruCache takes an
	    // int in its constructor.
	    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = maxMemory / 8;

	    mMemoryBitmapCache = new LruCache<String, byte[]>(cacheSize) {
	        @Override
	        protected int sizeOf(String key, byte[] bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.length / 1024;
	        }
	    };
	    
	    mMemorySimpleCache = new HashMap<String, Object>();
	    
	    // Use 1/8th of the available memory for this memory cache.
	    final int cSize = maxMemory / 8;

	    mBitmapCache = new LruCache<String, Bitmap>(cSize) {
			@Override
	        protected int sizeOf(String key, Bitmap bitmap) {
	            // The cache size will be measured in kilobytes rather than
	            // number of items.
	            return bitmap.getByteCount() / 1024;
	        }
	    };
	}
	
	public void removeObjectFromCache(String key) {
		mMemorySimpleCache.remove(key);
	}
	
	public Object getObject(String key) {
		if (key.startsWith("http://")) {
			return mMemorySimpleCache.get(key);
		}
		return null;
	}
	
	public Object getObjectFromCache(String key) {
		return mMemorySimpleCache.get(key);
	}
	
	public byte[] getBitmapFile(String key) {
//		if (DBAdapter.urlMap == null) {
//			try {
//				DBAdapter dbAdapter = new DBAdapter();
//				dbAdapter.syncDb();
//				dbAdapter.close();
//			} catch (Exception e) {
//				
//			}
//		} else  if (DBAdapter.urlMap.get(key) == null) {
//			return null;
//		}
//		
		byte[] bitmap = null;
//		
//		DBAdapter dbAdapter = new DBAdapter();
//		try {
//			dbAdapter.open(Mode.READ);
//			//this part is for image synchronization
//			bitmap = dbAdapter.get(key);
//		} catch (Exception e) {
//			Log.e(TAG, "getBitmapFile() " + e.toString());
//		} finally {
//			try {
//				dbAdapter.close();
//			} catch (Exception e) {
//				Log.e(TAG, "getBitmapFile() " + e.toString());
//			}
//		}
		
		return bitmap;
	}
	
	public byte[] getBitmap(String key) {
		byte[] bitmap = mMemoryBitmapCache.get(key);
		return bitmap;
	}
	
	public byte[] getBitmapQuick(String key) {
		byte[] b = mMemoryBitmapCache.get(key);
		return b;
	}
	
	public void put(String key, Object value) {
		if (value instanceof Bitmap) {
			mMemoryBitmapCache.put(key, (byte[])value);
		} else {
			mMemorySimpleCache.put(key, value);
		}
	}
	
	public void putImage(String key, byte[] value) {
		mMemoryBitmapCache.put(key, value);
	}
	
	public byte[] getImage(String key) {
		return mMemoryBitmapCache.get(key);
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {
	        mBitmapCache.put(key, bitmap);
	    }
	}
	
	public Bitmap getBitmapFromMemCache(String key) {
	    return mBitmapCache.get(key);
	}
}
