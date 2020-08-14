package com.yc.spring;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.yc.spring.bean.Person;
import com.yc.spring.dao.MySQLUserDao;
import com.yc.spring.dao.OracleUserDao;

/**
 * ע�ⷽʽ����IOC����
 */
@Configuration  // IOC�����������ע��   ==> beans.xml
@ComponentScan("com.yc.spring") // ɨ��ð������������Ӱ�
public class BeanConfig {
	
	// xml�е�ÿһ��bean ��Ӧ java ��һ������, ����������� bean ����
	// �����������޶�,  Bean ע���name���Զ�Ӧ <bean> �� id
	@Bean(name="hello")
	public Hello getHello() {
		return new Hello();
	}
	
	/*@Bean(name="odao")
	public OracleUserDao getOracleUserDao() {
		return new OracleUserDao();
	}
	
	@Bean(name="mdao")
	public MySQLUserDao getMySQLUserDao() {
		return new MySQLUserDao();
	}*/
	
	@Bean(name="p1")
	public Person getPerson1() {
		Person ret = new Person();
		ret.setName("����");
		ret.setAge(35);
		ret.setKilleds(new ArrayList<String>());
		ret.getKilleds().add("������");
		ret.getKilleds().add("������");
		ret.getKilleds().add("������");
		ret.getKilleds().add("������");
		ret.getKilleds().add("������");
		return ret;
	}
	
	@Bean(name="p5")
	public Person getPerson5() {
		Person p = Person.PersonFactory();
		p.setName("��Ӣ");
		return p;
	}
	
	/**
	 * ԭ��ģʽ
	 */
	@Bean("hello1")
	@Scope(value="prototype")  // ��Ӧ <bean scope="prototype">
	public Hello getHello1() {
		return new Hello();
	}
	
	
	/**
	 * ������
	 */
	@Bean("hello2")
	@Lazy
	public Hello getHello2() {
		return new Hello();
	}

}