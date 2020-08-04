package com.yc.web.Dao;

import java.util.List;

import com.yc.web.bean.DmOrderitem;

public interface DmOrderitemMapper {

	List<DmOrderitem> selectAll();
	
	DmOrderitem selectById(int id);
}