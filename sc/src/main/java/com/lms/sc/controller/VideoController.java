package com.lms.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.entity.Video;
import com.lms.sc.entity.Lecture;
import com.lms.sc.service.VideoService;
import com.lms.sc.service.LectureService;

import lombok.RequiredArgsConstructor;


@RequestMapping("/video")
@RequiredArgsConstructor
@Controller
public class VideoController {
	private final VideoService lecVideoService;
	private final LectureService lectureService;
	
	@GetMapping("/viewer/{vid_id}")
	public String getMethodName(Model model, @PathVariable("vid_id") long vid_id) throws Exception {
		Video video = lecVideoService.getVideo(vid_id);
		Lecture lecture = lectureService.getLecture(video.getLecture().getId());
		
		model.addAttribute("video", video);
		model.addAttribute("lecture", lecture);
		
		return "video/viewer";
	}
}
