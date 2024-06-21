package com.lms.sc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Video;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
	private final LectureRepository lecRepo;
	private final VideoRepository videoRepo;
	
	
	public Video getVideo(long id) throws Exception {
		Optional<Video> op = videoRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	public Video regVideo(String title, String url, long lecId) {
		Video video = new Video();
		video.setLecture(lecRepo.findById(lecId).get());
		video.setTitle(title);
		video.setUrl(url);
		return videoRepo.save(video);
	}
}
