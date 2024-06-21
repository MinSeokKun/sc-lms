package com.lms.sc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.entity.Lecture;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lecture")
@RequiredArgsConstructor
@Controller
public class LectureController {
	
	private final VideoService lecVideoService;
	private final LectureService lectureService;
	
	//강의 정보 자세히 보기
	@GetMapping("/list/{lec_id}")
	public String getLecture(Model model, @PathVariable("lec_id") long lec_id) {
		
		
		return "lecture/lectureDatail";
	}
	
	//강의 리스트 이동
	@GetMapping("/list")
	public String lecList(Model model) {
		List<Lecture> lecture = lectureService.lecList();
		model.addAttribute("lecture", lecture);
		return "admin/lec_list";
	}
	
	//강의 등록 이동
	@GetMapping("/regist")
	public String regLectureForm() {
		return "admin/lec_register";
	}
	
	//강의 등록
	@PostMapping("/regist")
	public String regLecture(@RequestParam(name = "title") String title, 
			@RequestParam(name = "content") String content){
		
		lectureService.regLecture(title, content);
		
		return "redirect:/lecture/list";
	}
	
	
  
}
