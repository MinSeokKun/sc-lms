package com.lms.sc.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Lecture;
import com.lms.sc.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureService {
	private final LectureRepository lecRepo;
	
	public Lecture getLecture(long id) throws Exception {
		Optional<Lecture> op = lecRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	public Lecture regLecture(String title, String content) {
		Lecture lecture = new Lecture();
		lecture.setTitle(title);
		lecture.setContent(content);
		lecture.setCreateDate(LocalDateTime.now());
		return lecRepo.save(lecture);
	}
}
