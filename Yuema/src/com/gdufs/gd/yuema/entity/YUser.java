package com.gdufs.gd.yuema.entity;

public class YUser extends BaseEntity {
	private static final long serialVersionUID = -7894694251168485641L;
	private int id;
	private String userName;
	private String pwd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "YUser [id=" + id + ", userName=" + userName + ", pwd=" + pwd
				+ "]";
	}
}
