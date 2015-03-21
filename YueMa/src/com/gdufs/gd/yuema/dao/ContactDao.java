package com.gdufs.gd.yuema.dao;

import android.content.ContentValues;

import com.gdufs.gd.yuema.base.BaseSqlite;
import com.gdufs.gd.yuema.constant.DBConstants;
import com.gdufs.gd.yuema.entity.Contact;
import com.gdufs.gd.yuema.util.SqliteUtils;

public class ContactDao extends BaseSqlite {
	private SqliteUtils sqliteUtil;

	public ContactDao(SqliteUtils sqliteUtil) {
		this.sqliteUtil = sqliteUtil;
		init();
	}

	private void init() {
		this.db = sqliteUtil.getDb();
	}

	@Override
	protected String tableName() {
		return DBConstants.CONTACTS_TABLE_NAME;
	}

	@Override
	protected String[] tableColumns() {
		String[] tableColumn = { DBConstants.CONTACTS_TABLE_ID,
				DBConstants.CONTACTS_TABLE_HOSTNUMBER,
				DBConstants.CONTACTS_TABLE_FRIENDNUMBER };
		return tableColumn;
	}

	// 刷新数据库的Contact表
	public boolean updateContactsTable(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(Contact.CONTACTS_TABLE_ID, contact.getId());
		values.put(Contact.CONTACTS_TABLE_HOSTNUMBER, contact.getHostNumber());
		values.put(Contact.CONTACTS_TABLE_FRIENDNUMBER,
				contact.getFriendNumber());

		// prepare sql
		String whereSql = Contact.CONTACTS_TABLE_ID + "=?";
		String[] whereParams = new String[] { String.valueOf(contact.getId()) };
		try {
			if (exists(whereSql, whereParams)) { // 因为id每次不一样，所以这里不会执行
				update(values, whereSql, whereParams);
			} else {
				insert(values);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
