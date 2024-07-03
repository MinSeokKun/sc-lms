package com.lms.sc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.NoteRepository;
import com.lms.sc.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VideoService {
	private final LectureRepository lecRepo;
	private final VideoRepository videoRepo;
	private final NoteRepository noteRepo;
	private final YouTubeService youtubeService;
	
	//비디오 하나 가져오기
	public Video getVideo(long id) throws Exception {
		Optional<Video> op = videoRepo.findById(id);
		if (op.isPresent())
			return op.get();
		else
			throw new Exception();
	}
	
	// 다음 비디오 하나 가져오기
	public Video getNextVideo(long videoId) {
		Video video = videoRepo.findById(videoId).get();
		Lecture lecture = video.getLecture();
		List<Video> videos = videoRepo.findAllByLecture(lecture);
		for (int i = 0; i < videos.size(); i++) {
			if (videos.get(i).getId() == videoId) {
				return i < videos.size() - 1 ? videos.get(i + 1) : null;
			}
		}
		return null;
	}
	
	// 이전 비디오 하나 가져오기
	public Video getPreVideo(long videoId) {
		Video video = videoRepo.findById(videoId).get();
		Lecture lecture = video.getLecture();
		List<Video> videos = videoRepo.findAllByLecture(lecture);
		for (int i = 0; i < videos.size(); i++) {
			if (videos.get(i).getId() == videoId) {
				return i > 0 ? videos.get(i - 1) : null;
			}
		}
		return null;
	}
	
	//비디오 등록
	public Video regVideo(String title, String url, long lecId) {
		Video video = new Video();
		int duration = youtubeService.getVideoDuration(url);
		video.setLecture(lecRepo.findById(lecId).get());
		video.setTitle(title);
		video.setUrl(url);
		video.setDuration(duration);
		return videoRepo.save(video);
	}
	
	//비디오 리스트
	public List<Video> VideoList(Lecture lecture) {
		//Lecture lecture = lecRepo.findById(lecId).get();
		return videoRepo.findAllByLecture(lecture);
	}
	
	// 비디오 삭제
	public void delVideo(Video video) {
		noteRepo.deleteAllByVideo(video);
		// 그냥 삭제하면 videoId를 외래키로 사용하는 Note때문에 삭제가 안됨
		// 따라서 List<Note>를 불러와 videoId를 사용하는 note를 전부 삭제후 video삭제
		videoRepo.delete(video);
	}
}









