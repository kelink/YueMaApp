package com.gdufs.yuema;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import cn.jpush.android.api.JPushInterface;

import com.gdufs.gd.yuema.base.BaseUi;
import com.gdufs.gd.yuema.baseview.HomePageFragment;
import com.gdufs.gd.yuema.baseview.HomePageFragment_FriendList;
import com.gdufs.gd.yuema.baseview.HomePageFragment_Msg;
import com.gdufs.gd.yuema.baseview.HomepageFragment_User;

public class HomePageActivity extends BaseUi {
	public static final String PAGE_HOME = "首页";
	public static final String PAGE_RELATION = "人脉";
	public static final String PAGE_MESSAGE = "消息";
	public static final String PAGE_USER = "我";

	// actionBar
	private View actionBarView;
	private TextView titleTextView;
	private ImageView settingImageView;
	private ImageView editImageView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
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
			HomepageFragment_User.class };
	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_selfinfo_btn, R.drawable.tab_message_btn,
			R.drawable.tab_more_btn };
	// Tab选项卡的文字
	private String[] mTextviewArray = new String[4];

	/**
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);
		// 设置actionBar
		actionBarView = layoutInflater.inflate(R.layout.actionbar_homepage,
				new LinearLayout(this), false);// 使用new LinearLayout 解决不能填满的问题
		settingImageView = (ImageView) actionBarView
				.findViewById(R.id.imageview_setting);
		titleTextView = (TextView) actionBarView
				.findViewById(R.id.textview_title);
		editImageView = (ImageView) actionBarView
				.findViewById(R.id.imageview_edit);
		settingImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(RelationActivity.class);
			}
		});
		editImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				forward(NewActivityActivity.class);
			}
		});
		titleTextView.setText("首页");
		actionBar.setCustomView(actionBarView);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);

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

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				CharSequence mTitle = "";
				switch (tabId) {
				case PAGE_HOME:
					mTitle = PAGE_HOME;
					break;
				case PAGE_RELATION:
					mTitle = PAGE_RELATION;
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
				titleTextView.setText(mTitle);
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
