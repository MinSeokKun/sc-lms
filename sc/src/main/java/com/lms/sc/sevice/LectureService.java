package com.lms.sc.sevice;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Lecture;
import com.lms.sc.repository.LectureRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LectureService {
	
	private final LectureRepository lecturer;
	
	public Lecture getLecture(long id) throws Exception {
		Optional<Lecture> lecture = lecturer.findById(id);
		if(lecture.isPresent()) {
			return lecture.get();			
		}else {
			throw new Exception();
		}
	}
	
	public List<Lecture> lecList(){
		return lecturer.findAll();
	}
	
}







