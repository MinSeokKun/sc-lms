package com.lms.sc.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password, String tellNumber, String profileImage) {
		SiteUser user = new SiteUser();
		user.setEmail(email);
		user.setName(username);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(password));
		user.setTellNumber(tellNumber);
		user.setProfileImage(profileImage);
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String username) {
		return userRepository.findByName(username).get();
	}
	
	public SiteUser getUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
}
