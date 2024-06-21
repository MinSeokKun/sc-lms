package com.lms.sc.sevice;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.LecVideo;
import com.lms.sc.entity.Lecture;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.LecvideoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LecVideoService {
	private final LecvideoRepository lecvideor;
	
	//강의 찾기
	public LecVideo getVideo(long id) throws Exception {
		Optional<LecVideo> lecVideo = lecvideor.findById(id);
		
		if(lecVideo.isPresent()) {
			return lecVideo.get();			
		}else {
			throw new Exception();
		}
	}
}
