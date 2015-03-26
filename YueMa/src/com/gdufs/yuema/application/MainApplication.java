package com.gdufs.yuema.application;

import android.app.Application;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

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

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	private void init() {
		RequestManager.getInstance(this);// 初始化网络请求管理器
		createImageCache();// 初始化缓存管理器
		initJPush();
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

}