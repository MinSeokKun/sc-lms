package com.lms.sc.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
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
	
	// 유저 프로필 등록
    public SiteUser updateProfileImage(Long userId, MultipartFile file) throws IOException {
        SiteUser user = getUserById(userId);
        
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;
        
        // file: 접두사 제거
        String cleanUploadDir = uploadDir.startsWith("file:") ? uploadDir.substring(5) : uploadDir;
        
        Path uploadPath = Paths.get(cleanUploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        Path targetLocation = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        
        user.setProfileImage("/images/user/" + newFileName);
        return userRepository.save(user);
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
