package com.gdufs.gd.yuema.base;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class BaseHandler extends Handler {

	protected Activity activity;

	public BaseHandler(Activity activity) {
		this.activity = activity;
	}

	public BaseHandler(Looper looper) {
		super(looper);
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
	}
}