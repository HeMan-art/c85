package com.yc.C85S3PhmSpringBoot;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.yc.C85S3PhmSpringBoot.bean.Information;
import com.yc.C85S3PhmSpringBoot.dao.InformationMapper;



@SpringBootTest
class C85S3PhmSpringBootApplicationTests {

	@Test
	void insert() {
		
		
		Information information = new Information();
				
		information.setName("hm1");		
		imapper.insert(information);
	}
	
	@Test
	void update() {
		
		Information information = new Information();
		
		information.setId(1);
		information.setName("zm");		
		imapper.update(information);
	}
	
	@Test
	void delete() {
		
		Information information = new Information();
		
		information.setId(2);		
		imapper.delete(information);
	}
	
	@Resource
	private InformationMapper imapper;
}
