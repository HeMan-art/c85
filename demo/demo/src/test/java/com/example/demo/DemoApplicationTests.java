package com.example.demo;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.bean.Information;
import com.example.demo.dao.InformationMapper;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		
		Information information = new Information();
		information.setName("hm");
		
		imapper.insert(information);
		
	}
	
	@Resource
	private InformationMapper imapper;
	
	

}
