package com.gdufs.yuema;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.base.C;
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
	private CheckBox checkBox_aggre;
	private TextView aggrementDetial;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regist_phone);
		initView();
		phoneEditText = (EditText) this.findViewById(R.id.editText_phone);
		nextBtn = (Button) this.findViewById(R.id.btn_next);
		checkBox_aggre = (CheckBox) this.findViewById(R.id.checkBox_aggre);
		aggrementDetial = (TextView) this
				.findViewById(R.id.textView_regist_aggrement);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String phoneNum = phoneEditText.getText().toString().trim();
				if (phoneNum.length() <= 0) {
					toast(C.ActivityMessage.blankField);
					return;
				} else {
					if (!isAggre()) {
						toast(C.ActivityMessage.noAggrement);
						return;
					}
					if (volidPhoneNum(phoneNum)) {
						Bundle bundle = new Bundle();
						bundle.putString(C.ParamsName.PHONE_NUM, phoneNum);
						forward(RegistPwdActivity.class, bundle);
					} else {
						toast(C.ActivityMessage.invalidPhone);
						return;
					}
				}

			}
		});
		aggrementDetial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(AggrementActivity.class);
			}
		});
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 判断是否符合格式
	private boolean volidPhoneNum(String phoneNum) {
		return LocalContactUtil.isMobileNum(phoneNum);
	}

	// 判断是否选择同意协议
	private boolean isAggre() {
		if (checkBox_aggre.isChecked()) {
			return true;
		} else {
			return false;
		}
	}

}
