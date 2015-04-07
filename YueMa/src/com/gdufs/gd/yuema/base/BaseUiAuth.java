package com.gdufs.gd.yuema.base;

import android.os.Bundle;

import com.gdufs.gd.yuema.model.User;
import com.gdufs.yuema.LoginActivity;

/**
 * 验证后的基类 ：需要override 登录状态和退出方法
 * 
 * @author Administrator
 * 
 */
public class BaseUiAuth extends BaseUi {
	protected User user;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!User.isLogin()) {
			forward(LoginActivity.class);
		} else {
			this.user = User.getInstance();
		}

	}

	public void logOut() {
		User.setLogin(false);
	}
}
