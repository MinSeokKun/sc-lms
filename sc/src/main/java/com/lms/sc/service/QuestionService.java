package com.lms.sc.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.sc.entity.Question;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.exception.DataNotFoundException;
import com.lms.sc.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	private final QuestionRepository questionRepository;
	
	@Transactional(readOnly = true)
	public Page<Question> getList(int page){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		Page<Question> questions = questionRepository.findAll(pageable);
		   questions.forEach(question -> Hibernate.initialize(question.getAnswerList()));
        return questions;
	}
	
	 public List<Question> getList() { 
		 return this.questionRepository.findAll(); 
	 	}
	 
	 public List<Question> getListByAuthor(SiteUser author) {
		 return this.questionRepository.findByAuthor(author);
	 }


	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if(question.isPresent()) {
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String title, String content, SiteUser author) {
		Question q = new Question();
		q.setTitle(title);
		q.setContent(content);
		q.setAuthor(author);
		q.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q);
	}
	
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
}
