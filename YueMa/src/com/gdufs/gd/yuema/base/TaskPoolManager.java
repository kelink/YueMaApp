//package com.gdufs.gd.yuema.base;
//
//import java.util.HashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import org.json.JSONObject;
//
//import android.content.Context;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//
///**
// * 任务类的线程池，每次有请任务时，开启线程处理
// * 
// * @author Administrator
// * 
// */
//public class TaskPoolManager {
//
//	private static TaskPoolManager taskPoolManager;
//	private static ExecutorService taskPool;
//	private Context context;
//	private RequestQueue queue;
//
//	private TaskPoolManager(Context context, RequestQueue queue) {
//		this.context = context;
//		this.queue = getRequestQueue();
//		taskPool = Executors.newCachedThreadPool();
//	}
//
//	public static synchronized TaskPoolManager getInstance(Context context,
//			RequestQueue queue) {
//		if (taskPoolManager == null) {
//			taskPoolManager = new TaskPoolManager(context, queue);
//		}
//		return taskPoolManager;
//	}
//
//	public ExecutorService getTaskPool() {
//		return taskPool;
//	}
//
//	public RequestQueue getRequestQueue() {
//		return queue;
//	}
//
//	// http post task with params
//	public void addTask(int taskId, String taskUrl,
//			HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
//		baseTask.setId(taskId);
//		try {
//			taskPool.execute(new TaskThread(taskUrl, taskArgs, baseTask,
//					delayTime));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
//
//	// http post task without params
//	public void addTask(int taskId, String taskUrl, BaseTask baseTask,
//			int delayTime) {
//		baseTask.setId(taskId);
//		try {
//			taskPool.execute(new TaskThread(taskUrl, null, baseTask, delayTime));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
//
//	// custom task(自定义任务类)
//	public void addTask(int taskId, BaseTask baseTask, int delayTime) {
//		baseTask.setId(taskId);
//		try {
//			taskPool.execute(new TaskThread(null, null, baseTask, delayTime));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
//
//	// 任务线程处理的逻辑
//	private class TaskThread implements Runnable {
//		private String taskUrl;
//		private HashMap<String, String> taskArgs;
//		private BaseTask baseTask;
//		private int delayTime = 0;
//
//		public TaskThread(String taskUrl, HashMap<String, String> taskArgs,
//				BaseTask baseTask, int delayTime) {
//			this.taskUrl = taskUrl;
//			this.taskArgs = taskArgs;
//			this.baseTask = baseTask;
//			this.delayTime = delayTime;
//		}
//
//		@Override
//		public void run() {
//			baseTask.onStart();// 多态实现
//			// 延迟执行
//			if (this.delayTime > 0) {
//				try {
//					Thread.sleep(this.delayTime);
//				} catch (InterruptedException e) {
//					baseTask.onError(e.getMessage());
//				}
//			}
//
//			// 网络任务
//			if (this.taskUrl != null) {
//				// JsonObjectRequest
//				JsonObjectRequest request = new JsonObjectRequest(
//						Request.Method.POST, taskUrl, new JSONObject(taskArgs),
//						new Response.Listener<JSONObject>() {
//							@Override
//							public void onResponse(JSONObject jsonobj) {
//								baseTask.onComplete(jsonobj);
//							}
//						}, new Response.ErrorListener() {
//
//							@Override
//							public void onErrorResponse(VolleyError error) {
//								baseTask.onError(error.toString());
//							}
//						});
//
//				// Json
//				queue.add(request);
//			} else {
//				baseTask.onError("invalid request");
//			}
//
//		}
//	}
//
// }