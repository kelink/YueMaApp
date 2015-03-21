package com.gdufs.yuema;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdufs.gd.yuema.util.LocalContactUtil;

public class VolleyActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		Button btn = (Button) this.findViewById(R.id.volleyGet);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("contact---->",
						LocalContactUtil
								.getLocalcontactList(VolleyActivity.this) + "");

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
		StringRequest request = new StringRequest(Method.GET, URL,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.i("response---->", response.toString());
					}

				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("response---->", error.toString());

					}
				});
		mQueue.add(request);
	}

}
