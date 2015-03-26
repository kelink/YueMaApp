package com.gdufs.gd.yuema.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.trinea.android.common.util.NetWorkUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.gdufs.gd.yuema.util.AppUtil;
import com.gdufs.gd.yuema.util.volley.RequestManager;

public class BaseUi extends Activity {
	protected Handler handler;
	protected boolean showLoadBar = false;
	protected boolean showDebugMsg = true;
	protected RequestQueue requestQueue = RequestManager.getInstance(this)
			.getRequestQueue();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 便于JPush 统计
		JPushInterface.onResume(this);
		debugMemory("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 便于JPush 统计
		JPushInterface.onPause(this);
		debugMemory("onPause");
	}

	@Override
	public void onStart() {
		super.onStart();
		// debug memory
		debugMemory("onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		// debug memory
		debugMemory("onStop");
		// 取消当前队列中的所有request
		requestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// util method

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 不结束当前活动 activity间的切换
	 * 
	 * @param classObj
	 */
	public void overlay(Class<?> classObj) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		startActivity(intent);
		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
	}

	/**
	 * 不结束当前活动 activity间的切换, 带参数
	 * 
	 * @param classObj
	 * @param params
	 */
	public void overlay(Class<?> classObj, Bundle params) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		intent.putExtras(params);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	/**
	 * 不结束当前活动 activity间的切换 带动画
	 * 
	 * @param classObj
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void overlay(Class<?> classObj, int enterAnim, int exitAnim) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		startActivity(intent);
		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
	}

	/**
	 * 不结束当前活动 activity间的切换 带参数 带动画
	 * 
	 * @param classObj
	 * @param params
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void overlay(Class<?> classObj, Bundle params, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		intent.putExtras(params);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	/**
	 * 原始的切换
	 * 
	 * @param classObj
	 */
	public void forward(Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(0, 0);
	}

	/**
	 * activity 之间带动画的切换
	 * 
	 * @param classObj
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void forward(Class<?> classObj, int enterAnim, int exitAnim) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(enterAnim, exitAnim);
	}

	/**
	 * 带参数的切换
	 * 
	 * @param classObj
	 * @param params
	 */
	public void forward(Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(0, 0);
	}

	/**
	 * 带动画而且带参数的切换
	 * 
	 * @param classObj
	 * @param params
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void forward(Class<?> classObj, Bundle params, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(enterAnim, enterAnim);
	}

	public Context getContext() {
		return this;
	}

	public Handler getHandler() {
		return this.handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public LayoutInflater getLayout() {
		return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getLayout(int layoutId) {
		return getLayout().inflate(layoutId, null);
	}

	public View getLayout(int layoutId, int itemId) {
		return getLayout(layoutId).findViewById(itemId);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// logic method

	public void doFinish() {
		this.finish();
	}

	public void doLogout() {

	}

	// 添加到请求队列
	public void addRequestQueue(Request<?> request) {
		requestQueue.add(request);
	}

	public void sendMessage(int what) {
		Message m = new Message();
		m.what = what;
		handler.sendMessage(m);
	}

	public void sendMessage(int what, String data) {
		Bundle b = new Bundle();
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	public void sendMessage(int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	// get network type
	public String getNetWorkType(Context context) {
		return NetWorkUtils.getNetWorkType(context);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// debug method

	public void debugMemory(String tag) {
		if (this.showDebugMsg) {
			Log.w(this.getClass().getSimpleName(),
					tag + ":" + AppUtil.getUsedMemory());
		}
	}
}