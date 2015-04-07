package com.gdufs.yuema.application;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import com.gdufs.gd.yuema.util.SharePreferencesUtil;
import com.gdufs.gd.yuema.util.volley.ImageCacheManager;
import com.gdufs.gd.yuema.util.volley.ImageCacheManager.CacheType;
import com.gdufs.gd.yuema.util.volley.RequestManager;

/**
 * 应用启动的时候首先加载运行的设定
 * 
 * @author Administrator
 * 
 */
public class MainApplication extends Application {
	private static final String TAG = "JPush";

	// 磁盘缓存的基参数
	private static int DISK_IMAGECACHE_SIZE = 1024 * 1024 * 5;
	private static CompressFormat DISK_IMAGECACHE_COMPRESS_FORMAT = CompressFormat.PNG;
	private static int DISK_IMAGECACHE_QUALITY = 100;

	private static MainApplication _instance;

	public static MainApplication getInstance() {
		return _instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		_instance = this;
		RequestManager
				.getInstance(MainApplication.this.getApplicationContext());// 初始化网络请求管理器
		createImageCache();// 初始化缓存管理器
		initJPush();
		initBaiDuMap();
	}

	/**
	 * 创建缓存管理，默认是内存一级缓存，其次为二级磁盘缓存
	 */
	private void createImageCache() {
		ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
				DISK_IMAGECACHE_SIZE, DISK_IMAGECACHE_COMPRESS_FORMAT,
				DISK_IMAGECACHE_QUALITY, CacheType.MEMORY);
	}

	/**
	 * 初始化JPush
	 */

	private void initJPush() {
		Log.d(TAG, "[JPushApplication] onCreate");
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	/**
	 * 初始化百度地图
	 */
	private void initBaiDuMap() {
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}

	// ////////////////////////////////////////////
	/* 保存sessionId 和cookies */
	// /////////////////////////////////////

	/**
	 * 从头部获取session cookies 并保存到SharePreferences
	 * 
	 * @param headers
	 *            Response Headers.
	 */
	public final void checkSessionCookie(Map<String, String> headers) {
		// 解析cookies
		String mHeader = headers.toString();
		// 使用正则表达式从reponse的头中提取cookie内容的子串
		Pattern pattern = Pattern.compile("Set-Cookie.*?;");
		Matcher m = pattern.matcher(mHeader);
		String cookieFromResponse = "";
		if (m.find()) {
			cookieFromResponse = m.group();
			// 去掉cookie末尾的分号
			cookieFromResponse = cookieFromResponse.substring(11,
					cookieFromResponse.length() - 1);
			SharePreferencesUtil.putString(this, "Cookie", cookieFromResponse);
			Log.i("cookie from server", cookieFromResponse);
		}
	}

	/**
	 * 如果session cookies存在的话 添加 到头部，
	 * 
	 * @param headers
	 */
	public final void addSessionCookie(Map<String, String> headers) {
		String sessionId = SharePreferencesUtil.getString(this, "Cookie", "");
		if (sessionId.equals("")) {
			return;
		} else {
			headers.put("Cookie", sessionId);
		}
	}

}