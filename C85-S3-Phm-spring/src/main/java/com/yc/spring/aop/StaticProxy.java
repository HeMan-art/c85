package com.yc.spring.aop;

public class StaticProxy {

	public static void main(String[] args) {
		Subject ps = new ProxySubject();
		ps.say();
	}

}

// ��������
interface Subject {
	void say();
}

// ��ʵ���� : ����
class RealSubject implements Subject {
	public void say() {
		System.out.println("��Ĳ����Ҹɵ�!");
	}
}

// ��������: ��ʦ
class ProxySubject implements Subject {
	// ��������� : ����
	RealSubject rs = new RealSubject();

	public void say() {
		System.out.println("�ҵĵ������в����ڳ���֤��!");
		rs.say();
		System.out.println("�ҵĵ������в����ڳ���֤��!");
	}
}