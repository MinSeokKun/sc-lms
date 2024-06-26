package com.lms.sc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.Note;
import com.lms.sc.entity.Video;
import com.lms.sc.service.NoteService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
	
	private final NoteService noteService;
	
	@GetMapping("/list/{userId}")
	public String getNoteList(@PathVariable("userId") long userId, Model model) {
		List<Lecture> noteLecture = noteService.getNoteLecture(userId);
		
		Map<Lecture, Integer> noteList = new HashMap<Lecture, Integer>();
		noteLecture.forEach(lecture -> 
			noteList.put(lecture, noteService.getByLecture(lecture.getId(), userId).size()));
		model.addAttribute("userId", userId);
		model.addAttribute("noteList", noteList);
		return "note/note_list";
	}
	
	@GetMapping("/list/{userId}/{lecId}")
	public String getMethodName(@PathVariable("userId") long userId,
			@PathVariable("lecId") long lecId, Model model) {
		List<Video> videoList = noteService.getVideosByLecture(lecId, userId);
		Map<Video, List<Note>> noteList = new HashMap<Video, List<Note>>();
		
		videoList.forEach(video -> noteList.put(video, noteService.getByVideo(video.getId(), userId)));
		
		model.addAttribute("noteList", noteList);
		
		return "note/note_lecture";
	}
	
	
	
}
