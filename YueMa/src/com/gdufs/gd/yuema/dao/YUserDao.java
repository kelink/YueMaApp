package com.gdufs.gd.yuema.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.gdufs.gd.yuema.entity.User;

public interface YUserDao {
	public int add(User user);
	public int delete(int id);
	public ArrayList<HashMap<String, String>> query();
	public int update();
}
