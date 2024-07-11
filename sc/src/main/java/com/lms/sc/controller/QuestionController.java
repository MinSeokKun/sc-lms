package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.lms.sc.createForm.AnswerCreateForm;
import com.lms.sc.createForm.QuestionCreateForm;
import com.lms.sc.entity.Answer;
import com.lms.sc.entity.Question;
import com.lms.sc.exception.DataNotFoundException;
import com.lms.sc.service.AnswerService;
import com.lms.sc.service.QuestionService;
import com.lms.sc.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@GetMapping("/list") 
	public String list(Model model, @RequestParam(value ="page", defaultValue = "0") int page) {
		Page<Question> paging =	this.questionService.getList(page);
		model.addAttribute("paging", paging);
		return "question/question_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerCreateForm answerCreateForm) {
		Question question = this.questionService.getQuestion(id);
		if (question == null) {
			throw new DataNotFoundException("Question not found");
		}
		List<Answer> answerList = answerService.getAnswerList(question);
		model.addAttribute("answerList", answerList);
		model.addAttribute("question", question);
		return "question/question_detail";
	}
	
	@GetMapping("/create")
	public String questionCreate(QuestionCreateForm questionCreateForm) {
		return "question/question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create") 
	public String questionCreate(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "question/question_form";
		}
		this.questionService.create(questionCreateForm.getTitle(), questionCreateForm.getContent(), userService.getUserByEmail(principal.getName()));
		return "redirect:/question/list"; //질문 저장 후 질문 목록으로 이동 }
	}
	
	@GetMapping("/delete/{id}")
	public String questionDelete(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (question == null) {
			throw new DataNotFoundException("Question not found");
		}
		questionService.delete(question);
		
		return "redirect:/question/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionCreateForm questionCreateForm, @PathVariable("id") Integer id, Principal principal) {
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getName().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		questionCreateForm.setTitle(question.getTitle());
		questionCreateForm.setContent(question.getContent());
		return "question/question_form";
		
	}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String questionModify(@Valid QuestionCreateForm questionCreateForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if(!question.getAuthor().getName().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
		}
		this.questionService.modify(question, questionCreateForm.getTitle(), questionCreateForm.getContent());
		return String.format("redirect:/question/detail/%s", id);
	}
	
}
