package com.gdufs.gd.yuema.base;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.IBinder;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gdufs.gd.yuema.util.volley.RequestManager;

public class BaseService extends Service {

	// 任务类基本属性
	public static final String ACTION_START = ".ACTION_START";
	public static final String ACTION_STOP = ".ACTION_STOP";
	public static final String ACTION_PING = ".ACTION_PING";
	public static final String HTTP_TYPE = ".HTTP_TYPE";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	// 异步网络任务
	/**
	 * 请求网络服务 无参数时候默认为GET，有参数为POST 不需要复写
	 * 
	 * @param url
	 * @param parmas
	 * @param requestType
	 */
	@SuppressWarnings("deprecation")
	public void doRequest(final Context context, final int taskId, String url,
			final HashMap<String, String> parmas, int requestType) {
		if (url != null) {
			Request<?> request = null;
			switch (requestType) {
			case C.RequestType.STRING:
				request = new StringRequest(url,
						new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								Log.i("response--->", response);
								onRequestComplete(response, taskId, context);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId, context);
							}
						}) {
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};

				break;
			// 请求json 对象
			case C.RequestType.JSONOBJ:

				request = new JsonObjectRequest(url, null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								onRequestComplete(response, taskId, context);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId, context);
							}
						}) {
					// Post时候传入参数
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};
				break;
			// 请求json 数组
			case C.RequestType.JSONARRAY:
				request = new JsonArrayRequest(url,
						new Response.Listener<JSONArray>() {

							@Override
							public void onResponse(JSONArray response) {
								onRequestComplete(response, taskId, context);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId, context);
							}
						}) {
					// Post时候传入参数,null时使用 Get
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};
				break;
			case C.RequestType.IMAGE:
				request = new ImageRequest(url,
						new Response.Listener<Bitmap>() {

							@Override
							public void onResponse(Bitmap response) {
								onRequestComplete(response, taskId, context);
							}
						}, 256, 256, Config.RGB_565,
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId, context);
							}
						});
				break;
			default:
				break;
			}
			// 添加到请求的线程池中去
			onPreRequest(taskId, context);
			RequestManager.getInstance(context).getRequestQueue().add(request);
		} else {
			onInvalidRequest(C.NetWorkMsg.REQUEST_FAIL, context);
		}
	}

	// 执行网络请求之前，复写处理方法
	public void onPreRequest(int taskId, Context context) {
		return;
	}

	// 做完任务后的回调方法，需要override
	public void onRequestComplete(Object response, int taskId, Context context) {

	}

	public void onRequestFail(Object error, int taskId, Context context) {
		stop(context, this.getClass());
		return;
	}

	public void onInvalidRequest(String msg, Context context) {
		stop(context, this.getClass());
		return;
	}

	public void onNetworkError(String errorMsg, Context context) {
		stop(context, this.getClass());
		return;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// static functions

	// 开启网络service
	public static void start(Context ctx, Class<? extends Service> sc) {
		// start service
		String actionName = sc.getName() + ACTION_START;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}

	// 停止context对象绑定的service
	public static void stop(Context ctx, Class<? extends Service> sc) {
		String actionName = sc.getName() + ACTION_STOP;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.stopService(i);
	}

	// 开启自定义的action为ACTION_PING的service
	public static void ping(Context ctx, Class<? extends Service> sc) {
		String actionName = sc.getName() + ACTION_PING;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}
}