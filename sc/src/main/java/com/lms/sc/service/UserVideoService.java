package com.lms.sc.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.UserVideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserVideoService {
	private final UserVideoRepository userVideoRepository;
	
//	public UserVideo getUserVideo(SiteUser user, Video video) {
//        return userVideoRepository.findByUserAndVideo(user, video).orElse(new UserVideo());
//    }
	
	public void saveUserVideo(UserVideo userVideo) {
		userVideoRepository.save(userVideo);
	}
	
	public void saveUserVideo(UserVideo userVideo, boolean watched, Date watchedAt, Integer watchingTime) {
		userVideo.setWatched(watched);
		userVideo.setWatchedAt(watchedAt);
		userVideo.setWatchingTime(watchingTime);
		userVideoRepository.save(userVideo);
	}
	
	public UserVideo getUserVideoOrNew(SiteUser user, Video video) {
	    Optional<UserVideo> optionalUserVideo = userVideoRepository.findByUserAndVideo(user, video);
	    if (optionalUserVideo.isPresent()) {
	        return optionalUserVideo.get();
	    } else {
	        UserVideo userVideo = new UserVideo();
	        userVideo.setUser(user);
	        userVideo.setVideo(video);
	        return userVideoRepository.save(userVideo);
	    }
	}
}
