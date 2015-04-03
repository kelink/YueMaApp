package com.gdufs.yuema;

import java.util.HashMap;

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
import com.gdufs.gd.yuema.model.TransferMessage;
import com.gdufs.gd.yuema.model.User;
import com.gdufs.gd.yuema.util.JacksonUtil;
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
					logOn(phoneNum, pwd);
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
		case C.Task.TASK_GET_REGIST_CODE:
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
		super.doRequest(C.Task.TASK_LOGON, C.API.LOGON, null,
				C.RequestType.STRING);

	}

	@Override
	public void onRequestComplete(Object response, int taskId) {
		switch (taskId) {
		case C.Task.TASK_LOGON:
			loadingDialog.dismiss();
			// 保存信息本地
			TransferMessage msgMessage = JacksonUtil.readJson2Entity(
					response.toString(), TransferMessage.class);
			if (msgMessage != null
					&& msgMessage.getCode().equals(C.ResponseCode.SUCCESS)) {
				SharePreferencesUtil.putString(this, User.COL_PHONENUM,
						phoneNum);
				SharePreferencesUtil.putString(this, User.COL_PASSWORD, pwd);
				User.getInstance().setPhoneNum(phoneNum);
				User.getInstance().setPassword(pwd);
				User.getInstance().setLogin(true);
				startActivity(new Intent(LoginActivity.this,
						HomePageActivity.class));
			}
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
