package com.gdufs.gd.yuema.jpush;

import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * For developer startup JPush SDK
 * 
 * һ�㽨�����Զ��� Application �����ʼ����Ҳ�������� Activity �
 */
public class JPushApplication extends Application {
	private static final String TAG = "JPush";

	@Override
	public void onCreate() {
		Log.d(TAG, "[JPushApplication] onCreate");
		super.onCreate();

		JPushInterface.setDebugMode(true); // ���ÿ�����־,����ʱ��ر���־
		JPushInterface.init(this); // ��ʼ�� JPush
	}
}
