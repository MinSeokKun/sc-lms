package com.lms.sc.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 20)
	private String name;
	
	@Column(length = 50)
	private String email;
	
	@Column(length = 100)
	private String password;
	
	@Column(length = 20)
	private String tellNumber;
	
	@Column(columnDefinition = "TEXT")
	private String profileImage;

	@OneToMany
	List<Lecture> lecList;
	
}
