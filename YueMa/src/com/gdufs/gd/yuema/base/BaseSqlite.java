package com.gdufs.gd.yuema.base;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 实现基本的：insert /delete /update /query
 * 
 * @author Administrator
 * 
 */
public abstract class BaseSqlite {
	protected SQLiteDatabase db = null;
	private Cursor cursor = null;

	public void insert(ContentValues values) {
		try {
			db.insert(tableName(), null, values);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void update(ContentValues values, String where, String[] params) {
		try {
			db.update(tableName(), values, where, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void delete(String where, String[] params) {
		try {
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

}