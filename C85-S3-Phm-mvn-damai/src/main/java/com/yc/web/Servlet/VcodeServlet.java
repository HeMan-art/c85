package com.yc.web.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yc.web.util.VerifyCodeUtils;

/**
 * Servlet implementation class VcodeServlet
 */
@WebServlet("/vcodes.do")
public class VcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取Session必须在获取图片之前
		HttpSession session=request.getSession();
		//向页面输出一张验证码图片
		String vcode=VerifyCodeUtils.outputImage(response);
		//将验证码设置到会话中
		session.setAttribute("vcode", vcode);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
