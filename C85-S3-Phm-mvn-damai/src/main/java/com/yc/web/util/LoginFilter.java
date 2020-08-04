package com.yc.web.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*
 * Servlet 地址配置: /开头 全局唯一
 * 1.资源绝对路径： /hello.s
 * 2.目录绝对路径: /page/  用于匹配 /page/下所有的资源  例如/page/1  /page/2 ...
 * 3.文件后缀名:  *.jsp  *.jpg *.do *.s  不能以/开头
 * 
 * 
 * 目录绝对路径+文件后缀名 是非法的 例如：/page/*.s
 * */

@WebFilter(urlPatterns={"*.jsp","*.do"})//如果只有一个可以直接使用""
public class LoginFilter implements Filter {



	/**
	 * 销毁方法
	 */
	public void destroy() {
		//
		System.out.println("========LoginFiler=====destroy==========");
	}

	/**
	 * 执行过滤
	 * FilterCain 过滤器链
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//设置请求对象的字符集==》post
				request.setCharacterEncoding("UTF-8");
				//设置响应对象的字符集
				response.setCharacterEncoding("UTF-8");
				//设置网页的字符集
				response.setContentType("text/html;charset=utf-8");
		
		//HttpSession session=((HttpServletRequest)request).getSession();
		//获取属性值
		//String user=(String) session.getAttribute("logined");
		//if(user==null){
			//response.getWriter().append("请您先登录");
			//中断执行 不执行chain.doFilter
			//return;
		//}
		//执行过滤器链中的下一个过滤器的doFilter,如果已经是最后一个过滤器了，那么就允许他访问目标资源
		chain.doFilter(request, response);
	}

	/**
	 * 初始化
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		//
		System.out.println("=====LoginFiler=====init=======");
	}

}
