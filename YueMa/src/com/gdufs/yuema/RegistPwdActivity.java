package com.gdufs.yuema;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.gdufs.gd.yuema.base.BaseUi;

public class RegistPwdActivity extends BaseUi {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_pwd);
		initView();
	}

	private void initView() {
		// 设置ActionBar
		setCustomerActionBarNoSetting(this.getLayoutInflater(), null, "密码设置");
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
}
