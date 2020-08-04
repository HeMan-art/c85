package com.yc.web.Dao;

import java.util.List;

import com.yc.web.util.DBHelper;

public class CategroyDao {
	public List<?> query() {
		return new DBHelper().query("select * from dm_category");
		
	}

}
