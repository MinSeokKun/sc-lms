package com.lms.sc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.createForm.AnswerCreateForm;
import com.lms.sc.entity.Notice;
import com.lms.sc.exception.DataNotFoundException;
import com.lms.sc.service.NoticeService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	private final NoticeService noticeService;
	
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
}
