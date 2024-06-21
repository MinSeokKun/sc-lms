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
	
	//비디오 하나 가져오기
	public Video getVideo(long id) throws Exception {
		Optional<Video> op = videoRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	//비디오 등록
	public Video regVideo(String title, String url, long lecId) {
		Video video = new Video();
		video.setLecture(lecRepo.findById(lecId).get());
		video.setTitle(title);
		video.setUrl("https://www.youtube.com/embed/" + url);
		return videoRepo.save(video);
	}
}
