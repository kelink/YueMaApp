//package com.gdufs.gd.yuema.base;
//
//import java.util.Set;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
//import android.util.Log;
//import cn.jpush.android.api.JPushInterface;
//import cn.jpush.android.api.TagAliasCallback;
//
//import com.gdufs.gd.yuema.util.AppUtil;
//
///**
// * 适应项目的基本handler
// * 
// * @author Administrator
// * 
// */
//public class BaseHandler extends Handler {
//	protected BaseUi ui;
//
//	// JPush基础类
//	protected TagAliasCallback mAliasCallback;
//	protected TagAliasCallback mTagsCallback;
//
//	public BaseHandler(BaseUi ui, TagAliasCallback mAliasCallback,
//			TagAliasCallback mTagsCallback) {
//		this.ui = ui;
//		this.mAliasCallback = mAliasCallback;
//		this.mTagsCallback = mTagsCallback;
//	}
//
//	public BaseHandler(Looper looper) {
//		super(looper);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void handleMessage(Message msg) {
//		try {
//			int taskId;
//			Object resultObj;
//			switch (msg.what) {
//			case BaseTask.TASK_COMPLETE:
//				taskId = msg.getData().getInt("task");
//				resultObj = msg.obj;
//				if (resultObj != null) {
//					ui.onTaskComplete(taskId, resultObj);
//				} else if (!AppUtil.isEmptyInt(taskId)) {
//					ui.onTaskComplete(taskId);
//				} else {
//					ui.toast(C.NetWorkMsg.NETWORK_DISCONNECTED);
//				}
//				break;
//			case BaseTask.TASK_ERROR:
//				taskId = msg.getData().getInt("task");
//				resultObj = msg.obj;
//				if (resultObj != null) {
//					ui.onTaskFail(taskId, resultObj);
//				}
//				break;
//			case BaseTask.NETWORK_ERROR:
//				taskId = msg.getData().getInt("task");
//				ui.onNetworkError(taskId);
//				break;
//			case BaseTask.SHOW_LOADBAR:
//				break;
//			case BaseTask.HIDE_LOADBAR:
//				break;
//			case BaseTask.SHOW_TOAST:
//				break;
//
//			// 设置JPUSH的 Tag 和alias
//			case C.JPushConstant.MSG_SET_ALIAS:
//				Log.d(C.JPushConstant.TAG, "Set alias in handler.");
//				JPushInterface.setAliasAndTags(ui.getContext(),
//						(String) msg.obj, null, mAliasCallback);
//				break;
//			case C.JPushConstant.MSG_SET_TAGS:
//				Log.d(C.JPushConstant.TAG, "Set tags in handler.");
//				JPushInterface.setAliasAndTags(ui.getContext(), null,
//						(Set<String>) msg.obj, mTagsCallback);
//				break;
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			ui.toast(e.getMessage());
//		}
//	}
//
// }
