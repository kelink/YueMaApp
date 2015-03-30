package com.gdufs.yuema;

import android.os.Bundle;

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
		setFullScreenActionBar();
	}
}
