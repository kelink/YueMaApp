package com.gdufs.yuema;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gdufs.gd.yuema.base.BaseUi;

public class LogonRegistActivity extends BaseUi {
	private Button registBtn;
	private Button logonBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logon_regist);
		initView();
		registBtn = (Button) this.findViewById(R.id.register_btn);
		logonBtn = (Button) this.findViewById(R.id.logon_btn);
		registBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(RegistPhoneActivity.class);
			}
		});
		logonBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(LoginActivity.class);
			}
		});
	}

	private void initView() {
		// 设置ActionBar
		setFullScreenActionBar();
	}
}
