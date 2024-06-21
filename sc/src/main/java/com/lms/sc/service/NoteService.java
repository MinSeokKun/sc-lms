package com.lms.sc.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lms.sc.entity.Note;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.NoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoteService {
	private final NoteRepository noteRepository;
	
	// 노트생성
	public Note createNote(String content, long videoTime, SiteUser author, Video video) {
		Note note = new Note();
		note.setContent(content);
		note.setVideoTime(videoTime);
		note.setAuthor(author);
		note.setVideo(video);
		note.setCreateDate(LocalDateTime.now());
		return noteRepository.save(note);
	}
	
	// 노트삭제
	public void delNote(int id) {
		Note note = noteRepository.findById(id).get();
		noteRepository.delete(note);
	}
	
	
}
