package com.gdufs.yuema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdufs.gd.yuema.base.BaseLoadingDialog;
import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.base.C;
import com.gdufs.gd.yuema.model.User;
import com.gdufs.gd.yuema.util.MD5Util;
import com.gdufs.gd.yuema.util.SharePreferencesUtil;

public class LoginActivity extends BaseUi {
	private Button logonBtn;
	private EditText userNameEditText;
	private EditText pwdEditText;
	private TextView forgetTextView;
	private TextView newUserTextView;
	private BaseLoadingDialog loadingDialog;

	private String phoneNum;
	private String pwd;

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
				phoneNum = userNameEditText.getText().toString().trim();
				pwd = pwdEditText.getText().toString().trim();
				if (phoneNum.equals("") || pwd.equals("")) {
					return;
				} else {
					doLogOn(phoneNum, MD5Util.md5Encryption(pwd));
				}
			}
		});
		forgetTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// forward(ForgetPwdActivity.class);
				forward(GeoCoderDemo.class);
				// toast("ssss");
				// forward(UpdateContactActivity.class);//
				// 测试该UpdateContactActivity
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

	// 登陆逻辑
	private void doLogOn(String phoneNum, String pwd) {
		startActivity(new Intent(LoginActivity.this, HomePageActivity.class));
		// HashMap<String, String> params = new HashMap<String, String>();
		// params.put(C.ParamsName.PHONE_NUM, phoneNum);
		// params.put(C.ParamsName.PASSWORD, pwd);
		// doRequest(C.Task.TASK_LOGON, C.API.LOGON, params,
		// C.RequestType.POST);
		// Log.i("API--->", C.API.LOGON);
		// Log.i("Params--->", params.toString());

	}

	@Override
	public void onPreRequest(int taskId) {
		switch (taskId) {
		case C.Task.TASK_LOGON:
			loadingDialog = new BaseLoadingDialog(this, C.ActivityMsg.LOGGING);
			loadingDialog.show();
			break;
		case C.Task.TASK_GET_REGIST_CODE:
			break;
		default:
			break;
		}
	}

	@Override
	public void onRequestComplete(Object response, int taskId) {
		switch (taskId) {
		case C.Task.TASK_LOGON:
			loadingDialog.dismiss();
			// 保存信息本地
			SharePreferencesUtil.putString(this, User.COL_PHONENUM, phoneNum);
			SharePreferencesUtil.putString(this, User.COL_PASSWORD, pwd);
			User.getInstance().setPhoneNum(phoneNum);
			User.getInstance().setPassword(MD5Util.md5Encryption(pwd));
			User.setLogin(false);
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
