package com.lms.sc.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.service.UserVideoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/userVideo")
public class UserVideoController {
	private final UserVideoService userVideoService;
	
	@PostMapping("/save")
	public ResponseEntity<Void> saveUserVideo(@RequestBody Map<String, Object> data){
		SiteUser user = (SiteUser)data.get("user");
		Video video = (Video)data.get("video");
		boolean watched = (boolean)data.get("watched");
		Instant watchedAt = Instant.parse((String)data.get("watchedAt"));
		
		userVideoService.saveUserVideo(user, video, watched, watchedAt);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
