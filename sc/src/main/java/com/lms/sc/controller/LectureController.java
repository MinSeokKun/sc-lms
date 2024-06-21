package com.lms.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.service.VideoService;
import com.lms.sc.service.LectureService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lecture")
@RequiredArgsConstructor
@Controller
public class LectureController {
	
	private final VideoService lecVideoService;
	private final LectureService lectureService;
	
	@GetMapping("/list/{lec_id}")
	public String getLecture(Model model, @PathVariable("lec_id") long lec_id) {
		
		
		return "lecture/lectureDatail";
	}
	
	@GetMapping("/list")
	public String lecList(Model model) {
		return "admin/lec_list";
	}
	
  @GetMapping("/regist")
	public String regLectureForm() {
		return "admin/common_register";
	}
	
	@PostMapping("/regist")
	public String regLecture(@RequestParam(name = "title") String title, 
			@RequestParam(name = "content") String content){
		
		lectureService.regLecture(title, content);
		
		return "redirect:/lecture/list";
	}
  
}
