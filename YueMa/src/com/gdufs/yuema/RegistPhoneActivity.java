package com.gdufs.yuema;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.util.LocalContactUtil;

/**
 * 注册获取验证码
 * 
 * @author Administrator
 * 
 */
public class RegistPhoneActivity extends BaseUi {
	private EditText phoneEditText;
	private Button nextBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_phone);
		initView();
		phoneEditText = (EditText) this.findViewById(R.id.editText_phone);
		nextBtn = (Button) this.findViewById(R.id.btn_next);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phoneNum = phoneEditText.getText().toString().trim();
				if (phoneNum.length() <= 0) {
					return;
				} else {
					if (volidPhoneNum(phoneNum)) {
						Bundle bundle = new Bundle();
						bundle.putString("phoneNum", phoneNum);
						forward(RegistPwdActivity.class, bundle);
					} else {
						toast("手机号码错误");
					}
				}

			}
		});
	}

	private void initView() {
		// 设置ActionBar
		setFullScreenActionBar();
	}

	// 判断是否符合格式
	private boolean volidPhoneNum(String phoneNum) {
		return LocalContactUtil.isMobileNum(phoneNum);
	}
}