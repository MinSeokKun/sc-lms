package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.createForm.AnswerCreateForm;
import com.lms.sc.createForm.NoticeCreateForm;
import com.lms.sc.entity.Notice;
import com.lms.sc.exception.DataNotFoundException;
import com.lms.sc.service.NoticeService;
import com.lms.sc.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	private final UserService userService;
	
	@GetMapping("list")
	public String list(Model model) {
		List<Notice> list = this.noticeService.getList();
		model.addAttribute("list", list);
		return "notice/notice_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerCreateForm answerCreateForm) {
		Notice notice = this.noticeService.getNotice(id);
		if (notice == null) {
			throw new DataNotFoundException("Notice not found");
		}
		model.addAttribute("notice", notice);
		return "notice/notice_detail";
	}
	
	@GetMapping("/create")
	public String noticeCreate(NoticeCreateForm noticeCreateForm) {
		return "notice/notice_form";
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create") 
	public String noticeCreate(@Valid NoticeCreateForm noticeCreateForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "notice/notice_form";
		}
		this.noticeService.create(noticeCreateForm.getTitle(), noticeCreateForm.getContent(), userService.getUserByEmail(principal.getName()));
		return "redirect:/notice/list";
	}
}
