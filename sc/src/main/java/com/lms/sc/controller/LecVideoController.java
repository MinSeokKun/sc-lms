package com.lms.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.entity.LecVideo;
import com.lms.sc.entity.Lecture;
import com.lms.sc.service.LecVideoService;
import com.lms.sc.service.LectureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/video")
public class LecVideoController {
	private final LecVideoService videoSer;
	private final LectureService lecSer;
	
	@GetMapping("/viewer/{lecid}/{vidid}")
	public String watchVideo(@PathVariable("lecid") long lecId, @PathVariable("vidid") long vidId,  Model model) throws Exception {
		Lecture lecture = lecSer.getLecture(lecId);
		LecVideo video = videoSer.getVideo(vidId);
		model.addAttribute("lecture", lecture);
		model.addAttribute("video", video);
		return "video/viewer";
	}
}
