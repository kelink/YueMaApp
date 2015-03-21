package com.gdufs.gd.yuema.entity;

import com.gdufs.gd.yuema.base.BaseModel;

/**
 * User 单例对象
 * 
 * @author Administrator
 * 
 */
public class User extends BaseModel {
	private static final long serialVersionUID = 1L;
	public final static String COL_NICKNAME = "nickName";
	public final static String COL_PHONENUM = "phoneNum";
	public final static String COL_PASSWORD = "password";
	private String nickName;
	private String phoneNum;
	private String password;

	private static boolean isLogin = false;

	private static User instance;

	private User() {
	}

	private static User newInstant() {
		return new User();
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(boolean isLogin) {
		User.isLogin = isLogin;
	}

	public boolean getLogin() {
		return isLogin;
	}

	public static final User getInstance() {
		if (instance == null) {
			synchronized (User.class) {
				if (instance == null) {
					instance = newInstant();
				}
			}
		}
		return instance;
	}

}
