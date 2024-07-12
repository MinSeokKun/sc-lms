package com.lms.sc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	List<Question> findByTitleContainingAndContentContainingAndAuthor(String title, String content, SiteUser author);
	
	@Query("SELECT q FROM Question q WHERE q.title LIKE %:keyword% OR q.content LIKE %:keyword% OR q.author.name LIKE %:keyword%")
    Page<Question> searchQuestions(@Param("keyword") String keyword, Pageable pageable);
	
	@Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answerList WHERE q.title LIKE %:keyword% OR q.content LIKE %:keyword%")
	Page<Question> findByKeywordWithAnswers(@Param("keyword") String keyword, Pageable pageable);
}
