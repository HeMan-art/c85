package com.yc.oder.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OderAction {

	@GetMapping("oder")
	public String order() {
		return "oder";
	}

}
