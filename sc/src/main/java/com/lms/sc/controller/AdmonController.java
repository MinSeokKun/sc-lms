package com.lms.sc.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdmonController {
	
	private final UserService userService;
	
	//유저 정보가기
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/userList")
	public String getUserList(Model model, @RequestParam(value = "kw", defaultValue = "") String kw, @RequestParam(value = "page", defaultValue = "0") int page) {
	    Page<SiteUser> paging = userService.getList(page, kw);
	    model.addAttribute("paging", paging);
	    model.addAttribute("kw", kw);
	    return "admin/userinfo";
	}
}
