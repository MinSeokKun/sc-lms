package com.lms.sc.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime videoTime;
	
	@ManyToOne
	@JoinColumn(name="author_id")
	private SiteUser author;
	
	@ManyToOne
	Lecture lecture;
	
	@ManyToOne
	LecVideo video;
}
