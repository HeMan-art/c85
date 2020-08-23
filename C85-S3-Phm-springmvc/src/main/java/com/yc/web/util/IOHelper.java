package com.yc.web.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO工具�?
 */
public class IOHelper {

	/**
	 * 	关闭流的工具方法,  �?有的流都实现�? Closeable 方法, �?以都有close 方法, 也就是说:
	 * 	Closeable 是所有流的父�?,  这里使用的就是OOP多�?��??
	 * @param c
	 */
	public static void close(AutoCloseable c) {
		if (c != null) {
			/**
			 * 	关于如何打开错误解决窗口 
			 * 	1, 鼠标停在 报错 点上, eclipse 会给出解决方�?, 其中就包�? try
			 * 	2, 光标停在 报错 点上  ctrl + 1
			 */
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
