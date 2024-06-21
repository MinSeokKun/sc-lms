package com.lms.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.service.LectureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/lecture")
@RequiredArgsConstructor
public class LectureController {
	private final LectureService lecSer;
	
	@GetMapping("/regist")
	public String regLectureForm() {
		return "admin/common_register";
	}
	
	@PostMapping("/regist")
	public String regLecture(@RequestParam(name = "title") String title, 
			@RequestParam(name = "content") String content){
		
		lecSer.regLecture(title, content);
		
		return "redirect:/lecture/list";
	}
	
}
