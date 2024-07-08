package com.lms.sc.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.UserRepository;
import com.lms.sc.repository.UserVideoRepository;
import com.lms.sc.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserVideoService {
	private final UserVideoRepository userVideoRepository;
	private final UserRepository userRepository;
	private final VideoRepository videoRepository;
	
	//유저 비디오 하나 가져오기
	public UserVideo getUserVideo(long vidId, long userId) {
		SiteUser user = userRepository.findById(userId).get();
		Video video = videoRepository.findById(vidId).get();
        return userVideoRepository.findByUserAndVideo(user, video).orElse(null);
    }
	
	public void saveUserVideo(UserVideo userVideo) {
		userVideoRepository.save(userVideo);
	}
	
	//유저 비디오 정보 저장
	public void saveUserVideo(UserVideo userVideo, boolean watched, Date watchedAt, Integer watchingTime) {
		userVideo.setWatched(watched);
		userVideo.setWatchedAt(watchedAt);
		userVideo.setWatchingTime(watchingTime);
		userVideoRepository.save(userVideo);
	}
	
	//유저 비디오 있으면 강의 진행 없으면 새로만들어서 진행
	public UserVideo getUserVideoOrNew(SiteUser user, Video video) {
	    Optional<UserVideo> optionalUserVideo = userVideoRepository.findByUserAndVideo(user, video);
	    if (optionalUserVideo.isPresent()) {
	        return optionalUserVideo.get();
	    } else {
	        UserVideo userVideo = new UserVideo();
	        userVideo.setUser(user);
	        userVideo.setVideo(video);
	        userVideo.setWatchingTime(0);
	        return userVideoRepository.save(userVideo);
	    }
	}
	
	// UserVideo 중 watched가 true인 리스트를 가져오기
	public List<UserVideo> getUserVideoByWatched(SiteUser user, Lecture lecture, boolean watched){
		return userVideoRepository.findByUserAndLectureAndWatched(user, lecture, watched);
	}
	
}
