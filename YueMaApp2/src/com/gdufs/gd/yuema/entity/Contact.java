package com.gdufs.gd.yuema.entity;

public class Contact extends BaseEntity {
	private static final long serialVersionUID = 82570650783060379L;
	private int id;
	private String hostNumber;
	private String friendNumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHostNumber() {
		return hostNumber;
	}

	public void setHostNumber(String hostNumber) {
		this.hostNumber = hostNumber;
	}

	public String getFriendNumber() {
		return friendNumber;
	}

	public void setFriendNumber(String friendNumber) {
		this.friendNumber = friendNumber;
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", hostNumber=" + hostNumber
				+ ", friendNumber=" + friendNumber + "]";
	}

}
