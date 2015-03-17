package com.gdufs.gd.yuema.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gdufs.gd.yuema.constant.DBConstants;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, DBConstants.DB_NAME, null, DBConstants.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.beginTransaction();
		try {
			db.execSQL(DBConstants.CREATE_CONTACTS_TABLE_SQL.toString());
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
