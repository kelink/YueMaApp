package com.gdufs.gd.yuema.service;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseSqlite {

	private static final String DB_NAME = "library.db";
	private static final int DB_VERSION = 1;

	private DbHelper dbh = null;
	private SQLiteDatabase db = null;
	private Cursor cursor = null;

	public BaseSqlite(Context context) {
		dbh = new DbHelper(context, DB_NAME, null, DB_VERSION);
	}

	public void create(ContentValues values) {
		try {
			db = dbh.getWritableDatabase();
			db.insert(tableName(), null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void update(ContentValues values, String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.update(tableName(), values, where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void delete(String where, String[] params) {
		try {
			db = dbh.getWritableDatabase();
			db.delete(tableName(), where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public ArrayList<HashMap<String, String>> query(String sql, String[] args) {
		ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		try {
			db = dbh.getReadableDatabase();
			cursor = db.rawQuery(sql, args);
			while (cursor.moveToNext()) {
				HashMap<String, String> colList = new HashMap<String, String>();
				int len = cursor.getColumnCount();
				for (int i = 0; i < len; i++) {
					colList.put(cursor.getColumnName(i), cursor.getString(i));
				}
				rowList.add(colList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return rowList;
	}

	public ArrayList<HashMap<String, String>> select(String where,
			String[] params) {
		ArrayList<HashMap<String, String>> rowList = new ArrayList<HashMap<String, String>>();
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null,
					null, null);

			while (cursor.moveToNext()) {
				HashMap<String, String> colList = new HashMap<String, String>();
				int len = cursor.getColumnCount();
				for (int i = 0; i < len; i++) {
					colList.put(cursor.getColumnName(i), cursor.getString(i));
				}
				rowList.add(colList);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			cursor.close();
			db.close();
		}

		return rowList;

	}

	public int count(String where, String[] params) {
		try {
			db = dbh.getReadableDatabase();
			cursor = db.query(tableName(), tableColumns(), where, params, null,
					null, null);
			return cursor.getCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			db.close();
		}
		return 0;
	}

	public boolean exists(String where, String[] params) {
		boolean result = false;
		try {
			int count = this.count(where, params);
			if (count > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			cursor.close();
		}
		return result;
	}

	abstract protected String tableName();

	abstract protected String[] tableColumns();

	abstract protected String createSql();

	abstract protected String upgradeSql();

	protected class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(createSql());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(upgradeSql());
			onCreate(db);
		}
	}
}