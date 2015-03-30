package com.gdufs.gd.yuema.baseview;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdufs.yuema.R;
import com.gdufs.yuema.adapter.MyFragmentPagerAdapter;

public class HomePageFragment extends Fragment {

	Resources resources;
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private TextView tvTabNew, tvTabHot;
	private int currIndex = 0;
	public final static int num = 2;
	Fragment home1;
	Fragment home2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, null);
		resources = getResources();
		InitTextView(view);
		InitViewPager(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		mPager.setCurrentItem(currIndex);

	}

	@Override
	public void onStop() {
		super.onStop();
		mPager.setCurrentItem(currIndex);
	}

	@Override
	public void onPause() {
		super.onPause();
		mPager.setCurrentItem(currIndex);
	}

	private void InitTextView(View parentView) {
		tvTabNew = (TextView) parentView.findViewById(R.id.tv_tab_1);
		tvTabHot = (TextView) parentView.findViewById(R.id.tv_tab_2);

		tvTabNew.setOnClickListener(new MyOnClickListener(0));
		tvTabHot.setOnClickListener(new MyOnClickListener(1));
	}

	private void InitViewPager(View parentView) {
		mPager = (ViewPager) parentView.findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();

		home1 = new HomePageFragment_FriendMsg();
		home2 = new HomePageFragment_MyMsg();

		fragmentsList.add(home1);
		fragmentsList.add(home2);

		mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(),
				fragmentsList));
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mPager.setCurrentItem(currIndex);

	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			switch (arg0) {
			case 0:
				// 左边
				tvTabNew.setTextColor(resources.getColor(R.color.white));
				tvTabNew.setBackground(resources
						.getDrawable(R.drawable.corners_bg_left_red));
				// 右边
				tvTabHot.setTextColor(resources.getColor(R.color.red));
				tvTabHot.setBackground(resources
						.getDrawable(R.drawable.corners_bg_right_white));
				break;
			case 1:
				tvTabNew.setTextColor(resources.getColor(R.color.red));
				tvTabNew.setBackground(resources
						.getDrawable(R.drawable.corners_bg_left_white));
				// 右边
				tvTabHot.setTextColor(resources.getColor(R.color.white));
				tvTabHot.setBackground(resources
						.getDrawable(R.drawable.corners_bg_right_red));

				break;
			}
			Log.i("arg0----------->", arg0 + "");
			Log.i("currIndex----------->", currIndex + "");

			currIndex = arg0;
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

}