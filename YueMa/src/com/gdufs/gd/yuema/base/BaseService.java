//package com.gdufs.gd.yuema.base;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import cn.trinea.android.common.util.NetWorkUtils;
//
//public class BaseService extends Service {
//
//	// 任务类基本属性(通过service action启动)
//	public static final String ACTION_START = ".ACTION_START";
//	public static final String ACTION_STOP = ".ACTION_STOP";
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		return null;
//	}
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId) {
//		super.onStart(intent, startId);
//	}
//
//	// 异步网络任务
//
//	// 完成任务后的回调方法，需要override
//	public void onTaskComplete(int taskId, BaseMessage message) {
//
//	}
//
//	// //////////////////////////////////////////////////////////////////////////////////////////////
//	// static functions
//
//	/**
//	 * 网络连接上的时候,开启网络service
//	 * 
//	 * @param ctx
//	 * @param sc
//	 */
//	public static void start(Context ctx, Class<? extends Service> sc) {
//		// get some global data
//		if (!NetWorkUtils.getNetWorkType(ctx).equals(
//				NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
//			// start service
//			String actionName = sc.getName() + ACTION_START;
//			Intent i = new Intent(ctx, sc);
//			i.setAction(actionName);
//			ctx.startService(i);
//		}
//
//	}
//
//	// 停止context对象绑定的service
//	public static void stop(Context ctx, Class<? extends Service> sc) {
//		String actionName = sc.getName() + ACTION_STOP;
//		Intent i = new Intent(ctx, sc);
//		i.setAction(actionName);
//		ctx.stopService(i);
//	}
//
// }