//package com.gdufs.gd.yuema.base;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//public class BaseDialog {
//
//	private TextView mTextMessage;
//	private ImageView mImageClose;
//	private ProgressBar mLoading;
//	private Dialog mDialog;
//
//	public BaseDialog(Context context) {
//		mDialog = new Dialog(context, R.style.base_dialog);
//		mDialog.setContentView(R.layout.main_dialog);
//		mDialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
//
//		Window window = mDialog.getWindow();
//		WindowManager.LayoutParams wl = window.getAttributes();
//		wl.x = 0;
//		wl.y = 0;
//		wl.alpha = 0.8f;
//		window.setAttributes(wl);
//		// window.setGravity(Gravity.CENTER);
//		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//		window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
//				ViewGroup.LayoutParams.WRAP_CONTENT);
//
//		mLoading = (ProgressBar) mDialog.findViewById(R.id.main_dialog_loading);
//		mTextMessage = (TextView) mDialog.findViewById(R.id.main_dialog_text);
//		mImageClose = (ImageView) mDialog.findViewById(R.id.main_dialog_close);
//		mImageClose.setOnClickListener(new ImageView.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				mDialog.dismiss();
//			}
//		});
//	}
//
//	/**
//	 * 设置对话框数据
//	 * 
//	 * @param type
//	 *            类型 1为加载完成; 0为正在加载，有个菊花在转
//	 * @param text
//	 *            提示的文字, type为1时文字有效
//	 */
//	public void setData(int type, String text) {
//
//		switch (type) {
//		case 0:
//			mTextMessage.setVisibility(View.GONE);
//			// 显示loading的
//			mLoading.setVisibility(View.VISIBLE);
//			Log.i("basedialog-->setdata", "text" + mTextMessage.getVisibility()
//					+ "/mloading:" + mLoading.getVisibility());
//			break;
//
//		case 1:
//			mLoading.setVisibility(View.GONE);
//			mTextMessage.setText(text);
//			mTextMessage.setVisibility(View.VISIBLE);
//			Log.i("basedialog-->setdata", text);
//			Log.i("basedialog-->setdata", "text" + mTextMessage.getVisibility()
//					+ "/mloading:" + mLoading.getVisibility());
//
//			break;
//
//		}
//	}
//
//	/**
//	 * 显示对话框
//	 */
//	public void show() {
//		mDialog.show();
//	}
//
//	/**
//	 * 关闭对话框
//	 */
//	public void close() {
//		mDialog.dismiss();
//	}
//
// }
