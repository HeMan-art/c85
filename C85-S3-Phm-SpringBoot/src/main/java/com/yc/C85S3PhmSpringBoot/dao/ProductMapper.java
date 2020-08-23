package com.yc.C85S3PhmSpringBoot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.yc.web.been.DmProduct;



public interface ProductMapper {

	@Select("select * from dm_product")
	List<DmProduct> selectAll();
}
