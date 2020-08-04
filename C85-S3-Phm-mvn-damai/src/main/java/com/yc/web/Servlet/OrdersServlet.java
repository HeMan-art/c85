package com.yc.web.Servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.web.Dao.CartDao;
import com.yc.web.Dao.OrderitemDao;
import com.yc.web.Dao.OrdersDao;


@WebServlet("/orders.do")
public class OrdersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       private OrdersDao odao=new OrdersDao();
       private OrderitemDao oidao=new OrderitemDao();
       private CartDao cdao=new CartDao();
   //添加订单
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		System.out.println(uid);
		odao.insert(uid);
		oidao.insert(uid);
		cdao.deleteByUid(uid);
		response.getWriter().append("{\"code\":\"1\"}");
	}
	//查询新增订单
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> orders = odao.queryNewOrders(uid);
		ret.put("orders", orders);
		ret.put("orderitem", oidao.queryByOid("" + orders.get("id")));
		print(response, ret);
	}
	
	protected void query1(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("logined");
		String uid=""+user.get("id");
		Map<String, Object> ret = new HashMap<>();
		Map<String, Object> orders = odao.queryNewOrders1(uid);
		ret.put("orders", orders);
		ret.put("orderitem", oidao.queryByOid(""+orders.get("id")));
		print(response, ret);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
