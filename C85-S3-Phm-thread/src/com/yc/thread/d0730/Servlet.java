package com.yc.thread.d0730;

import java.io.IOException;

/**
 * Servlet 继承关系
 * Sevlet ==> GenriceServlet ==> HttpServlet ==> 自定义的Sevlet
 * 
 *
 *
 */
public interface Servlet {
	public void service(HttpServletRequest request, HttpServletResponse response) 
			throws IOException;
}