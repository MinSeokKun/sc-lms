package com.lms.sc.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long answerId;
	
	@Column(columnDefinition = "TEXT")
	private String answerContent;
	
	private int answerLikeCnt;
	
	@CreatedDate
	private LocalDateTime answerCreateDate;
	
	private LocalDateTime answerModifyDate;
	
	@ManyToOne
	private Question question;
	
	@ManyToOne
	private SiteUser author;
}
