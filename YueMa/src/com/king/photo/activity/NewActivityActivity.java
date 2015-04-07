package com.king.photo.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.baseview.DateTimePickDialog;
import com.gdufs.gd.yuema.model.CityListItem;
import com.gdufs.gd.yuema.util.DBManager;
import com.gdufs.gd.yuema.util.volley.VolleyUploadUtil;
import com.gdufs.yuema.R;
import com.gdufs.yuema.adapter.CityAdapter;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;

/**
 * 开启新活动activity
 * 
 * @author Administrator
 * 
 */

public class NewActivityActivity extends BaseUi implements
		OnGetGeoCoderResultListener {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	// 弹出框
	private Button cameraBtn;
	private Button photoBtn;
	private Button cancelBtn;

	private EditText startDateTime;// 开始时间
	private EditText endDateTime;// 结束时间
	private DBManager dbm;
	private SQLiteDatabase db;// 省份地区数据库
	private Spinner provinceSpinner;// 省份
	private Spinner citySpinner;// 市区
	private Spinner districtSpinner;// 地区
	private EditText addressEditText;// 详细地址

	// 百度地图
	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;
	private GeoCoder mSearch;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	boolean isFirstLoc = true;// 是否首次定位

	// 需要上传的数据
	private String title;
	private Date beginTime;
	private Date endTimeDate;
	ArrayList<String> photoList = new ArrayList<>();// 上传的图片
	private String province = null;
	private String city = null;
	private String district = null;
	private String detialAddress;// 活动地址
	private double addressLatitude;// 地址的经度
	private double addresslongitude;// 地址的纬度
	private String activityDetial;// 活动详情
	private double cost;// 预期花费
	private int count;// 人数
	private String category;// 活动类别
	private String benifit;// 活动益处

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg,
				null);
		setContentView(parentView);
		Init();
	}

	public void Init() {
		mMapView = (MapView) findViewById(R.id.new_activity_geocoder_bmapView);
		mMapView.showZoomControls(false);// 不要放大放小
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mCurrentMode = LocationMode.NORMAL;
		mCurrentMarker = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_marka);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker));
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		// 省市区初始化
		provinceSpinner = (Spinner) findViewById(R.id.spinner_province);
		citySpinner = (Spinner) findViewById(R.id.spinner_city);
		districtSpinner = (Spinner) findViewById(R.id.spinner_district);
		provinceSpinner.setPrompt("省");
		citySpinner.setPrompt("城市");
		districtSpinner.setPrompt("地区");
		initProvinceSpinner();

		// 弹出窗体
		pop = new PopupWindow(NewActivityActivity.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);

		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		cameraBtn = (Button) view.findViewById(R.id.item_popupwindows_camera);
		photoBtn = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		cancelBtn = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		cameraBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		photoBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(NewActivityActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
				Map<String, File> files = new HashMap<>();
				Map<String, String> contents = new HashMap<>();
				for (ImageItem item : Bimp.tempSelectBitmap) {
					toast(item.getImagePath());
					files.put(item.imageId, new File(item.getImagePath()));
				}
				new VolleyUploadUtil(getContext(), Method.POST, "",
						new Listener() {

							@Override
							public void onResponse(Object arg0) {

							}
						}, new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError arg0) {

							}

						}, files, contents);

			}
		});
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(
							NewActivityActivity.this,
							R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					// 显示图片
					Intent intent = new Intent(NewActivityActivity.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		// 时间输入框
		startDateTime = (EditText) findViewById(R.id.editText_beginTime);

		endDateTime = (EditText) findViewById(R.id.editText_endTime);
		startDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog(
						NewActivityActivity.this, null);
				dateTimePicKDialog.showDateTimePicKDialog(startDateTime);

			}
		});

		endDateTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog(
						NewActivityActivity.this, null);
				dateTimePicKDialog.showDateTimePicKDialog(endDateTime);
			}
		});
		// 输入地址
		addressEditText = (EditText) this
				.findViewById(R.id.editText_activity_address);
		addressEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!addressEditText.getText().toString().equals("")) {
					detialAddress = addressEditText.getText().toString();
					SearchProcess(province + city + district, detialAddress);
					mBaiduMap.setMyLocationEnabled(false);// 去除定位
					toast(province + city + district + detialAddress);
				}

			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		// 异步加载图片通知改变adapter
		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	// ///////////////////////////
	// 初始化省份，市区，县 三级选择
	// ///////////////
	public void initProvinceSpinner() {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<CityListItem> list = new ArrayList<CityListItem>();

		try {
			String sql = "select * from province";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				CityListItem myListItem = new CityListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			CityListItem myListItem = new CityListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		CityAdapter myAdapter = new CityAdapter(this, list);
		provinceSpinner.setAdapter(myAdapter);
		provinceSpinner
				.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
	}

	public void initCitySpinner(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<CityListItem> list = new ArrayList<CityListItem>();

		try {
			String sql = "select * from city where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				CityListItem myListItem = new CityListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			CityListItem myListItem = new CityListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();

		CityAdapter myAdapter = new CityAdapter(this, list);
		citySpinner.setAdapter(myAdapter);
		citySpinner.setOnItemSelectedListener(new SpinnerOnSelectedListener2());
	}

	public void initDistrictSpinner(String pcode) {
		dbm = new DBManager(this);
		dbm.openDatabase();
		db = dbm.getDatabase();
		List<CityListItem> list = new ArrayList<CityListItem>();

		try {
			String sql = "select * from district where pcode='" + pcode + "'";
			Cursor cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				String code = cursor.getString(cursor.getColumnIndex("code"));
				byte bytes[] = cursor.getBlob(2);
				String name = new String(bytes, "gbk");
				CityListItem myListItem = new CityListItem();
				myListItem.setName(name);
				myListItem.setPcode(code);
				list.add(myListItem);
				cursor.moveToNext();
			}
			String code = cursor.getString(cursor.getColumnIndex("code"));
			byte bytes[] = cursor.getBlob(2);
			String name = new String(bytes, "gbk");
			CityListItem myListItem = new CityListItem();
			myListItem.setName(name);
			myListItem.setPcode(code);
			list.add(myListItem);

		} catch (Exception e) {
		}
		dbm.closeDatabase();
		db.close();
		CityAdapter myAdapter = new CityAdapter(this, list);
		districtSpinner.setAdapter(myAdapter);
		districtSpinner
				.setOnItemSelectedListener(new SpinnerOnSelectedListener3());
	}

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			province = ((CityListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((CityListItem) adapterView
					.getItemAtPosition(position)).getPcode();

			initCitySpinner(pcode);
			initDistrictSpinner(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	}

	class SpinnerOnSelectedListener2 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			city = ((CityListItem) adapterView.getItemAtPosition(position))
					.getName();
			String pcode = ((CityListItem) adapterView
					.getItemAtPosition(position)).getPcode();

			initDistrictSpinner(pcode);
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	}

	class SpinnerOnSelectedListener3 implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			district = ((CityListItem) adapterView.getItemAtPosition(position))
					.getName();
			Toast.makeText(NewActivityActivity.this,
					province + " " + city + " " + district, Toast.LENGTH_LONG)
					.show();
		}

		public void onNothingSelected(AdapterView<?> adapterView) {
		}
	}

	/**
	 * 相册id
	 */
	private static final int TAKE_PICTURE = 0x000001;

	// 打开相机
	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	// 回调相机事件
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);

				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for (int i = 0; i < PublicWay.activityList.size(); i++) {
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			System.exit(0);
		}
		return true;
	}

	// 发布活动
	public void sendNewActivity() {
		// Bimp.tempSelectBitmap.get
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(0).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();

			// 设置默认值
			addressLatitude = location.getLatitude();
			addresslongitude = location.getLongitude();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	// /////////////////////////////
	// 百度地图的地理编码和反地理编码
	// ////////////////////////////

	/**
	 * 发起搜索
	 * 
	 * @param v
	 */
	public void SearchProcess(String city, String address) {
		mSearch.geocode(new GeoCodeOption().city(city).address(address));
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(NewActivityActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		String strInfo = String.format("纬度：%f 经度：%f",
				result.getLocation().latitude, result.getLocation().longitude);
		Toast.makeText(NewActivityActivity.this, strInfo, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(NewActivityActivity.this, "抱歉，未能找到结果",
					Toast.LENGTH_LONG).show();
			return;
		}
		mBaiduMap.clear();
		mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.icon_marka)));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
				.getLocation()));
		Toast.makeText(NewActivityActivity.this, result.getAddress(),
				Toast.LENGTH_LONG).show();

	}

}
