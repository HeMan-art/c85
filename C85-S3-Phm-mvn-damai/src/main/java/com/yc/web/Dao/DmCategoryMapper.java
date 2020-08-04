package com.yc.web.Dao;

import java.util.List;

import com.yc.web.bean.DmCategory;

public interface DmCategoryMapper {

	List<DmCategory> selectAll();
	
	int insert(DmCategory dc);
	
	int update(DmCategory dc);
	
	int delete(int id);
}