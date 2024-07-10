package com.lms.sc.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lms.sc.createForm.UserCreateForm;
import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	private final UserLectureService userLecService;
	private final LectureService lecService;
	
	// 회원가입 이동
	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "user/sign_up";
	}
	
	// 회원가입
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "user/sign_up";
		}
		if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
			return "user/sign_up";
		}
		
		userService.create(userCreateForm.getName(), userCreateForm.getEmail(), userCreateForm.getPassword1(), userCreateForm.getTellNumber(), userCreateForm.getProfileImg());		
		return "redirect:/user/login";
	}
	
	// 로그인 이동
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	//이메일 중복 체크
	@PostMapping("/emailCheck")
	@ResponseBody
	public String CheckEamil(@RequestParam("email") String email) {
		return userService.checkEmail(email);
	}
	
//	@GetMapping("/getUser")
//	public SiteUser getUser(Principal principal) {
//		SiteUser user = userService.getUser(principal.getName());
//		return user;
//	}
	
	@GetMapping("/getUser")
    public ResponseEntity<SiteUser> getCurrentUser(Principal principal) {
        SiteUser user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(user);
    }
	
	// 강의 목록 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/lec/delete/{lecId}")
	public String delLecture(Principal principal, @PathVariable("lecId") long lecId) throws Exception {
		SiteUser user = userService.getUserByEmail(principal.getName());
		Lecture lecture = lecService.getLecture(lecId);
		lecService.lectureSave(lecture);
		userLecService.deleteLec(user, lecture);
		return "redirect:/my/list";
	}
	
	// 마이페이지 이동
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mypage")
	public String myPage(Model model, Principal principal) {
		if(principal == null) {
			return "main/main";
		}
		
		SiteUser user = userService.getUserByEmail(principal.getName());
		model.addAttribute("user", user);
		
		return "mypage/mypage";
	}
	
	//유저 정보 수정
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify")
	public String modify(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("tellNumber") String tellNumber) {
	    SiteUser user = userService.getUserById(id);
	    userService.modify(user, name, password, tellNumber);
	    return "redirect:/user/mypage";
	}

}
