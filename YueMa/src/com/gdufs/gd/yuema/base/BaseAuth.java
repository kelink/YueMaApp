package com.gdufs.gd.yuema.base;

import java.util.HashMap;

import android.content.Context;
import cn.trinea.android.common.util.PreferencesUtils;

import com.gdufs.gd.yuema.entity.User;

/**
 * 用户登陆的验证类
 * 
 * @author Administrator
 * 
 */
public class BaseAuth {
	/**
	 * 获得用户单例
	 * 
	 * @return
	 */
	public static User getUser() {
		return User.getInstance();
	}

	public static void setLogin(boolean isLogin) {
		User.getInstance().setLogin(isLogin);
	}

	public static boolean isLogin() {
		return User.getInstance().getLogin();
	}

	/**
	 * 保存用户信息到share preferences
	 * 
	 * @param context
	 * @param data
	 */
	public static void saveUserInfo(Context context,
			HashMap<String, String> data) {
		User user = BaseAuth.getUser();
		PreferencesUtils.putString(context, User.COL_NICKNAME,
				user.getNickName());
		PreferencesUtils.putString(context, User.COL_PASSWORD,
				user.getPassword());
	}

	/**
	 * 从手机本地获取保存的用户信息
	 * 
	 * @param context
	 * @return
	 */
	public static HashMap<String, String> getUserInfo(Context context) {
		HashMap<String, String> userInfo = new HashMap<String, String>();
		userInfo.put(User.COL_NICKNAME,
				PreferencesUtils.getString(context, User.COL_NICKNAME));
		userInfo.put(User.COL_PHONENUM,
				PreferencesUtils.getString(context, User.COL_PHONENUM));
		userInfo.put(User.COL_PASSWORD,
				PreferencesUtils.getString(context, User.COL_PASSWORD));
		return userInfo;
	}

	/**
	 * 清理share preference
	 * 
	 * @param context
	 */
	public static void clearUserInfo(Context context) {
		PreferencesUtils.putString(context, User.COL_NICKNAME, "");
		PreferencesUtils.putString(context, User.COL_PHONENUM, "");
		PreferencesUtils.putString(context, User.COL_PASSWORD, "");

	}

}
