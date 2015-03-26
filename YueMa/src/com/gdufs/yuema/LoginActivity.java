package com.gdufs.yuema;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.common.util.NetWorkUtils;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gdufs.gd.yuema.base.BaseLoadingDialog;
import com.gdufs.gd.yuema.base.BaseUi;

public class LoginActivity extends BaseUi {
	private String TAG = "LoginActivity------>";
	private Button logonBtn;
	private EditText userNameEditText;
	private EditText pwdEditText;
	private TextView forgetTextView;
	private TextView newUserTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon);
		logonBtn = (Button) this.findViewById(R.id.btn_logon);
		userNameEditText = (EditText) this.findViewById(R.id.editText_username);
		pwdEditText = (EditText) this.findViewById(R.id.editTex_password);
		forgetTextView = (TextView) this.findViewById(R.id.textView_forget);
		newUserTextView = (TextView) this.findViewById(R.id.textView_newuser);
		logonBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String userName = userNameEditText.getText().toString().trim();
				String pwd = pwdEditText.getText().toString().trim();
				if (userName.equals("") || pwd.equals("")) {
					return;
				} else {
					logOn(userName, pwd);
				}
			}
		});
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	// 登陆逻辑

	private void logOn(String userName, String pwd) {
		if (NetWorkUtils.getNetWorkType(this).equals(
				NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {
			toast("请检查网络");
			return;
		}
		final BaseLoadingDialog dialog = new BaseLoadingDialog(this, "验证中...");
		String logonAPI = "http://www.baidu.com";
		StringRequest request = new StringRequest(Method.POST, logonAPI,
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, "response -> " + response);
						// 保存信息
						if ("1".equals("1")) {
							dialog.dismiss();
							forward(HomePageActivity.class);
						} else {
							toast("登陆信息有误");
							dialog.dismiss();
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.d(TAG, "error -> " + error);
						toast("网络异常");
						dialog.dismiss();
					}
				}) {
			@Override
			protected Map<String, String> getParams() {
				// 在这里设置需要post的参数
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", "value1");
				params.put("password", "value2");
				return params;
			}
		};
		dialog.show();
		addRequestQueue(request);
	}
}
