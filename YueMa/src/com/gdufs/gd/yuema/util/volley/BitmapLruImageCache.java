package com.gdufs.gd.yuema.util.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * 基于LruCache实现的图片缓存类，内存缓存
 * 
 * @author Administrator
 * 
 */
public class BitmapLruImageCache extends LruCache<String, Bitmap> implements
		ImageCache {

	private final String TAG = this.getClass().getSimpleName();

	public BitmapLruImageCache(int maxSize) {
		super(maxSize);
	}

	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public Bitmap getBitmap(String url) {
		Log.v(TAG, "Retrieved item from Mem Cache");
		return get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		Log.v(TAG, "Added item to Mem Cache");
		put(url, bitmap);
	}
}
