package com.gdufs.yuema;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdufs.gd.yuema.util.LocalContactUtil;

public class VolleyActivity extends ActionBarActivity {

	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		Button btn = (Button) this.findViewById(R.id.volleyGet);

		// 设置进度
		mDialog = new ProgressDialog(this);
		mDialog.setCanceledOnTouchOutside(false);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("contact---->",
						LocalContactUtil
								.getLocalcontactList(VolleyActivity.this) + "");
				volleyRequest();

			}
		});
		// BaseProgressDialog.show(this, "登录中...", true, null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private static final String URL = "http://www.baidu.com/";
	private RequestQueue mQueue; //

	/**
	 * 使用volley请求数
	 */
	private void volleyRequest() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.peopleface);
		StringRequest request = new StringRequest(Method.GET, URL,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("response---->", response.toString());
						// response = response.substring(response.indexOf('img
						// src='));
						// response =
						// response.substring(8,response.indexOf('/>')) ;
						// Log.v('zgy','===========onResponse========='+response)
						// ;
						// mShowResponse.setText('图片地址:'+response);
						mDialog.dismiss();
						Toast.makeText(VolleyActivity.this, "上传成功",
								Toast.LENGTH_SHORT).show();

					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("response---->", error.toString());
						// mShowResponse.setText('ErrorResponse'+error.getMessage());
						Toast.makeText(VolleyActivity.this, "上传失败",
								Toast.LENGTH_SHORT).show();
						mDialog.dismiss();

					}
				});
		mQueue.add(request);
	}
}
