package com.lms.sc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserLecture;
import com.lms.sc.repository.UserLectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLectureService {
//	private final LectureRepository lectureRepository;
//	private final VideoRepository videoRepository;
//	private final UserRepository userRepository;
	
	private final UserLectureRepository userLectureRepository;
	
	// 나의 강의 리스트
	public List<UserLecture> getMyList(SiteUser user){		
		
		List<UserLecture> userLectureList = userLectureRepository.findByUser(user);
		return userLectureList;
	}
	
	// 강의 수강 시작시 UserLecture도 등록되게
	public UserLecture createUserLecture(SiteUser user, Lecture lecture) {
		UserLecture userLec = new UserLecture();
		userLec.setUser(user);
		userLec.setLecture(lecture);
		userLec.setProgress(0);
		return userLectureRepository.save(userLec);
	}
}
