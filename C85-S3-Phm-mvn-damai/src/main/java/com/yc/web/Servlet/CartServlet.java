package com.yc.web.Servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.web.Dao.CartDao;


@WebServlet("/cart.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       private CartDao cdao=new CartDao();
   
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		String pid=request.getParameter("pid");
		if(cdao.update(uid, pid)==0){
			cdao.insert(uid, pid);
		}
		response.getWriter().append("{\"msg\":\"购物车添加成功!\"}");
	}
	
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		List<?> list=cdao.queryByUid(uid);
		print(response,list);
	}
	
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id=request.getParameter("id");
		cdao.Del(id);
	}
	
	protected void dels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		cdao.deleteByUid(uid);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
