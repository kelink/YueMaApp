package com.gdufs.yuema;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdufs.gd.yuema.base.BaseLoadingDialog;
import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.base.C;
import com.gdufs.gd.yuema.util.LocalContactUtil;
import com.gdufs.gd.yuema.util.SqliteUtils;

public class LoginActivity extends BaseUi {
	private Button logonBtn;
	private EditText userNameEditText;
	private EditText pwdEditText;
	private TextView forgetTextView;
	private TextView newUserTextView;
	private BaseLoadingDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon);
		initView();
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
		forgetTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(ForgetPwdActivity.class);
			}
		});
		newUserTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(RegistPhoneActivity.class);
			}
		});
	}

	private void initView() {
		// 设置ActionBar
		setFullScreenActionBar();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onPreRequest(int taskId) {
		switch (taskId) {
		case C.Task.TASK_LOGON:
			loadingDialog = new BaseLoadingDialog(this, C.ActivityMsg.LOGGING);
			loadingDialog.show();
			break;
		case C.Task.TASK_GETREGIST_CODE:
			break;
		default:
			break;
		}
	}

	// 登陆逻辑
	private void logOn(String userName, String pwd) {
		HashMap<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("userName", userName);
		userInfo.put("pwd", pwd);
		super.doRequest(C.Task.TASK_LOGON, C.API.LOGON, userInfo,
				C.RequestType.JSONOBJ);
		Log.i("SqliteUtils-->", SqliteUtils.getInstance(this).getDb()
				.toString());
		Log.i("contact-->", LocalContactUtil.getLocalcontactList(this)
				.toString());
	}

	public void onRequestComplete(Object response, int taskId) {
		switch (taskId) {
		case C.Task.TASK_LOGON:
			loadingDialog.dismiss();
			// 保存信息本地
			startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
			break;

		default:
			break;
		}
	}

	@Override
	public void onRequestFail(Object error, int taskId) {
		loadingDialog.dismiss();
		super.onRequestFail(error, taskId);

	}
}
