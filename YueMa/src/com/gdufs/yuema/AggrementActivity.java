package com.gdufs.yuema;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.gdufs.gd.yuema.base.BaseUi;

/**
 * 协议界面
 * 
 * @author Administrator
 * 
 */
public class AggrementActivity extends BaseUi {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggrement);
		intView();
	}

	private void intView() {
		// 设置ActionBar
		setCustomerActionBarWithBack(this.getLayoutInflater(),
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						doFinish();
					}
				}, getResources().getString(R.string.title_regist));
	}
}
