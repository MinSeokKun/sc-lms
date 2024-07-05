package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final LectureService lectureService;
	private final UserService userService;
	
	@GetMapping("/")
	public String root(Model model, Principal principal) {
		if (principal != null) {
			SiteUser user = userService.getUserByEmail(principal.getName());
			model.addAttribute("user", user);
		}
		
		List<Lecture> lecture = lectureService.lecList();
		model.addAttribute("lecture", lecture);
		return "main/main";
	}
	
}
