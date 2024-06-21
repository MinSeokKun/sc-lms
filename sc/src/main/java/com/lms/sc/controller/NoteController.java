package com.lms.sc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {
	
	@GetMapping("/list/{userId}")
	public String getNoteList(@PathVariable("userId") long userId, Model model) {
		
		return "note/note_list";
	}
	
	
}
