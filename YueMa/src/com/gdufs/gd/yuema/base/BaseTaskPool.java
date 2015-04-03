//package com.gdufs.gd.yuema.base;
//
//import java.util.HashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import android.content.Context;
//
///**
// * 任务池
// * 
// * @author Administrator
// * 
// */
//
//public class BaseTaskPool {
//
//	public static ExecutorService taskPool;
//	private Context context;
//
//	public BaseTaskPool(BaseUi ui) {
//		taskPool = Executors.newCachedThreadPool();
//		context = ui.getContext();
//	}
//
//	// POST，有参，需要传值
//	public void addTask(int taskId, String taskUrl,
//			HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime,
//			int taskNetWorkType) {
//		baseTask.setTaskId(taskId);
//		try {
//			taskPool.execute(new BaseTaskThread(context, taskUrl, taskArgs,
//					delayTime, baseTask, taskNetWorkType));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
//
//	// Get，无参，需要构造
//	public void addTask(int taskId, String taskUrl, BaseTask baseTask,
//			int delayTime, int taskNetWorkType) {
//		baseTask.setTaskId(taskId);
//		try {
//			taskPool.execute(new BaseTaskThread(context, taskUrl, null,
//					delayTime, baseTask, taskNetWorkType));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
//
//	// custom task(自定义任务类)
//	public void addTask(int taskId, BaseTask baseTask, int delayTime) {
//		baseTask.setTaskId(taskId);
//		try {
//			taskPool.execute(new BaseTaskThread(context, null, null, delayTime,
//					baseTask, -1));
//		} catch (Exception e) {
//			taskPool.shutdown();
//		}
//	}
// }
