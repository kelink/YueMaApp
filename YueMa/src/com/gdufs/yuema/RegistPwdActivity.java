package com.gdufs.yuema;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gdufs.gd.yuema.base.BaseLoadingDialog;
import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.base.C;
import com.gdufs.gd.yuema.model.TransferMessage;
import com.gdufs.gd.yuema.util.MD5Util;

public class RegistPwdActivity extends BaseUi {
	private EditText codeEditText;
	private EditText pwdEditText;
	private EditText nameEditText;
	private Button getCodeBtn;
	private Button finishBtn;
	private String registPhone;
	private BaseLoadingDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registPhone = this.getIntent().getExtras()
				.getString(C.ParamsName.PHONE_NUM);
		setContentView(R.layout.activity_regist_pwd);
		initView();
	}

	private void initView() {
		// 设置ActionBar
		setCustomerActionBarWithBack(this.getLayoutInflater(),
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						doFinish();
					}
				}, getResources().getString(R.string.title_regist));
		codeEditText = (EditText) this.findViewById(R.id.editText_code);
		pwdEditText = (EditText) this.findViewById(R.id.editText_pwd);
		nameEditText = (EditText) this.findViewById(R.id.editText_name);
		getCodeBtn = (Button) this.findViewById(R.id.btn_getCode);
		finishBtn = (Button) this.findViewById(R.id.btn_finish);
		getCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doGetRegistCode(registPhone);// 获取验证码

			}
		});
		finishBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (codeEditText.getText().toString().trim().equals("")
						|| pwdEditText.getText().toString().trim().equals("")
						|| nameEditText.getText().toString().trim().equals("")) {
					toast(C.ActivityMessage.blankField);
					return;
				} else {
					doRegist(registPhone, codeEditText.getText().toString()
							.trim(), pwdEditText.getText().toString().trim(),
							nameEditText.getText().toString().trim());
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			Intent intent = new Intent(this, RegistPhoneActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 获取验证码
	private void doGetRegistCode(String phoneNum) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(C.ParamsName.PHONE_NUM, phoneNum);
		doRequest(C.Task.TASK_GET_REGIST_CODE, C.API.GET_REGIST_CODE, params,
				C.RequestType.POST);
		Log.i("API-->", C.API.GET_REGIST_CODE);
		Log.i("params->", params.toString());
	}

	// 注册
	private void doRegist(String phoneNum, String code, String pwd, String name) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put(C.ParamsName.PHONE_NUM, phoneNum);
		params.put(C.ParamsName.REGIST_CODE, code);
		params.put(C.ParamsName.PASSWORD, MD5Util.md5Encryption(pwd));
		params.put(C.ParamsName.USERNAME, name);
		doRequest(C.Task.TASK_REGIST, C.API.REGIST, params, C.RequestType.POST);
		Log.i("API-->", C.API.REGIST);
		Log.i("params->", params.toString());
	}

	@Override
	public void onPreRequest(int taskId) {
		switch (taskId) {
		case C.Task.TASK_GET_REGIST_CODE:
			break;
		case C.Task.TASK_REGIST:
			loadingDialog = new BaseLoadingDialog(this,
					C.ActivityMsg.REGIST_NOW);
			loadingDialog.show();
			break;
		default:
			break;
		}
	}

	@Override
	public void onRequestComplete(Object response, int taskId) {
		switch (taskId) {
		case C.Task.TASK_GET_REGIST_CODE:
			TransferMessage msgMessage = (TransferMessage) response;
			codeEditText.setText(msgMessage.getResultMap()
					.get(C.ParamsName.REGIST_CODE).toString());
			break;

		case C.Task.TASK_REGIST:
			// 解析返回，符合则成功注册
			loadingDialog.dismiss();
			toast("注册成功，请登录");
			forward(LoginActivity.class);
			break;

		default:
			break;
		}
	}
}
