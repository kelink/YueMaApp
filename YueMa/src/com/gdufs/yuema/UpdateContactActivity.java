package com.gdufs.yuema;

import java.util.HashMap;
import java.util.Set;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gdufs.gd.yuema.base.BaseLoadingDialog;
import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.base.C;
import com.gdufs.gd.yuema.util.LocalContactUtil;

/**
 * 更新contact到服务器的activity
 * 
 * @author Administrator
 * 
 */
public class UpdateContactActivity extends BaseUi {

	private Button updateBtn;
	private BaseLoadingDialog loadingDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_contact);
		initView();
		updateBtn = (Button) this.findViewById(R.id.btn_update);
		updateBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateContact();
			}
		});
	}

	private void initView() {
		setCustomerActionBarWithBack(this.getLayoutInflater(),
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						doFinish();
					}
				}, getResources().getString(R.string.title_regist));
	}

	/**
	 * 更新contact到服务器
	 */
	private void updateContact() {
		StringBuilder builder = new StringBuilder();
		Set<Pair<String, String>> list = LocalContactUtil
				.getLocalcontactList(this);
		for (Pair<String, String> pair : list) {
			builder.append(pair.first + "-");
		}
		String result = builder.toString();
		result = result.substring(0, result.length() - 1);
		HashMap<String, String> params = new HashMap<>();
		params.put(C.ParamsName.CONTACT, result);
		params.put(C.ParamsName.PHONE_NUM, "18825162414");
		// params.put(C.ParamsName.PHONE_NUM, User.getInstance().getPhoneNum());
		doRequest(C.Task.TASK_UPLOAD_CONTACT, C.API.UPLOAD_CONTACT, params,
				C.RequestType.POST);
		Log.i("API---->", C.API.UPLOAD_CONTACT);
		Log.i("params---->", params.toString());
	}

	@Override
	public void onPreRequest(int taskId) {
		switch (taskId) {
		case C.Task.TASK_UPLOAD_CONTACT:
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
		case C.Task.TASK_UPLOAD_CONTACT:
			loadingDialog.dismiss();
			toast("更新通讯录成功");
			break;

		default:
			break;
		}
	}

	@Override
	public void onRequestFail(Object error, int taskId) {
		super.onRequestFail(error, taskId);
		loadingDialog.dismiss();
		toast("更新通讯录失败");
	}
}
