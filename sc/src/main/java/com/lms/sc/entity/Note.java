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
	private long noteId;
	
	@Column(columnDefinition = "TEXT")
	private String noteContent;
	
	private LocalDateTime noteVideoTime;
	
	@ManyToOne
	private SiteUser author;
	
	@ManyToOne
	Lecture lecture;
	
	@ManyToOne
	Lecture video;
}
