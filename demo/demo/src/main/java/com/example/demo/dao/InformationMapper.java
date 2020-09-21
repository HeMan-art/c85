package com.example.demo.dao;

import org.apache.ibatis.annotations.Insert;

import com.example.demo.bean.Information;

public interface InformationMapper {
	
	@Insert("insert into information values(null,name=#{name},now()")
	public int insert(Information information);

}
