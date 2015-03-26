package com.gdufs.yuema;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.gdufs.gd.yuema.baseview.FragmentPage1;
import com.gdufs.gd.yuema.baseview.FragmentPage2;
import com.gdufs.gd.yuema.baseview.FragmentPage3;
import com.gdufs.gd.yuema.baseview.FragmentPage4;
import com.gdufs.gd.yuema.baseview.FragmentPage5;

public class HomePageActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		initView();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	// 點擊菜單，可以设置actionBar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.homepage, menu);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜單item選擇
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case R.id.search:
			Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
			break;
		case R.id.action_settings:
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**************************************************************************************/
	/**
	 * 實現底部的tab欄目
	 */
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;

	// 定义一个布局
	private LayoutInflater layoutInflater;

	// 定义数组来存放Fragment界面
	private Class<?> fragmentArray[] = { FragmentPage1.class,
			FragmentPage2.class, FragmentPage3.class, FragmentPage4.class,
			FragmentPage5.class };

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_selfinfo_btn, R.drawable.tab_square_btn,
			R.drawable.tab_message_btn, R.drawable.tab_more_btn };

	// Tab选项卡的文字
	private String[] mTextviewArray = new String[5];

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// 得到fragment的个数
		int count = fragmentArray.length;

		mTextviewArray = getResources().getStringArray(R.array.homepage_items);
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			mTabHost.getTabWidget().getChildAt(i)
					.setBackgroundResource(R.drawable.selector_tab_background);
		}
	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageViewArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextviewArray[index]);

		return view;
	}

}
