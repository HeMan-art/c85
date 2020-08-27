package com.yc.oder.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OderAction {
	
	@Resource
	private RestTemplate restTemplate;

	@GetMapping("oder")
	public String order(HttpServletRequest req) {
		return String.format("server:order ; ip:%s ; port:%s",
				req.getLocalAddr(),
				req.getLocalPort());
	}
	
	@GetMapping("user")
	public String user() {
		String url="http://127.0.0.1:8001/user";
		String res = restTemplate.getForObject(url, String.class);
		return res;
	}

}
