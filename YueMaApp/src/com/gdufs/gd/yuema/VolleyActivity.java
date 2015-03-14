package com.gdufs.gd.yuema;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends ActionBarActivity {
	private static final String URL = "http://www.baidu.com/";
	private RequestQueue mQueue; //

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		mQueue = Volley.newRequestQueue(getApplicationContext());
		Button volleyBtn = (Button) this.findViewById(R.id.volleyGet);
		volleyBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				volleyRequest();
			}
		});
	}

	/**
	 * 使用volley请求数据
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
