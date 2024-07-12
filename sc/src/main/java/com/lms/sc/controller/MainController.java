package com.lms.sc.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final LectureService lectureService;
	private final UserService userService;
	private final UserLectureService userLecService;
	
	@GetMapping("/")
	public String root(Model model, Principal principal) {
		if (principal != null) {
			SiteUser user = userService.getUserByEmail(principal.getName());
			model.addAttribute("user", user);
		}
		
		List<Lecture> lecture = lectureService.lecList();
		Map<Lecture, Integer> userLecture = new LinkedHashMap<>();
		for (Lecture lec : lecture) {
			int students = 0;
			if (userLecService.getStudents(lec) != 0) {
				students = userLecService.getStudents(lec);
			}
			userLecture.put(lec, students);
		}
		
		model.addAttribute("userLecture", userLecture);
		model.addAttribute("lecture", lecture);
		return "main/main";
	}
	
}
