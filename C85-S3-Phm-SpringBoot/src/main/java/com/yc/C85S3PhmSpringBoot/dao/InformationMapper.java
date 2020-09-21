package com.yc.C85S3PhmSpringBoot.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import com.yc.C85S3PhmSpringBoot.bean.Information;



public interface InformationMapper {
	
	@Insert("insert into information values(null,#{name},now())")
	public int insert(Information information);
	
    @Update("update information set name=#{name} where id = #{id}")
    public int update(Information information);
    
    @Delete("delete from information where id=#{id}")
    public int delete(Information information);
    
    
}
