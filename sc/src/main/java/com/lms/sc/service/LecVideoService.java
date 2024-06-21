package com.lms.sc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.LecVideo;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.LecvideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LecVideoService {
	private final LectureRepository lecRepo;
	private final LecvideoRepository videoRepo;
	
	
	public LecVideo getVideo(long id) throws Exception {
		Optional<LecVideo> op = videoRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	public LecVideo regVideo(String title, String url, long lecId) {
		LecVideo video = new LecVideo();
		video.setLecture(lecRepo.findById(lecId).get());
		video.setTitle(title);
		video.setUrl(url);
		return videoRepo.save(video);
	}
}
