package com.lms.sc.controller;

import java.security.Principal;
import java.util.List;

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
	private final VideoService VideoService;
	private final LectureService lectureService;
	private final NoteService noteService;
	private final UserService userService;
	
	@GetMapping("/viewer/{vidId}")
	public String getVideo(Model model, @PathVariable("vidId") long vidId, 
			@RequestParam(value = "tab", defaultValue = "none") String tab, Principal principal) throws Exception {
		
		Video video = VideoService.getVideo(vidId);
		Lecture lecture = lectureService.getLecture(video.getLecture().getId());
		
		model.addAttribute("video", video);
		model.addAttribute("lecture", lecture);
		
		SiteUser user = userService.getUserByEmail(principal.getName());
		List<Note> noteList = noteService.getByVideo(vidId, user.getId());
		model.addAttribute("noteList", noteList);
		if (tab.equals("note")) {
		}
		
		return "video/viewer";
	}
	
	//강의 마다 영상 등록 페이지로 이동
	@GetMapping("/addVideo/{id}")
	public String addVideo(Model model, @PathVariable("id") long id) throws Exception {
		Lecture lecture = lectureService.getLecture(id);
		
		model.addAttribute("lecture", lecture);
		
		return "admin/video_form";
	}
	
	@PostMapping("/addVideo/{lec_id}")
	public String regVideo(@PathVariable("lec_id") long lec_id,
							@RequestParam(name = "title[]") String[] title, @RequestParam(name = "url[]") String[] url) throws Exception {

		//VideoService.regVideo(title, url, lec_id);
		for (int i = 0; i < title.length; i++) {
	        VideoService.regVideo(title[i], url[i], lec_id);
	    }
		
		return "redirect:/lecture/list";
	}
	
	
}
