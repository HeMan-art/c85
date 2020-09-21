package com.yc.C85S3PhmSpringBoot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//默认情况下，所有的方法的返回值都被解析成视图
//template
public class IndexAction {
	
	@GetMapping("index.do")
	public String index() {
		return "index";
		
	}

}
