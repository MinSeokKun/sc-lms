package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserLecture;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/my")
public class UserLectureController {
	private final UserLectureService userLectureService;
	private final UserService userService;
//	private final LectureService lectureService;
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("list")
	public String getMyList(Principal principal, Model model) throws Exception {
		if (principal == null) {
			return "user/login";
		}
		
		SiteUser user = userService.getUserByEmail(principal.getName());
		List<UserLecture> userLectureList = userLectureService.getMyList(user);
		model.addAttribute("userLectureList", userLectureList);
		
		return "mypage/my_list";
	}
	
	//강의 중복 확인
	@PreAuthorize("isAuthenticated()")
	@GetMapping("lecCheck")
	@ResponseBody
	public String lecChk(Principal principal, @RequestParam("lecId") Long lecId) throws Exception {
		String user = principal.getName();
		return userLectureService.checkLec(user, lecId);
	}
	
	
	// 대시보드
	@PreAuthorize("isAuthenticated()")
	@GetMapping("dashboard")
	public String dashboard(Principal principal, Model model) {
		if (principal == null) {
			return "user/login";
		}
		
		SiteUser user = userService.getUserByEmail(principal.getName());
		return "mypage/dashboard";
	}
	
}
