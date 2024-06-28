package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.Note;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.service.LectureService;
import com.lms.sc.service.NoteService;
import com.lms.sc.service.UserService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;



@RequestMapping("/video")
@RequiredArgsConstructor
@Controller
public class VideoController {
	private final VideoService videoService;
	private final LectureService lectureService;
	private final NoteService noteService;
	private final UserService userService;
	
	//비디오 하나
	@GetMapping("/viewer/{vidId}")
	public String getVideo(Model model, @PathVariable("vidId") long vidId, Principal principal) throws Exception {
		if(principal == null) {
			return "redirect:/user/login";
		}
		
		Video video = videoService.getVideo(vidId);
		Lecture lecture = lectureService.getLecture(video.getLecture().getId());
		
		model.addAttribute("video", video);
		model.addAttribute("lecture", lecture);
		
		SiteUser user = userService.getUserByEmail(principal.getName());
		List<Note> noteList = noteService.getByVideo(vidId, user.getId());
		model.addAttribute("noteList", noteList);
		
		List<Video> videoList = videoService.VideoList(lecture);
		model.addAttribute("videoList", videoList);
		
		return "video/viewer";
	}
	
	//강의 마다 영상 등록 페이지로 이동
	@GetMapping("/addVideo/{id}")
	public String addVideo(Model model, @PathVariable("id") long id) throws Exception {
		Lecture lecture = lectureService.getLecture(id);
		
		model.addAttribute("lecture", lecture);
		
		return "admin/video_form";
	}
	
	//비디오 등록
	@PostMapping("/addVideo/{lec_id}")
	public String regVideo(@PathVariable("lec_id") long lec_id,
							@RequestParam(name = "title[]") String[] title, @RequestParam(name = "url[]") String[] url) throws Exception {

		//VideoService.regVideo(title, url, lec_id);
		for (int i = 0; i < title.length; i++) {
	        videoService.regVideo(title[i], url[i], lec_id);
	    }
		
		return String.format("redirect:/video/list/%s", lec_id);
	}
	
	//등록된 영상 리스트
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list/{lec_id}")
	public String videoList(Model model, @PathVariable("lec_id") long lec_id) throws Exception {
		Lecture lecture = lectureService.getLecture(lec_id);		
		List<Video> video = videoService.VideoList(lecture);
		
		model.addAttribute("video", video);
		return "admin/video_list";
	}
	
}
