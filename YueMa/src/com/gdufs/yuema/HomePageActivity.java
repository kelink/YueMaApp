package com.gdufs.yuema;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;
import cn.trinea.android.common.util.ToastUtils;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.baseview.HomePageFragment;
import com.gdufs.gd.yuema.baseview.HomePageFragment_FriendList;
import com.gdufs.gd.yuema.baseview.HomePageFragment_Msg;
import com.gdufs.gd.yuema.baseview.HomePageFragment_Send;
import com.gdufs.gd.yuema.baseview.HomepageFragment_User;

public class HomePageActivity extends BaseUi {

	private CharSequence mTitle;
	public static final String PAGE_HOME = "首页";
	public static final String PAGE_RELATION = "人脉";
	public static final String PAGE_NEW = "发布";
	public static final String PAGE_MESSAGE = "消息";
	public static final String PAGE_USER = "我";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		mTitle = getTitle();
		initView();
	}

	@Override
	public void onStop() {
		super.onStop();

	}

	@Override
	public void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	// 點擊菜單，可以设置actionBar
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		// restoreActionBar();
		return super.onCreateOptionsMenu(menu);
	}

	// 重新写actionBar
	public void restoreActionBar() {
	}

	// 菜單item選擇
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// int id = item.getItemId();
		//
		// switch (id) {
		// case R.id.search:
		// Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
		// startActivity(new Intent(this, RelationActivity.class));
		// break;
		// case R.id.action_settings:
		// break;
		// default:
		// break;
		// }
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
	private Class<?> fragmentArray[] = { HomePageFragment.class,
			HomePageFragment_FriendList.class, HomePageFragment_Msg.class,
			HomepageFragment_User.class, HomePageFragment_Send.class };

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
		super.setCustomerActionBarWithSetting(layoutInflater,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						startActivity(new Intent(HomePageActivity.this,
								RelationActivity.class));

					}
				}, "首页");
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

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				ToastUtils.show(HomePageActivity.this, tabId);
				switch (tabId) {
				case PAGE_HOME:
					mTitle = PAGE_HOME;
					break;
				case PAGE_RELATION:
					mTitle = PAGE_RELATION;
					break;
				case PAGE_NEW:
					mTitle = PAGE_NEW;
					break;
				case PAGE_MESSAGE:
					mTitle = PAGE_MESSAGE;
					break;
				case PAGE_USER:
					mTitle = PAGE_USER;
					break;
				default:
					break;
				}
				title.setText(mTitle);
			}
		});
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
