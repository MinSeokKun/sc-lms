package com.lms.sc.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.entity.Answer;
import com.lms.sc.entity.DataNotFoundException;
import com.lms.sc.entity.Question;
import com.lms.sc.service.AnswerService;
import com.lms.sc.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/question")
public class QuestionController {

	private final QuestionService questionService;
	private final AnswerService answerService;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question/question_list";
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		if (question == null) {
			throw new DataNotFoundException("Question not found");
		}
		List<Answer> answerList = answerService.getAnswerList(question);
		model.addAttribute("answerList", answerList);
		model.addAttribute("question", question);
		return "question/question_detail";
		
	}
}
