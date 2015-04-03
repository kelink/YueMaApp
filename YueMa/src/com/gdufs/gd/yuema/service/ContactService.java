//package com.gdufs.gd.yuema.service;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Pair;
//
//import com.gdufs.gd.yuema.base.BaseService;
//import com.gdufs.gd.yuema.base.C;
//import com.gdufs.gd.yuema.util.LocalContactUtil;
//
//public class ContactService extends BaseService {
//
//	private static final int ID = 4000;
//	private static final String NAME = ContactService.class.getName();
//
//	private Set<Pair<String, String>> contactSet;
//
//	@Override
//	public void onCreate() {
//		super.onCreate();
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId) {
//		super.onStart(intent, startId);
//		startService();
//	}
//
//	public void startService() {
//		contactSet = LocalContactUtil.getLocalcontactList(this);
//		String userPhoneNum = "18825162414";// 测试
//		StringBuilder builder = new StringBuilder();
//		HashMap<String, String> params = new HashMap<>();
//		for (Iterator<Pair<String, String>> iterator = LocalContactUtil
//				.getLocalcontactList(this).iterator(); iterator.hasNext();) {
//			builder.append(iterator.next().first + ",");
//		}
//		params.put(C.ParamsName.CONTACT, builder.toString());
//		params.put(C.ParamsName.HOST_NUM, userPhoneNum);
//
//		doRequest(this, C.Task.TASK_UPLOAD_CONTACT, C.API.UPLOAD_CONTACT,
//				params, C.RequestType.STRING);
//	}
//
//	@Override
//	public void onRequestComplete(Object response, int taskId, Context context) {
//		super.onRequestComplete(response, taskId, context);
//		switch (taskId) {
//		case C.Task.TASK_UPLOAD_CONTACT:
//			ui.onServiceComplete(response, C.Task.TASK_UPLOAD_CONTACT);
//			break;
//		default:
//			break;
//		}
//	}
// }
