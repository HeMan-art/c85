package com.yc.web.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.web.Dao.CategroyDao;


@WebServlet("/category.do")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	private CategroyDao cdao = new CategroyDao();

	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?> list = cdao.query();
		print(response, list);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
