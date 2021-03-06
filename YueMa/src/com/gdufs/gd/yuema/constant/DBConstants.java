package com.gdufs.gd.yuema.constant;

public class DBConstants {

	public static final String DB_NAME = "YueMa.db";
	public static final int DB_VERSION = 1;

	public static final StringBuffer CREATE_CONTACTS_TABLE_SQL = new StringBuffer();
	public static final String CONTACTS_TABLE_NAME = "Contacts";
	public static final String CONTACTS_TABLE_ID = "id";
	public static final String CONTACTS_TABLE_HOSTNUMBER = "hostNumber";
	public static final String CONTACTS_TABLE_FRIENDNUMBER = "friendNumber";

	static {
		/**
		 * CREATE TABLE Contacts (id integer primary key autoincrement,
		 * hostNumber text, hostNumber text)
		 */
		CREATE_CONTACTS_TABLE_SQL.append("CREATE TABLE ")
				.append(CONTACTS_TABLE_NAME).append("(")
				.append(CONTACTS_TABLE_ID)
				.append(" integer primary key autoincrement,")
				.append(CONTACTS_TABLE_HOSTNUMBER).append(" text,")
				.append(CONTACTS_TABLE_FRIENDNUMBER).append(" text")
				.append(")");

	}

	public static void main(String[] args) {
		System.out.println(CREATE_CONTACTS_TABLE_SQL.toString());
	}
}
