//package com.gdufs.gd.yuema.base;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.ImageRequest;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.StringRequest;
//import com.gdufs.gd.yuema.util.NetWorkUtils;
//import com.gdufs.gd.yuema.util.volley.RequestManager;
//
///**
// * 任务类线程
// * 
// * @author Administrator
// * 
// */
//public class BaseTaskThread implements Runnable {
//
//	private Context context;
//	private String taskUrl;
//	private int taskNetWorkType;
//	private HashMap<String, String> taskArgs;
//	private BaseTask baseTask;
//	private int delayTime = 0;
//
//	public BaseTaskThread(Context context, String taskUrl,
//			HashMap<String, String> taskArgs, int delayTime, BaseTask baseTask,
//			int taskNetWorkType) {
//		this.context = context;
//		this.taskUrl = taskUrl;
//		this.taskArgs = taskArgs;
//		this.delayTime = delayTime;
//		this.baseTask = baseTask;
//		this.taskNetWorkType = taskNetWorkType;
//	}
//
//	@SuppressWarnings("deprecation")
//	@Override
//	public void run() {
//		try {
//			baseTask.onStart();
//			// set delay time
//			if (delayTime > 0) {
//				Thread.sleep(delayTime);
//			}
//			try {
//				// 网络异常
//				if (!NetWorkUtils.isConnected(context)) {
//					baseTask.onNetWorkError();
//					return;
//				}
//				// 远程任务
//				if (taskUrl != null) {
//					Request<?> request = null;
//					switch (taskNetWorkType) {
//					case C.RequestType.STRING:
//						request = new StringRequest(taskUrl,
//								new Response.Listener<String>() {
//
//									@Override
//									public void onResponse(String response) {
//										baseTask.onComplete(response);
//									}
//								}, new Response.ErrorListener() {
//									@Override
//									public void onErrorResponse(
//											VolleyError error) {
//										baseTask.onError(error);
//									}
//								}) {
//							@Override
//							protected Map<String, String> getParams()
//									throws AuthFailureError {
//								return taskArgs;
//							}
//						};
//
//						break;
//					// 请求json 对象
//					case C.RequestType.JSONOBJ:
//
//						request = new JsonObjectRequest(taskUrl, null,
//								new Response.Listener<JSONObject>() {
//									@Override
//									public void onResponse(JSONObject response) {
//										try {
//											baseTask.onComplete(response);
//											VolleyLog.v("Response:%n %s",
//													response.toString(4));
//										} catch (JSONException e) {
//											e.printStackTrace();
//										}
//									}
//								}, new Response.ErrorListener() {
//									@Override
//									public void onErrorResponse(
//											VolleyError error) {
//										baseTask.onError(error);
//									}
//								}) {
//							// Post时候传入参数
//							@Override
//							protected Map<String, String> getParams()
//									throws AuthFailureError {
//								return taskArgs;
//							}
//						};
//						break;
//					// 请求json 数组
//					case C.RequestType.JSONARRAY:
//						request = new JsonArrayRequest(taskUrl,
//								new Response.Listener<JSONArray>() {
//
//									@Override
//									public void onResponse(JSONArray response) {
//										baseTask.onComplete(response);
//									}
//								}, new Response.ErrorListener() {
//									@Override
//									public void onErrorResponse(
//											VolleyError error) {
//										baseTask.onError(error);
//									}
//								}) {
//							// Post时候传入参数,null时使用 Get
//							@Override
//							protected Map<String, String> getParams()
//									throws AuthFailureError {
//								return taskArgs;
//							}
//						};
//						break;
//					case C.RequestType.IMAGE:
//						request = new ImageRequest(taskUrl,
//								new Response.Listener<Bitmap>() {
//
//									@Override
//									public void onResponse(Bitmap response) {
//										baseTask.onComplete(response);
//									}
//								}, 256, 256, Config.RGB_565,
//								new Response.ErrorListener() {
//									@Override
//									public void onErrorResponse(
//											VolleyError error) {
//										baseTask.onError(error);
//									}
//								});
//						break;
//					default:
//						break;
//					}
//					// 添加到请求的线程池中去
//					RequestManager.getInstance(context).getRequestQueue()
//							.add(request);
//				} else {
//					baseTask.onError("Error");
//				}
//			} catch (Exception e) {
//				baseTask.onError(e.getMessage());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				baseTask.onStop();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
// }
