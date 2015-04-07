package com.gdufs.gd.yuema.base;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

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