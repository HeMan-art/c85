package com.yc.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.annotations.Insert;

import com.yc.spring.bean.Person;
import com.yc.spring.dao.UserDao;

/**
 *	MyBaits �ĻỰ����
 * @author ����
 *
 */
public class SqlSession {

	public static void main(String[] args) {

		SqlSession session = new SqlSession();

		UserDao udao = session.getMapper(UserDao.class);

		udao.insert(new Person());

	}

	/**
	 * ���ͷ���: �������ʲô����, ���صľ���ʲô���Ͷ���
	 * ��������Ľӿ�, ���ؽӿڴ������
	 * MyBaits ��̬����,û��Ŀ�����   session.getMapper ( UserMapper.class );
	 * @param cls
	 * @return
	 */
	public <M> M getMapper(Class<M> cls) {

		@SuppressWarnings("unchecked")
		M ret = (M) Proxy.newProxyInstance(
				cls.getClassLoader(), 
				new Class[] { cls }, 
				new InvocationHandler() {

			public Object invoke(
					Object proxy, 
					Method method, 
					Object[] args) throws Throwable {

				Insert insert = method.getAnnotation(Insert.class);
				if (insert != null) {
					// ʹ�� DBHelper ִ�� �����
					System.out.println("ִ��: " + insert.value()[0]);
					// ���ݷ����ķ��ؽ��, ��dbhelper��ִ�н������
					if(method.getReturnType()!=null) {
						if(method.getReturnType().equals(int.class)) {
							// �������ֵ�� Integer ����, �ⷵ�� integer ֵ
							return 0;
						}
					}
				}

				return null;
			}
		});

		return ret;
	}

}