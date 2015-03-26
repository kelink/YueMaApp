package com.gdufs.gd.yuema.base;

import java.util.HashMap;

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

	// 异步网络任务
	public void doTaskAsync(final int taskId, final String taskUrl) {

	}

	public void doTaskAsync(final int taskId, final String taskUrl,
			final HashMap<String, String> taskArgs) {

	}

	// 完成任务后的回调方法，需要override
	public void onTaskComplete(int taskId, BaseMessage message) {

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
		ctx.startService(i);
	}

	// 开启自定义的action为ACTION_PING的service
	public static void ping(Context ctx, Class<? extends Service> sc) {
		String actionName = sc.getName() + ACTION_PING;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}
}