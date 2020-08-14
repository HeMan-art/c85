package com.yc.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.yc.spring.bean.Person;

@Configuration
@ComponentScan("com.yc.spring")
/** 
 * AOP ����Spring �ṩ�ĸ���
 * Spring �� AOP������ AspectJ ���
 */
@EnableAspectJAutoProxy   // ��Ӧ  <aop:aspectj-autoproxy/>
public class AOPConfig {
	
	@Bean  // δָ�� id ���������� id
	public Person getPerson() {
		return new Person();
	}
	
	@Bean
	public Hello getHello() {
		return new Hello();
	}

}