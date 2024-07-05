package com.lms.sc.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;
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
	
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUserVideo(@RequestBody Map<String, Object> data, HttpServletRequest request) {
		// CSRF 토큰 검증 (옵션)
        String csrfToken = request.getHeader("X-XSRF-TOKEN");
        if (csrfToken == null || !csrfToken.equals(((CsrfToken) request.getAttribute("_csrf")).getToken())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "Invalid CSRF token"));
        }
		
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
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
