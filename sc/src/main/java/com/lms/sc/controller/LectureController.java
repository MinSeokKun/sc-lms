package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/lecture")
@RequiredArgsConstructor
@Controller
public class LectureController {
	
	private final LectureService lectureService;
	private final VideoService videoService;
	private final UserService userService;
	private final UserLectureService userLecService;
	
	//강의 리스트 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String lecList(Model model) {
		List<Lecture> lecture = lectureService.lecList();
		model.addAttribute("lecture", lecture);
		return "admin/lec_list";
	}
	
	// 강의 상세페이지 이동
	@GetMapping("/detail/{lecId}")
	public String lecDetail(Model model, @PathVariable("lecId") long lecId) throws Exception {
		Lecture lecture = lectureService.getLecture(lecId);
		model.addAttribute("lecture", lecture);
		List<Video> videoList = videoService.VideoList(lecture);
		model.addAttribute("videoList", videoList);
		return "lecture/lec_detail";
	}
	
	
	//강의 등록 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/regist")
	public String regLectureForm() {
		return "admin/lec_register";
	}
	
	//강의 등록
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/regist")
	public String regLecture(@RequestParam(name = "title") String title, 
			@RequestParam(name = "content") String content){
		
		lectureService.regLecture(title, content);
		
		return "redirect:/lecture/list";
	}
	
	// 강의 시작
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/startlearn/{lecId}")
	public String getMethodName(@PathVariable("lecId") long lecId, Principal principal) throws Exception {
		SiteUser user = userService.getUserByEmail(principal.getName());
		Lecture lecture = lectureService.getLectureWithStu(lecId);
		lectureService.studentAdd(lecture, user);
		userLecService.createUserLecture(user, lecture);
		return "mypage/my_list";
	}
	
	//강의 수정페이지 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modify(Model model, @PathVariable("id") long id) throws Exception {
		Lecture lecture = lectureService.getLecture(id);
		
		model.addAttribute("lecture", lecture);
		return "admin/lec_modify";
	}
	
	//강의 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modify(@PathVariable("id") long id, @RequestParam("title") String title, @RequestParam("content") String content) throws Exception {
		Lecture lecture = lectureService.getLecture(id);
		
		lectureService.modify(lecture, title, content);
		return "redirect:/lecture/list";
	}
  
}
