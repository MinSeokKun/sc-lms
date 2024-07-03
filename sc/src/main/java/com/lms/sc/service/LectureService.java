package com.lms.sc.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.NoteRepository;
import com.lms.sc.repository.UserLectureRepository;
import com.lms.sc.repository.VideoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {
	private final LectureRepository lecRepo;
	private final VideoRepository videoRepo;
	private final NoteRepository noteRepo;
	private final UserLectureRepository userLecRepo;
	
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
	@Transactional
	public void remove(Lecture lecture) {
		List<Video> videoList = videoRepo.findAllByLecture(lecture);
		videoList.forEach(video -> noteRepo.deleteAllByVideo(video));;
		videoRepo.deleteAllByLecture(lecture);
		userLecRepo.deleteAllByLecture(lecture);
		// video 삭제와 마찬가지로 lecture도 lectureId를 외래키로 사용하는 video를 모두 삭제후 강의 삭제
		lecRepo.delete(lecture);
	}
	
	// 강의 중복신청 방지
	public boolean isAlreadyRegistered(SiteUser user, Lecture lecture) {
	    // user와 lecture 조합으로 UserLecture 엔티티를 검색하여 존재 여부 확인
	    return userLecRepo.existsByUserAndLecture(user, lecture);
	}
}
