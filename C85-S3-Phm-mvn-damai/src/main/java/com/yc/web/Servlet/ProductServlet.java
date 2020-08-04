package com.yc.web.Servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yc.web.Dao.ProductDao;
import com.yc.web.bean.DmProduct;
import com.yc.web.util.VerifyCodeUtils;
import com.yc.web.util.DBHelper;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/product.do")
public class ProductServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       private ProductDao pdao=new ProductDao();
    
	protected void hottop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String sql="select*from dm_product where is_hot=1 limit 0,10";
	List<?> list=new DBHelper().query(sql);
	HashMap<String,Object> page=new HashMap<>();
	page.put("list", list);
	print(response,page);
	}

	protected void newtop(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sql="select*from dm_product ORDER BY createtime DESC LIMIT 0,10";
		List<?> list=new DBHelper().query(sql);
		HashMap<String,Object> page=new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
		
	protected void products(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid=request.getParameter("cid");
		List<Map<String, Object>> list= pdao.getCategory(cid);
		HashMap<String,Object> page=new HashMap<>();
		page.put("list", list);
		print(response,page);
	}
	//分页查询所有商品
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		DmProduct dp = new DmProduct();
		// 装载方法
		BeanUtils.populate(dp, request.getParameterMap());
		List<?> list=pdao.query(dp,page, rows);
		int total=pdao.count(dp);
		HashMap<String,Object> data=new HashMap<>();
		data.put("rows", list);
		data.put("total", total);
		print(response, data);
	}
	//查询某一个商品
	protected void queryById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		String sql="select*from dm_product where id= ?";
		List<?> list=new DBHelper().query(sql,id);
		print(response, list.get(0));
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cname=request.getParameter("username");
		String upass=request.getParameter("password");
		//获取用户验证码
		String vcode=request.getParameter("vcode");
		Map<String,Object> user=pdao.queryOne(cname, upass);
		//获取会话中的验证码
		String scode=(String) request.getSession().getAttribute("vcode");		
		if(vcode!=null&&vcode.trim().isEmpty()==false){
		  if(vcode.equalsIgnoreCase(scode)){
			  if(user.size()==0){
					response.getWriter().append("请您注册");
				}else{
					request.getSession().setAttribute("logined", user);
					response.getWriter().print("登录成功");
				}
			}else{
				response.getWriter().append("验证码错误");
				}
			}else{
				response.getWriter().append("请输入验证码");
		}
		
	}
	
	protected void getlogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String,Object>)request.getSession().getAttribute("logined");
		Gson gson=new Gson();
		String json=gson.toJson(user);
		response.getWriter().print(json);
	}

	
	protected void reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ename=request.getParameter("ename");
		String cname=request.getParameter("cname");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		String phone=request.getParameter("phone");
		String sex=request.getParameter("sex");
		String passwords=request.getParameter("passwords");
		String vcode=request.getParameter("vcode");
		String scode=(String) request.getSession().getAttribute("vcode");
		boolean f=pdao.inserts(ename);
		if(ename==null || ename.trim().isEmpty()){
			response.getWriter().append("请填写用户名");
		}else if(password == null || password.trim().isEmpty()){
			response.getWriter().append("请输入密码");
		}else if(password.equals(passwords)==false){
			response.getWriter().append("两次输入的密码不一样");
		}else if(f==true){
			response.getWriter().append("该用户已存在,请更改用户名");
		}else if(vcode!=null&&vcode.trim().isEmpty()==false){
			  if(vcode.equalsIgnoreCase(scode)){
				  pdao.insert(ename, cname, password, email,phone,sex);
				  response.getWriter().append("用户注册成功");
				}else{
					response.getWriter().append("验证码错误");
					}
				}else{
					response.getWriter().append("请输入验证码");
			}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
