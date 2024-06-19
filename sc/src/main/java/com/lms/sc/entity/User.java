package com.lms.sc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	@Id
	@GeneratedValue
	private Integer userId;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String tellNumber;
	
	private String profileImage;
}
