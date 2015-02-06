package com.gdufs.gd.yuema.daoImpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.gdufs.gd.yuema.dao.YUserDao;
import com.gdufs.gd.yuema.entity.YUser;
import com.gdufs.gd.yuema.util.SqliteUtils;

public class YUserDaoImpl implements YUserDao {
	private SqliteUtils sqliteUtils;

	public YUserDaoImpl(SqliteUtils sqliteUtils) {
		this.sqliteUtils = sqliteUtils;
	}

	@Override
	public int add(YUser user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<HashMap<String, String>> query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update() {
		// TODO Auto-generated method stub
		return 0;
	}

}
