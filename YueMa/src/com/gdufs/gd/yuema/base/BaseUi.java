package com.gdufs.gd.yuema.base;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.gdufs.gd.yuema.util.NetWorkUtils;
import com.gdufs.gd.yuema.util.JPush.JPushSettingUtil;
import com.gdufs.gd.yuema.util.volley.NormalPostRequest;
import com.gdufs.gd.yuema.util.volley.RequestManager;
import com.gdufs.yuema.R;

public class BaseUi extends ActionBarActivity {
	private long exitTime = 0;// 退出时间

	protected ActionBar actionBar;
	protected ImageView setting;
	protected TextView title;
	protected RequestQueue requestQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		requestQueue = RequestManager.getInstance(this).getRequestQueue();
		initView();
		initJPush();
	}

	// 初始化视图
	private void initView() {

	}

	// 初始化 JPush,如果已经初始化，但没有登录成功，则执行重新登录。
	private void initJPush() {
		JPushInterface.init(getApplicationContext());
	}

	// 判断是否为全屏，是的话不显示标题栏
	protected void setFullScreenActionBar() {
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
	}

	// 设置自定义的actionBar
	protected void setCustomerActionBarWithSetting(
			LayoutInflater layoutInflater, OnClickListener settingListener,
			String titleString) {
		View actionBarView = layoutInflater.inflate(
				R.layout.actionbar_port_layout_setting, null);
		setting = (ImageView) actionBarView.findViewById(R.id.Setting);
		setting.setOnClickListener(settingListener);
		title = (TextView) actionBarView.findViewById(R.id.title);
		title.setText(titleString);
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
	}

	// 设置自定义的actionBar带返回键
	protected void setCustomerActionBarWithBack(LayoutInflater layoutInflater,
			OnClickListener listener, String titleString) {
		View actionBarView = layoutInflater.inflate(
				R.layout.actionbar_port_layout_back, null);
		final ImageView back = (ImageView) actionBarView
				.findViewById(R.id.actionbar_back);
		back.setOnClickListener(listener);
		title = (TextView) actionBarView.findViewById(R.id.title);
		title.setText(titleString);
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 便于JPush 统计
		JPushInterface.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 便于JPush 统计
		JPushInterface.onPause(this);
	}

	@Override
	public void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();
		// 取消当前队列中的所有request
		requestQueue.cancelAll(new RequestQueue.RequestFilter() {
			@Override
			public boolean apply(Request<?> request) {
				return true;
			}
		});

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// util method

	/**
	 * 不结束当前活动 activity间的切换
	 * 
	 * @param classObj
	 */
	public void overlay(Class<?> classObj) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		startActivity(intent);
		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
	}

	/**
	 * 不结束当前活动 activity间的切换, 带参数
	 * 
	 * @param classObj
	 * @param params
	 */
	public void overlay(Class<?> classObj, Bundle params) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		intent.putExtras(params);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	/**
	 * 不结束当前活动 activity间的切换 带动画
	 * 
	 * @param classObj
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void overlay(Class<?> classObj, int enterAnim, int exitAnim) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		startActivity(intent);
		overridePendingTransition(0, 0); // 去掉activity切换时的动画效果
	}

	/**
	 * 不结束当前活动 activity间的切换 带参数 带动画
	 * 
	 * @param classObj
	 * @param params
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void overlay(Class<?> classObj, Bundle params, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		intent.putExtras(params);
		startActivity(intent);
		overridePendingTransition(0, 0);
	}

	/**
	 * 原始的切换
	 * 
	 * @param classObj
	 */
	public void forward(Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(0, 0);
	}

	/**
	 * activity 之间带动画的切换
	 * 
	 * @param classObj
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void forward(Class<?> classObj, int enterAnim, int exitAnim) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(enterAnim, exitAnim);
	}

	/**
	 * 带参数的切换
	 * 
	 * @param classObj
	 * @param params
	 */
	public void forward(Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(0, 0);
	}

	/**
	 * 带动画而且带参数的切换
	 * 
	 * @param classObj
	 * @param params
	 * @param enterAnim
	 * @param exitAnim
	 */
	public void forward(Class<?> classObj, Bundle params, int enterAnim,
			int exitAnim) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
		this.finish();
		overridePendingTransition(enterAnim, enterAnim);
	}

	public Context getContext() {
		return this;
	}

	/**
	 * 活动layoutInflater
	 * 
	 * @return
	 */
	public LayoutInflater getLayout() {
		return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * 通过layout_id获取view
	 * 
	 * @param layoutId
	 * @return
	 */
	public View getLayout(int layoutId) {
		return getLayout().inflate(layoutId, null);
	}

	/**
	 * 通过layout_id获取里面布局的view对象
	 * 
	 * @param layoutId
	 * @param itemId
	 * @return
	 */
	public View getLayoutItem(int layoutId, int itemId) {
		return getLayout(layoutId).findViewById(itemId);
	}

	public void doFinish() {
		this.finish();
	}

	/**
	 * 默认的回退按钮的操作
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// if (keyCode == KeyEvent.KEYCODE_BACK
		// && event.getAction() == KeyEvent.ACTION_DOWN) {
		// if ((System.currentTimeMillis() - exitTime) > 3000) {
		// Toast.makeText(getApplicationContext(), "再按一次退出程序",
		// Toast.LENGTH_SHORT).show();
		// exitTime = System.currentTimeMillis();
		// } else {
		// finish();
		// System.exit(0);
		// }
		// return true;
		// }
		return super.onKeyDown(keyCode, event);
	}

	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// 逻辑实现的方法

	/**
	 * 当前线程Handler发送消息
	 * 
	 * @param what
	 */
	public void sendMessage(int what) {
		Message m = new Message();
		m.what = what;
		handler.sendMessage(m);
	}

	/**
	 * 当前线程Handler发送消息 附带数据data
	 * 
	 * @param what
	 */
	public void sendMessage(int what, String data) {
		Bundle b = new Bundle();
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	/**
	 * 当前线程Handler发送消息 附带数据data
	 * 
	 * @param what
	 * @param taskId
	 * @param data
	 */
	public void sendMessage(int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	public void sendMessage(int what, int taskId, String data, Object result) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.obj = result;
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	/**
	 * 获取网络类型
	 * 
	 * @param context
	 * @return
	 */
	public String getNetWorkType(Context context) {
		return NetWorkUtils.getNetWorkType(context);
	}

	/**
	 * 判断网络是否连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean isConnectedNetWork(Context context) {
		return NetWorkUtils.isConnected(context);
	}

	/**
	 * 自定义request
	 * 
	 * @param request
	 */
	public void addRequestQueue(Request<?> request) {
		requestQueue.add(request);
	}

	/**
	 * 请求网络服务 无参数时候默认为GET，有参数为POST 不需要复写
	 * 
	 * @param url
	 * @param parmas
	 * @param requestType
	 */
	@SuppressWarnings("deprecation")
	public void doRequest(final int taskId, String url,
			final HashMap<String, String> parmas, int requestType) {
		if (!isConnectedNetWork(this)) {
			onNetworkError(C.NetWorkMsg.NETWORK_DISCONNECTED);
			return;
		}
		if (url != null) {
			switch (requestType) {
			/**
			 * 1.客户端以普通的post方式进行提交,(null时使用GET）服务端返回"字符串"
			 */
			case C.RequestType.STRING:
				StringRequest stringRequest = new StringRequest(url,
						new Response.Listener<String>() {

							@Override
							public void onResponse(String response) {
								Log.i("response--->", response);
								onRequestComplete(response, taskId);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId);
							}
						}) {
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};
				// 添加到请求的线程池中去
				onPreRequest(taskId);
				requestQueue.add(stringRequest);
				break;
			/**
			 * 2.客户端以json串的post请求方式进行提交,服务端返回"json串"
			 */
			case C.RequestType.JSONOBJ:

				JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
						url, null, new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								onRequestComplete(response, taskId);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId);
							}
						}) {
					// Post时候传入参数，传入的参数必须为JSON 串
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};
				// 添加到请求的线程池中去
				onPreRequest(taskId);
				requestQueue.add(jsonObjectRequest);
				break;
			// 请求json 数组
			case C.RequestType.JSONARRAY:
				JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
						new Response.Listener<JSONArray>() {

							@Override
							public void onResponse(JSONArray response) {
								onRequestComplete(response, taskId);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId);
							}
						}) {
					// Post时候传入参数,null时使用 Get
					@Override
					protected Map<String, String> getParams()
							throws AuthFailureError {
						return parmas;
					}
				};
				// 添加到请求的线程池中去
				onPreRequest(taskId);
				requestQueue.add(jsonArrayRequest);
				break;
			case C.RequestType.IMAGE:
				ImageRequest imageRequest = new ImageRequest(url,
						new Response.Listener<Bitmap>() {

							@Override
							public void onResponse(Bitmap response) {
								onRequestComplete(response, taskId);
							}
						}, 256, 256, Config.RGB_565,
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId);
							}
						});
				// 添加到请求的线程池中去
				onPreRequest(taskId);
				requestQueue.add(imageRequest);
				break;
			/**
			 * 客户端以普通的post方式进行提交,服务端返回json串
			 */
			case C.RequestType.NORMAL:
				NormalPostRequest normalPostRequest = new NormalPostRequest(
						url, new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								onRequestComplete(response, taskId);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								onRequestFail(error, taskId);
							}
						}, parmas);

				onPreRequest(taskId);
				requestQueue.add(normalPostRequest);
				break;
			default:
				break;
			}

		} else {
			onInvalidRequest(C.NetWorkMsg.REQUEST_FAIL);
		}
	}

	// 执行网络请求之前，复写处理方法
	public void onPreRequest(int taskId) {
		return;
	}

	// 做完任务后的回调方法，需要override
	public void onRequestComplete(Object response, int taskId) {
		return;
	}

	public void onRequestFail(Object error, int taskId) {
		toast(C.NetWorkMsg.REQUEST_FAIL);
		return;
	}

	public void onInvalidRequest(String msg) {
		toast(C.NetWorkMsg.REQUEST_FAIL);
		return;
	}

	public void onNetworkError(String errorMsg) {
		toast(C.NetWorkMsg.NETWORK_EXCEPTION);
		return;
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////

	/**************************************************
	 * JPUSH 回调Tags和Alias 使用Handler的作用是防止网络不连接的时候设置alias和tag不成功
	 **************************************************/
	protected void setTag(String[] tags) {
		if (tags == null || tags.length <= 0) {
			return;
		}
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : tags) {
			if (!JPushSettingUtil.isValidTagAndAlias(sTagItme)) {
				return;
			}
			tagSet.add(sTagItme);
		}
		// 调用JPush API设置Tag
		handler.sendMessage(handler.obtainMessage(C.JPushConstant.MSG_SET_TAGS,
				tagSet));

	}

	protected void setAlias(String alias) {
		if (alias == null || alias.equals("")) {
			return;
		}
		if (!JPushSettingUtil.isValidTagAndAlias(alias)) {
			return;
		}

		// 调用JPush API设置Tag
		handler.sendMessage(handler.obtainMessage(
				C.JPushConstant.MSG_SET_ALIAS, alias));
	}

	/**
	 * 设置通知提示方式 - 基础属性
	 */
	public void setStyleBasic() {
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				this);
		builder.statusBarDrawable = R.drawable.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为点击后自动消失
		builder.notificationDefaults = Notification.DEFAULT_SOUND; // 设置为铃声（
																	// Notification.DEFAULT_SOUND）或者震动（
																	// Notification.DEFAULT_VIBRATE）
		JPushInterface.setPushNotificationBuilder(1, builder);
	}

	/**
	 * 设置通知栏样式 - 定义通知栏Layout
	 */
	public void setStyleCustom(Context context, int layout, int icon,
			int title, int text) {
		CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
				context, layout, icon, title, text);
		builder.layoutIconDrawable = R.drawable.ic_launcher;
		builder.developerArg0 = "developerArg2";
		JPushInterface.setPushNotificationBuilder(2, builder);
	}

	// 设置Jpush alias的回调
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(C.JPushConstant.TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(C.JPushConstant.TAG, logs);
				if (NetWorkUtils.isConnected(getContext())) {
					handler.sendMessageDelayed(handler.obtainMessage(
							C.JPushConstant.MSG_SET_ALIAS, alias), 1000 * 60);
				} else {
					Log.i(C.JPushConstant.TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(C.JPushConstant.TAG, logs);
			}

		}

	};

	// 设置JPush 的tag标签
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

		@Override
		public void gotResult(int code, String alias, Set<String> tags) {
			String logs;
			switch (code) {
			case 0:
				logs = "Set tag and alias success";
				Log.i(C.JPushConstant.TAG, logs);
				break;

			case 6002:
				logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
				Log.i(C.JPushConstant.TAG, logs);
				if (NetWorkUtils.isConnected(getContext())) {
					handler.sendMessageDelayed(handler.obtainMessage(
							C.JPushConstant.MSG_SET_TAGS, tags), 1000 * 60);
				} else {
					Log.i(C.JPushConstant.TAG, "No network");
				}
				break;

			default:
				logs = "Failed with errorCode = " + code;
				Log.e(C.JPushConstant.TAG, logs);
			}
		}

	};
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case C.JPushConstant.MSG_SET_ALIAS:
				Log.d(C.JPushConstant.TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;

			case C.JPushConstant.MSG_SET_TAGS:
				Log.d(C.JPushConstant.TAG, "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i(C.JPushConstant.TAG, "Unhandled msg - " + msg.what);
			}
		}
	};
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case C.JPushConstant.MSG_SET_ALIAS:
				Log.d(C.JPushConstant.TAG, "Set alias in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(),
						(String) msg.obj, null, mAliasCallback);
				break;

			case C.JPushConstant.MSG_SET_TAGS:
				Log.d(C.JPushConstant.TAG, "Set tags in handler.");
				JPushInterface.setAliasAndTags(getApplicationContext(), null,
						(Set<String>) msg.obj, mTagsCallback);
				break;

			default:
				Log.i(C.JPushConstant.TAG, "Unhandled msg - " + msg.what);
			}
		}
	};

}