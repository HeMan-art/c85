package com.yc.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
	
	public static void main(String[] args) {
		JDKProxy jp = new JDKProxy();
		RealSubject rs = new RealSubject();
		// ��Ӧ ProxySubject ps = new ProxySubject();
		Subject ps = (Subject) jp.createProxyInstance(rs);  
		ps.say();
		Houseowner ho = new Houseowner();
		Broker bk = (Broker) jp.createProxyInstance(ho);
		bk.sale();
		
		// ����ʹ�ýӿ�, ���򱨴�
		//Houseowner bk1 = (Houseowner) jp.createProxyInstance(ho);
		//bk1.sale();
	}
	
	// ���������
	private Object realSubject;

	/**
	 * @param proxy   Ŀ�����Ĵ�����ʵ��
	 * @param method  ��Ӧ���ڴ���ʵ���ϵ��ýӿڷ�����Methodʵ��
	 * @param args ���뵽����ʵ���Ϸ�������ֵ�Ķ�������
	 * @return �����ķ���ֵ û�з���ֵʱ��null
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// ǰ����ǿ
		System.out.println("�ҵĵ������в����ڳ���֤��!");
		// ִ��Ŀ�귽��
		Object ret = method.invoke(realSubject, args);// rs.say();
		// ������ǿ
		System.out.println("�ҵĵ������в����ڳ���֤��!");
		return ret;
	}

	/**
	 * �����������
	 * @param targerObject
	 * @return
	 */
	public Object createProxyInstance(Object targerObject) {
		this.realSubject = targerObject;
		/*
		 * ��һ���������ô���ʹ�õ��������,һ����ø�Ŀ������ͬ���������
		 * �ڶ����������ô�����ʵ�ֵĽӿ�,��Ŀ����ʹ����ͬ�Ľӿ�
		 * �������������ûص�����,���������ķ���������ʱ,����øò���ָ�������invoke����
		 */
		return Proxy.newProxyInstance(
				targerObject.getClass().getClassLoader(), 
				targerObject.getClass().getInterfaces(),
				this);
	}

	// Proxy InvocationHandler

}

interface Broker {
	void sale();
}

class Houseowner implements Broker{
	public void sale() {
		System.out.println("�����ִ��ַ���");
	}
}