package com.gdufs.gd.yuema.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.gdufs.gd.yuema.entity.YUser;

public interface YUserDao {
	public int add(YUser user);
	public int delete(int id);
	public ArrayList<HashMap<String, String>> query();
	public int update();
}
