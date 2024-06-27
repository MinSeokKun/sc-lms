package com.lms.sc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lms.sc.entity.Lecture;
import com.lms.sc.service.LectureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final LectureService lectureService;
	
	@GetMapping("/")
	public String root(Model model) {
		List<Lecture> lecture = lectureService.lecList();
		model.addAttribute("lecture", lecture);
		return "main/main";
	}
	
}
