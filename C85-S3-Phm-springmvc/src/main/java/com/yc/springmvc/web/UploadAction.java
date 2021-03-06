package com.yc.springmvc.web;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("demo")
public class UploadAction {

	@PostMapping(path="upload",produces = "text/html;charset=utf-8")
	public String upload(@RequestParam("file") MultipartFile file) 
			throws IllegalStateException, IOException {
		
		String diskpath = "C:/Users/Administrator/Desktop/lianxi/";
		String filename = file.getOriginalFilename();
		file.transferTo(new File(diskpath + filename));
		return "success" + "photo/" + filename;
		
	}
}
