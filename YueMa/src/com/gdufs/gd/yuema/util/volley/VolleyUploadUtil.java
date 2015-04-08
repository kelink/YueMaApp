package com.gdufs.gd.yuema.util.volley;

import java.io.File;
import java.util.Map;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

/**
 * 基于Volley 的文件上传类(支持图文上传)
 * 
 * 回调方法，需要自己定义volley的失败和成功的监听器传入
 * 
 * @author Administrator
 * 
 */
public class VolleyUploadUtil {
	private static RequestQueue mSingleQueue;

	public static RequestQueue getmSingleQueue() {
		return mSingleQueue;
	}

	public static void uploadFiles(final String url,
			final Map<String, File> files, final Map<String, String> params,
			final Listener<String> responseListener,
			final ErrorListener errorListener, final Object tag, Context context) {
		if (mSingleQueue == null) {
			mSingleQueue = Volley
					.newRequestQueue(context, new MultiPartStack());
		}
		if (null == url || null == responseListener) {
			return;
		}
		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				Request.Method.POST, url, responseListener, errorListener) {

			@Override
			public Map<String, File> getFileUploads() {
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				return params;
			}

		};
		mSingleQueue.add(multiPartRequest);
	}
}
