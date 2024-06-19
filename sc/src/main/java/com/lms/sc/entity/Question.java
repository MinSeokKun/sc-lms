package com.lms.sc.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question {
	@Id
	@GeneratedValue
	private Integer questId;
	
	@Column(length = 100)
	String questTitle;
	
	@Column(columnDefinition = "TEXT")
	private String questContent;
	
	private Integer questLikeCnt;
	
	@CreatedDate
	private LocalDateTime questCreateDate;
	
	private LocalDateTime questModifyDate;
	
	private int questResult;
	
	@ManyToOne
	private SiteUser user;
	
	@ManyToOne
	private Video video;
}

