package com.yc.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {

	// aspectj ���е���ʽ
	// execution ����Ҫ���ط���ǩ��������
	// execution( public void ����.����.������(����))
	@Pointcut(("execution( * com.yc.spring.dao.*Dao.select*(..))"))
	public void aspect1() {
		/*�е㷽��*/ }

	@Before("aspect1()")
	// JoinPoint ���ӵ���� ==> ���� ==> �������Method
	// �ӿ�ע�� JoinPoint ����
	public void before(JoinPoint jp) {
		jp.getArgs(); // ��������
		jp.getSignature(); // ����ǩ����
		System.out.println("========== ǰ����ǿ ===========");
	}

	@After("aspect1()")
	public void after(JoinPoint jp) {
		System.out.println("========== ������ǿ ===========");
	}

	/**
	 * returning = "ret" ��ʾҵ�񷽷���������ֵ Ҫע�뵽�ķ�������������
	 * @param jp
	 * @param ret
	 */
	@AfterReturning(value = "aspect1()", returning = "ret")
	public void afterReturning(JoinPoint jp, Object ret) {
		System.out.println("========== ������ǿ ===== " + ret + " ======");
	}

	/**
	 * throwing="e" ��ʾҵ�񷽷������쳣 Ҫע�뵽�ķ�������������
	 * @param jp
	 * @param e
	 */
	@AfterThrowing(value = "aspect1()", throwing="e")
	public void afterThrowing(JoinPoint jp, Exception e) {
		System.out.println("========== �쳣��ǿ ======== " + e.getMessage() +" ===");
	}

	/**
	 * ������ǿ, ҵ�񷽷���Ҫ�����Լ���ִ��
	 */
	@Around("execution( * com.yc.spring.dao.Oracle*.select*(..))")
	public Object around(ProceedingJoinPoint pjp) {
		Object ret = null;
		try {
			// ����ҵ�����ķ���
			long begin = System.currentTimeMillis();
			ret = pjp.proceed();
			long end = System.currentTimeMillis();
			System.out.println("һ��������" + ((end - begin) / 1000) + "��");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	// ��������� hello ��ôhello������� new Hello() ������
	@After("execution(*  com.yc.spring.Hello.*(..))")
	public void afterForHello(JoinPoint jp) {
		System.out.println("========== Hello ������ǿ ===========");
	}

}