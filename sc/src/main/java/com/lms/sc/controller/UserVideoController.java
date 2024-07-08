package com.lms.sc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserLecture;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
import com.lms.sc.service.NoteService;
import com.lms.sc.service.UserLectureService;
import com.lms.sc.service.UserService;
import com.lms.sc.service.UserVideoService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userVideo")
public class UserVideoController {
	private final UserVideoService userVideoService;
	private final UserService userService;
	private final VideoService videoService;
	private final UserLectureService userLecService;
	private final NoteService noteService;
	
	@PostMapping("/save")
	public ResponseEntity<Void> saveUserVideo(@RequestBody Map<String, Object> data) {
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			long userId = objectMapper.convertValue(data.get("userId"), long.class);
			long videoId = objectMapper.convertValue(data.get("videoId"), long.class);
			
			SiteUser user = userService.getUserById(userId);
			Video video = videoService.getVideo(videoId);
			
			UserVideo userVideo = userVideoService.getUserVideoOrNew(user, video);
			
			boolean watched = objectMapper.convertValue(data.get("watched"), boolean.class);
			Date watchedAt = objectMapper.convertValue(data.get("watchedAt"), Date.class);
			Integer watchingTime = objectMapper.convertValue(data.get("watchingTime"), Integer.class);
			
			userVideoService.saveUserVideo(userVideo, watched, watchedAt, watchingTime);
			
			// watched 가 true 일 때 userLecture의 progress를 업데이트
			if (watched) {
				UserLecture userLec = userLecService.getMyUserLec(user, video.getLecture());
				List<Video> videoList = noteService.getVideosByLecture(video.getLecture().getId(), userId);
				List<UserVideo> watchedList = userVideoService.getUserVideoByWatched(user, video.getLecture(), true);
				double progress = (double) watchedList.size() / videoList.size();
				userLec.setProgress(progress);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
