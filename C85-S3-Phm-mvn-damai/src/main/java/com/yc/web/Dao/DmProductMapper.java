package com.yc.web.Dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yc.web.bean.DmProduct;

public interface DmProductMapper {

	List<DmProduct> selectAll();
	
	DmProduct selectById(int id);
	
	List<DmProduct> selectByObj(DmProduct dp);
	
	/**
	 * 根据枚举的分类id进行查询
	 * @param cids 存放分类id的数组
	 * @return
	 */
	List<DmProduct> selectByCids(@Param("cids") int[] cids);

	int update(DmProduct dp);
	
	
}