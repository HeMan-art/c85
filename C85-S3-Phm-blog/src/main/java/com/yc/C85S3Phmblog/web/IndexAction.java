package com.yc.C85S3Phmblog.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.yc.C85S3Phmblog.dao.ArticleMapper;

@Controller
public class IndexAction {
	
	@Resource
	private ArticleMapper amapper;
	
	@GetMapping("/")
	public String index(Model m) {
		m.addAttribute("alist",amapper.selectByNew());
		return "index";
		
	}
	
	

}
