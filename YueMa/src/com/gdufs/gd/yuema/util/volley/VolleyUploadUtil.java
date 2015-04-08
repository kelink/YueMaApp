package com.gdufs.gd.yuema.util.volley;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.util.Log;

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

	private Context context;
	private int requestMethod;
	private String url;
	private Listener responseListener;
	private ErrorListener errorListener;
	private Map<String, File> files;
	private Map<String, String> params;

	public VolleyUploadUtil(Context context, int requestMethod, String url,
			Listener responseListener, ErrorListener errorListener,
			Map<String, File> files, Map<String, String> params) {
		this.context = context;
		this.requestMethod = requestMethod;
		this.url = url;
		this.responseListener = responseListener;
		this.errorListener = errorListener;
		this.files = files;
		this.params = params;
	}

	public void uploadFiles() {
		RequestQueue mSingleQueue = Volley.newRequestQueue(context,
				new MultiPartStack());
		MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
				requestMethod, url, responseListener, errorListener) {

			@Override
			public Map<String, File> getFileUploads() {
				// 需要上传的文件
				return files;
			}

			@Override
			public Map<String, String> getStringUploads() {
				// 需要上传的字符串
				return params;
			}

		};
		mSingleQueue.add(multiPartRequest);
	}
}
