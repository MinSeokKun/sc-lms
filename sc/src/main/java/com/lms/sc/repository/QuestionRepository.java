package com.lms.sc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Question;
import com.lms.sc.entity.Video;


public interface QuestionRepository extends JpaRepository<Question, Integer>{
//	@EntityGraph(attributePaths = {"answerList"})
	Page<Question> findAll(Pageable pageable);
	
	void deleteAllByVideo(Video video);
}
