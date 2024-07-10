package com.lms.sc.service;

import java.util.Optional;

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
	
	//회원가입
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
	
	// 유저 하나 가져오기 
	public SiteUser getUser(String username) {
		return userRepository.findByName(username).get();
	}
	
	// 유저 이메일로 가져오기
	public SiteUser getUserByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
	
	// 유저 아이디로 가져오기
	public SiteUser getUserById(long id) {
		return userRepository.findById(id).get();
	}
	
	// 유저 이메일 중복 확인
	public String checkEmail(String email) {
		Optional<SiteUser> user = userRepository.findByEmail(email);
		
		return user.isPresent() ? "false" : "true";
	}
	
	// 유저 정보 수정
	public void modify(SiteUser user, String name, String password, String tellNumber) {
		user.setName(name);
		if(password != null && !password.isEmpty()) {
			user.setPassword(passwordEncoder.encode(password));			
		}
		user.setTellNumber(tellNumber);
		
		userRepository.save(user);
	}
	
}
