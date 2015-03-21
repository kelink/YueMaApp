package com.gdufs.gd.yuema.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * SqliteUtils 单例模式，如果不存在则创建，会自动加载创建数据表
 * 
 * @author Administrator
 * 
 */

public class SqliteUtils {

	private static volatile SqliteUtils instance;

	private DBHelper dbHelper;
	private SQLiteDatabase db;

	private SqliteUtils(Context context) {
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public static SqliteUtils getInstance(Context context) {
		if (instance == null) {
			synchronized (SqliteUtils.class) {
				if (instance == null) {
					instance = new SqliteUtils(context);
				}
			}
		}
		return instance;
	}

	public SQLiteDatabase getDb() {
		return db;
	}
}
