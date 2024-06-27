package com.lms.sc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.DataNotFoundException;
import com.lms.sc.entity.Question;
import com.lms.sc.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionReqRepository;
	
	public List<Question> getList() {
		return this.questionReqRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionReqRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String title, String content) {
		Question q = new Question();
		q.setTitle(title);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		this.questionReqRepository.save(q);
	}
}
