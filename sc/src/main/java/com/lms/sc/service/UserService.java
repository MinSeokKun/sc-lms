package com.lms.sc.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lms.sc.entity.CommonUtil;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.exception.DataNotFoundException;
import com.lms.sc.exception.EmailException;
import com.lms.sc.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordMailService pms;
	private final CommonUtil cu;
	
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
		user.setCreateDate(LocalDateTime.now());
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
	public void modify(SiteUser user, String name, String newPassword, String tellNumber) {
	    user.setName(name);
	    if (newPassword != null) {
	        user.setPassword(newPassword);
	    }
	    user.setTellNumber(tellNumber);
	    userRepository.save(user);
	}
	
	// 회원 목록 - 어드민
	public Page<SiteUser> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("id"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return userRepository.findAllByKeyword(kw, pageable);
	}
	
	//임시 비밀번호 이메일 전송
	public void modifyPassword(String email) throws EmailException{
		//CommonUtil 클래스안의 createTempPassword 불러온다.
		String tempPassword = cu.createTempPassword();
		
		//ur(유저레포지토리)에서 주어진 이메일을 찾고 없으면 예외메세지를 넘겨준다.
		SiteUser user = userRepository.findByEmail(email).orElseThrow(()-> new DataNotFoundException("해당 이메일의 유저가 없습니다."));
		//찾은 사용자의 비밀번호을 임시번호로 설정합니다. encode코드를 사용해 
		//임시비밀번호를 암호화하고 암호와된 비밀번호를 사용자 객체에 설정합니다.
		user.setPassword(passwordEncoder.encode(tempPassword));
		//user.setTemppassword(true);
		//디비에 저장합니다.
		userRepository.save(user);
		//사용자의 임시비밀번호를 전송합니다.
		pms.sendSimpleMessage(email, tempPassword);
	}
	
}
