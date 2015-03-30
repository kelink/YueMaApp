package com.gdufs.yuema;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.util.DateTimePickDialogUtil;

public class NewActivityActivity extends BaseUi {
	private Spinner labels;
	private ArrayAdapter<CharSequence> labelsAdapter = null;
	private static String[] labelInfo = { "好玩", "认识朋友", "新奇", "免费" };
	private EditText startDateTime;
	private EditText endDateTime;
	private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
	private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity);
		labels = (Spinner) this.findViewById(R.id.spinnerLabel);
		// 动态实现的下拉框，数据在程序中获得，实际项目可能来自数据库等
		labelsAdapter = new ArrayAdapter<CharSequence>(this,
				android.R.layout.simple_spinner_dropdown_item, labelInfo);
		// 设置下拉列表的风格
		labelsAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		labels.setAdapter(labelsAdapter);
		labels.setOnItemSelectedListener(new SpinnerSelectedListener());
		// 设置默认值
		labels.setVisibility(View.VISIBLE);

		// 两个输入框
		startDateTime = (EditText) findViewById(R.id.editText_beginTime);
		endDateTime = (EditText) findViewById(R.id.editText_endTime);

		// startDateTime.setText(initStartDateTime);
		// endDateTime.setText(initEndDateTime);

		startDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
						NewActivityActivity.this, null);
				dateTimePicKDialog.dateTimePicKDialog(startDateTime);

			}
		});

		endDateTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
//						NewActivityActivity.this, initEndDateTime);
//				dateTimePicKDialog.dateTimePicKDialog(endDateTime);
			}
		});
	}

	private void initView() {

	}

	// 下拉框选择事件
	private class SpinnerSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			String city = parent.getItemAtPosition(position).toString();
			Toast.makeText(NewActivityActivity.this, "选择的城市是：" + city,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
		}

	}
	// 时间选择器

}
