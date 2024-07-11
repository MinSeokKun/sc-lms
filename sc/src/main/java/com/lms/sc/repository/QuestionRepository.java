package com.lms.sc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.Question;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;



public interface QuestionRepository extends JpaRepository<Question, Integer>{
//	@EntityGraph(attributePaths = {"answerList"})
	Page<Question> findAll(Pageable pageable);
	
	void deleteAllByVideo(Video video);
	
	List<Question> findByAuthor(SiteUser author);
	
	List<Question> findByAuthorAndVideo_Lecture(SiteUser author, Lecture lecture);
	List<Question> findByVideo_Lecture(Lecture lecture);
}
