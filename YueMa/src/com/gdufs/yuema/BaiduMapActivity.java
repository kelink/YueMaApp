package com.gdufs.yuema;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

public class BaiduMapActivity extends Activity {

	private MapView mMapView = null;
	private GeoCoder mSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidumap);
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
	}

	/**
	 * 反地理编码得到地址信息
	 */
	private void reverseGeoCode(LatLng latLng) {
		// 创建地理编码检索实例
		GeoCoder geoCoder = GeoCoder.newInstance();
		//
		OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
			// 反地理编码查询结果回调函数
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null
						|| result.error != SearchResult.ERRORNO.NO_ERROR) {
					// 没有检测到结果
					Toast.makeText(BaiduMapActivity.this, "抱歉，未能找到结果",
							Toast.LENGTH_LONG).show();
				}
				Toast.makeText(BaiduMapActivity.this,
						"位置：" + result.getAddress(), Toast.LENGTH_LONG).show();
			}

			// 地理编码查询结果回调函数
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				if (result == null
						|| result.error != SearchResult.ERRORNO.NO_ERROR) {
					// 没有检测到结果
				}
				Toast.makeText(
						BaiduMapActivity.this,
						"latitude：" + result.getLocation().latitude + "\n"
								+ "longitude:" + result.getLocation().longitude,
						Toast.LENGTH_LONG).show();
			}
		};
		// 设置地理编码检索监听者
		geoCoder.setOnGetGeoCodeResultListener(listener);
		//
		geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
		// 释放地理编码检索实例
		// geoCoder.destroy();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
}