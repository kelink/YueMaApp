//package com.gdufs.gd.yuema.base;
//
//public class BaseTask {
//	// 任务完成状态
//	public static final int TASK_COMPLETE = 0;
//	public static final int TASK_ERROR = 1;
//	public static final int NETWORK_ERROR = 2;
//	public static final int SHOW_LOADBAR = 3;
//	public static final int HIDE_LOADBAR = 4;
//	public static final int SHOW_TOAST = 5;
//	public static final int LOAD_IMAGE = 6;
//
//	// 任务id和任务名称
//	private int taskId = 0;
//	private String taskName = "";
//
//	public int getTaskId() {
//		return taskId;
//	}
//
//	public void setTaskId(int taskId) {
//		this.taskId = taskId;
//	}
//
//	public String getTaskName() {
//		return taskName;
//	}
//
//	public void setTaskName(String taskName) {
//		this.taskName = taskName;
//	}
//
//	public void onStart() {
//
//	}
//
//	// 网络错误
//	public void onNetWorkError() {
//
//	}
//
//	// 返回执行结果
//	public void onComplete(Object result) {
//	}
//
//	// 返回错误值
//	public void onError(Object error) {
//	}
//
//	public void onStop() throws Exception {
//	}
//
//	// 无返回值
//	public void onComplete() {
//
//	}
//
// }