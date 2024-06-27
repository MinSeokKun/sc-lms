package com.lms.sc.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.Note;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.service.AsyncService;
import com.lms.sc.service.NoteService;
import com.lms.sc.service.UserService;
import com.lms.sc.service.VideoService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
	
	private final NoteService noteService;
	private final UserService userService;
	private final VideoService videoService;
	private final AsyncService asyncService;
	
	// 강의 별 노트 갯수 리스트
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public String getNoteList(Principal principal, Model model) {
		if (principal == null) {
			return "user/login";
		}
		SiteUser user = userService.getUserByEmail(principal.getName());
		long userId = user.getId();
		List<Lecture> noteLecture = noteService.getNoteLecture(userId);
		
		Map<Lecture, Integer> noteList = new HashMap<Lecture, Integer>();
		noteLecture.forEach(lecture -> 
			noteList.put(lecture, noteService.getByLecture(lecture.getId(), userId).size()));
		model.addAttribute("userId", userId);
		model.addAttribute("noteList", noteList);
		return "note/note_list";
	}
	
	// 강의의 영상별 노트 리스트
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list/{lecId}")
	public String getMethodName(Principal principal,
			@PathVariable("lecId") long lecId, Model model) {
		if (principal == null) {
			return "user/login";
		}
		SiteUser user = userService.getUserByEmail(principal.getName());
		List<Video> videoList = noteService.getVideosByLecture(lecId, user.getId());
		Map<Video, List<Note>> noteList = new HashMap<Video, List<Note>>();
		
		videoList.forEach(video -> noteList.put(video, noteService.getByVideo(video.getId(), user.getId())));
		
		model.addAttribute("noteList", noteList);
		
		return "note/note_lecture";
	}
	
	// 노트 등록
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{videoId}")
	@ResponseBody
	public Note createNote(@PathVariable("videoId") long videoId, @RequestBody Map<String, Object> payload, Principal principal) throws Exception {
		asyncService.executeAsyncTask();
		SiteUser author = userService.getUserByEmail(principal.getName());
		Video video = videoService.getVideo(videoId);
		
		String content = (String) payload.get("content");
		long videoTime = 0;
		
		return noteService.createNote(content, videoTime, author, video);
	}
	
//	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/create/{videoId}")
	public ResponseEntity<?> createNoteInViewer(@PathVariable("videoId") long videoId, @RequestBody Map<String, Object> payload, Principal principal) throws Exception {
		SiteUser author = userService.getUserByEmail(principal.getName());
		Video video = videoService.getVideo(videoId);
		
		String content = (String) payload.get("content");
		long videoTime = (Long) payload.get("videoTime");
		
		noteService.createNote(content, videoTime, author, video);
		return ResponseEntity.ok("노트가 생성 되었습니다.");
	}
	
	
	// 노트 삭제
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{noteId}")
	public String delNote(@PathVariable("noteId") long noteId, Principal principal) {
		Note note = noteService.getNote(noteId);
		if (note.getAuthor().getEmail().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
		}
		return new String();
	}
	
	
	
}
