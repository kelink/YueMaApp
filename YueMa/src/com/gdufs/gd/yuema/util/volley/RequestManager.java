package com.gdufs.gd.yuema.util.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 本类是使用volley 框架建立的频繁的HTTP 通讯的单例工具类 <li>依赖于volley.jar包</li>
 * 
 * @author Administrator
 * 
 */
public class RequestManager {
	private static RequestManager volleyManager;
	private RequestQueue mRequestQueue;
	private static Context mCtx;

	private RequestManager(Context context) {
		mCtx = context;
		mRequestQueue = getRequestQueue();
	}

	public static synchronized RequestManager getInstance(Context context) {
		if (volleyManager == null) {
			volleyManager = new RequestManager(context);
		}
		return volleyManager;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			// getApplicationContext()是关键, 它会避免
			// Activity或者BroadcastReceiver带来的缺点.
			mRequestQueue = Volley
					.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req) {
		getRequestQueue().add(req);
	}
}
