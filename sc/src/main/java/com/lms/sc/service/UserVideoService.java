package com.lms.sc.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.UserVideoRepository;
import com.lms.sc.repository.VideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserVideoService {
	private final UserVideoRepository userVideoRepository;
	
//	public UserVideo getUserVideo(SiteUser user, Video video) {
//        return userVideoRepository.findByUserAndVideo(user, video).orElse(new UserVideo());
//    }
	
	
	public void saveUserVideo(SiteUser user, Video video, boolean watched, Instant watchedAt) {
		UserVideo userVideo = userVideoRepository.findByUserAndVideo(user, video).orElse(new UserVideo());
		userVideo.setUser(user);
		userVideo.setVideo(video);
		userVideo.setWatched(watched);
		userVideo.setWatchedAt(watchedAt);
		userVideoRepository.save(userVideo);
	}
}
