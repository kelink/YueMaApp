package com.gdufs.yuema;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.Toast;

/**
 * 人脉activity
 * 
 * @author Administrator
 * 
 */
public class RelationActivity extends Activity {
	private MyHandler myhandler;
	private SearchView searchView;
	// schedule executor
	private ScheduledExecutorService scheduledExecutor = Executors
			.newScheduledThreadPool(10);
	private String currentSearchTip;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		myhandler = new MyHandler();
		setContentView(R.layout.activity_relation);
		setActionBarLayout(R.layout.activity_actionbar_relation);
		searchView = (SearchView) this.findViewById(R.id.search_view);
		searchView.setIconified(false);
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {
				// to avoid click x button and the edittext hidden
				return true;
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			public boolean onQueryTextSubmit(String query) {
				Toast.makeText(context, "begin search", Toast.LENGTH_SHORT)
						.show();
				return true;
			}

			public boolean onQueryTextChange(String newText) {
				if (newText != null && newText.length() > 0) {
					currentSearchTip = newText;
					showSearchTip(newText);
				}
				return true;
			}
		});

		// show keyboard
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

	}

	/**
	 * 设置ActionBar的布局
	 * 
	 * @param layoutId
	 *            布局Id
	 * 
	 * */
	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			LayoutInflater inflator = (LayoutInflater) this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

	public void showSearchTip(String newText) {
		// excute after 500ms, and when excute, judge current search tip and
		// newText
		schedule(new SearchTipThread(newText), 500);
	}

	class SearchTipThread implements Runnable {

		String newText;

		public SearchTipThread(String newText) {
			this.newText = newText;
		}

		public void run() {
			// keep only one thread to load current search tip, u can get data
			// from network here
			if (newText != null && newText.equals(currentSearchTip)) {
				myhandler.sendMessage(myhandler.obtainMessage(1, newText
						+ " search tip"));
			}
		}
	}

	public ScheduledFuture<?> schedule(Runnable command, long delayTimeMills) {
		return scheduledExecutor.schedule(command, delayTimeMills,
				TimeUnit.MILLISECONDS);
	}

	private class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				Toast.makeText(context, (String) msg.obj, Toast.LENGTH_SHORT)
						.show();
				break;
			}
		}
	}
}
