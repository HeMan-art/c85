package com.yc.web.Dao;

import java.util.List;

import com.yc.web.bean.DmProduct;

public interface DmProductMapper {

	List<DmProduct> selectAll();
	
	DmProduct selectById(int id);
}