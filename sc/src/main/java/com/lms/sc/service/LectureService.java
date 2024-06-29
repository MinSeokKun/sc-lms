package com.lms.sc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.repository.LectureRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {
	private final LectureRepository lecRepo;
	
	//강의 아이디 가져오기
	public Lecture getLecture(long id) throws Exception {
		Optional<Lecture> op = lecRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	//강의 등록
	public Lecture regLecture(String title, String content) {
		Lecture lecture = new Lecture();
		lecture.setTitle(title);
		lecture.setContent(content);
		lecture.setCreateDate(LocalDateTime.now());
		return lecRepo.save(lecture);
	}
	
	//강의 리스트
	public List<Lecture> lecList(){
		return lecRepo.findAll();
	}
	
	// 강의를 유저도 함께 가져오기
	@Transactional(readOnly = true)
	public Lecture getLectureWithStu(long id) {
		return lecRepo.findByIdWithStudents(id).orElseThrow(() -> new EntityNotFoundException("강의가 없습니다."));
		
	}
	
	// 강의 시작
	@Transactional
	public void studentAdd(Lecture lecture, SiteUser student) {
		lecture.getStudents().add(student);
		lecRepo.save(lecture);
	}
	
	//강의 수정
	public void modify(Lecture lecture, String title, String content) {
		lecture.setTitle(title);
		lecture.setContent(content);
		
		lecRepo.save(lecture);
	}
	
	// 강의 삭제
	public void remove(Lecture lecture) {
		lecRepo.delete(lecture);
	}
}
