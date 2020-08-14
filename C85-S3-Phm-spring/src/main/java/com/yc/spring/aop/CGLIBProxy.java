package com.yc.spring.aop;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class CGLIBProxy implements MethodInterceptor {
	
	public static void main(String[] args) {
		RealSubject rs = new RealSubject();
		Houseowner ho = new Houseowner();
		
		CGLIBProxy cp = new CGLIBProxy();
		
		RealSubject proxyRs = (RealSubject) cp.proxy(rs);
		proxyRs.say();
		
		Houseowner proxyHo = (Houseowner) cp.proxy(ho);
		proxyHo.sale();
	}
	

	private Object realSubject;

	/**
	    * @param obj  Ŀ�����������ʵ��
	    * @param method ����ʵ���ϵ��ø��෽����Methodʵ��
	    * @param args  ���뵽����ʵ���Ϸ�������ֵ�Ķ�������
	    * @param methodProxy ʹ�������ø���ķ���
	    * @return
	    * @throws Throwable
	    */

	public Object intercept(
			Object obj, 
			Method method, 
			Object[] args, 
			MethodProxy methodProxy) throws Throwable {
		System.out.println("������ " + obj.getClass());
		System.out.println("�������� " + method.getName());
		if (args != null && args.length > 0) {// �����Ĳ���ֵ
			for (int i = 0; i < args.length; i++) {
				System.out.println("�������� " + args[i]);
			}
		}
		Object returnvalue = null; // �����ķ���ֵ,�޷�������ʱ��Ϊnull
		returnvalue = methodProxy.invoke(this.realSubject, args); // ����Ŀ�����ķ���
		System.out.println("�����ķ���ֵ " + returnvalue);
		return returnvalue;
	}

	/**
	 * ������
	 * @param targetObject
	 * @return
	 */
	public Object proxy(Object targetObject) {
		this.realSubject = targetObject;
		Enhancer enhancer = new Enhancer(); // �����������ɴ������
		enhancer.setSuperclass(targetObject.getClass()); // ���ø���
		enhancer.setCallback(this); // ���ûص��ö���Ϊ����
		return enhancer.create(); // �����������

	}

}