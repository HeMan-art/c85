package com.yc.web.Dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.web.been.DmProduct;
import com.yc.web.util.DBHelper;

public class ProductDao {
	public List<Map<String,Object>>getCategory(String id){
		String sql="select*from dm_product where cid=?";
		return new DBHelper().query(sql, id);
	}
	public void insert(String ename,String cname,String password,String email,String phone,String sex){
		String sql="insert into dm_user values(null,?,?,?,?,?,?,0,now())";
		DBHelper dbh=new DBHelper();
		dbh.update(sql, ename,cname,password,email,phone,sex);
	}
	public boolean inserts(String ename){
		String sql="select*from dm_user where ename=?";
		DBHelper dbh=new DBHelper();
		return dbh.count(sql, ename)> 0;
	}
	public Map<String,Object> queryOne(String uname,String upass){
		String sql="select*from dm_user where ename=? and password=?";
		DBHelper dbh=new DBHelper();
		List<Map<String,Object>>list=dbh.query(sql, uname,upass);
		if(list.size()==0){
			return null;
		}else{
			Map<String,Object> user=list.get(0);
			return user;
		}
	}
	public List<Map<String,Object>>query(DmProduct dp,String page,String rows){
		String where = "";
		List<Object> params = new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}
		
		if(dp.getCid() != null) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}
		
		if(dp.getIsHot() != null) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}
		
		int ipage = Integer.parseInt(page);
		int irows = Integer.parseInt(rows);
		ipage = (ipage - 1) * 10;
		String sql = "select a.*, b.cname from dm_product a"
				+ " join dm_category b on a.cid = b.id"
				+ " where 1=1"
				+ where
				+ " limit ?, ?";
		params.add(ipage);
		params.add(irows);
		return new DBHelper().query(sql, params.toArray());
	}
	public int count(DmProduct dp){
		String where = "";
		List<Object> params = new ArrayList<>();
		if(dp.getPname()!=null && dp.getPname().trim().isEmpty() == false) {
			where += " and pname like ?";
			params.add("%" + dp.getPname() + "%");
		}
		
		if(dp.getCid() != null) {
			where += " and cid = ?";
			params.add(dp.getCid());
		}
		
		if(dp.getIsHot() != null) {
			where += " and is_hot = ?";
			params.add(dp.getIsHot());
		}
		String sql = "select count(*) from dm_product where 1=1" + where;
		return new DBHelper().count(sql, params.toArray());
	}
}
