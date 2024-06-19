package com.lms.sc.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long lecId;
	
	@Column(length = 200)
	private String lecTit;
	
	@Column(columnDefinition = "TEXT")
	private String lecContent;
	
	@CreatedDate
	private LocalDateTime lecRegDate;
	
	@ManyToMany
	Set<SiteUser> lecStdCnt;
	
	@ManyToMany
	Set<Review> lecReviewCnt;
}
