package com.lms.sc.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer noteId;
	
	@Column(columnDefinition = "TEXT")
	private String noteContent;
	
	private LocalDateTime noteVideoTime;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	SiteUser user;
	
	@ManyToOne
	@JoinColumn(name = "lecId")
	Lecture lecture;
	
	@ManyToOne
	@JoinColumn(name = "videoId")
	Lecture video;
}
