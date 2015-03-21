//package com.gdufs.gd.yuema.base;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Message;
//import android.support.v7.app.ActionBarActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Toast;
//
//import com.gdufs.gd.yuema.constant.AppException;
//import com.gdufs.gd.yuema.service.BaseMessage;
//
//public class BaseUi extends ActionBarActivity {
//	protected BaseHandler handler;
//
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	protected void onResume() {
//		super.onResume();
//	}
//
//	@Override
//	protected void onPause() {
//		super.onPause();
//	}
//
//	@Override
//	public void onStart() {
//		super.onStart();
//	}
//
//	@Override
//	public void onStop() {
//		super.onStop();
//	}
//
//	// //////////////////////////////////////////////////////////////////////////////////////////////
//	// util method
//
//	public void toast(String msg) {
//		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//	}
//
//	/**
//	 * 不结束当前活动 activity间的切换
//	 * 
//	 * @param classObj
//	 */
//	public void overlay(Class<?> classObj) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//		intent.setClass(this, classObj);
//		startActivity(intent);
//		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
//	}
//
//	/**
//	 * 不结束当前活动 activity间的切换, 带参数
//	 * 
//	 * @param classObj
//	 * @param params
//	 */
//	public void overlay(Class<?> classObj, Bundle params) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//		intent.setClass(this, classObj);
//		intent.putExtras(params);
//		startActivity(intent);
//		overridePendingTransition(0, 0);
//	}
//
//	/**
//	 * 不结束当前活动 activity间的切换 带动画
//	 * 
//	 * @param classObj
//	 * @param enterAnim
//	 * @param exitAnim
//	 */
//	public void overlay(Class<?> classObj, int enterAnim, int exitAnim) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//		intent.setClass(this, classObj);
//		startActivity(intent);
//		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
//	}
//
//	/**
//	 * 不结束当前活动 activity间的切换 带参数 带动画
//	 * 
//	 * @param classObj
//	 * @param params
//	 * @param enterAnim
//	 * @param exitAnim
//	 */
//	public void overlay(Class<?> classObj, Bundle params, int enterAnim,
//			int exitAnim) {
//		Intent intent = new Intent(Intent.ACTION_VIEW);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
//		intent.setClass(this, classObj);
//		intent.putExtras(params);
//		startActivity(intent);
//		overridePendingTransition(0, 0);
//	}
//
//	/**
//	 * 原始的切换
//	 * 
//	 * @param classObj
//	 */
//	public void forward(Class<?> classObj) {
//		Intent intent = new Intent();
//		intent.setClass(this, classObj);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		this.startActivity(intent);
//		this.finish();
//		overridePendingTransition(0, 0);
//	}
//
//	/**
//	 * activity 之间带动画的切换
//	 * 
//	 * @param classObj
//	 * @param enterAnim
//	 * @param exitAnim
//	 */
//	public void forward(Class<?> classObj, int enterAnim, int exitAnim) {
//		Intent intent = new Intent();
//		intent.setClass(this, classObj);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		this.startActivity(intent);
//		this.finish();
//		overridePendingTransition(enterAnim, exitAnim);
//	}
//
//	/**
//	 * 带参数的切换
//	 * 
//	 * @param classObj
//	 * @param params
//	 */
//	public void forward(Class<?> classObj, Bundle params) {
//		Intent intent = new Intent();
//		intent.setClass(this, classObj);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtras(params);
//		this.startActivity(intent);
//		this.finish();
//		overridePendingTransition(0, 0);
//	}
//
//	/**
//	 * 带动画而且带参数的切换
//	 * 
//	 * @param classObj
//	 * @param params
//	 * @param enterAnim
//	 * @param exitAnim
//	 */
//	public void forward(Class<?> classObj, Bundle params, int enterAnim,
//			int exitAnim) {
//		Intent intent = new Intent();
//		intent.setClass(this, classObj);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtras(params);
//		this.startActivity(intent);
//		this.finish();
//		overridePendingTransition(enterAnim, enterAnim);
//	}
//
//	public Context getContext() {
//		return this;
//	}
//
//	public BaseHandler getHandler() {
//		return this.handler;
//	}
//
//	public void setHandler(BaseHandler handler) {
//		this.handler = handler;
//	}
//
//	public LayoutInflater getLayout() {
//		return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//	}
//
//	public View getLayout(int layoutId) {
//		return getLayout().inflate(layoutId, null);
//	}
//
//	public View getLayout(int layoutId, int itemId) {
//		return getLayout(layoutId).findViewById(itemId);
//	}
//
//	// //////////////////////////////////////////////////////////////////////////////////////////////
//	// logic method
//
//	public void doFinish() {
//		this.finish();
//	}
//
//	public void doLogout() {
//
//	}
//
//	public void sendMessage(int what) {
//		Message m = new Message();
//		m.what = what;
//		handler.sendMessage(m);
//	}
//
//	public void sendMessage(int what, String data) {
//		Bundle b = new Bundle();
//		b.putString("data", data);
//		Message m = new Message();
//		m.what = what;
//		m.setData(b);
//		handler.sendMessage(m);
//	}
//
//	public void sendMessage(int what, int taskId, String data) {
//		Bundle b = new Bundle();
//		b.putInt("task", taskId);
//		b.putString("data", data);
//		Message m = new Message();
//		m.what = what;
//		m.setData(b);
//		handler.sendMessage(m);
//	}
//
//	// 任务回调方法，需要override
//	public void onTaskComplete(int taskId, BaseMessage message) {
//
//	}
//
//	public void onTaskComplete(int taskId) {
//
//	}
//
//	// 网络异常
//	public void onNetworkError(int taskId) {
//		toast(AppException.NETWORK_ERROR);
//	}
//
// }