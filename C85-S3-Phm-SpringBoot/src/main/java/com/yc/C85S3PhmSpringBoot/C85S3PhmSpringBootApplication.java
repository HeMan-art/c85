package com.yc.C85S3PhmSpringBoot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yc.C85S3PhmSpringBoot.dao")
public class C85S3PhmSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(C85S3PhmSpringBootApplication.class, args);
	}

}
