package com.lms.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
