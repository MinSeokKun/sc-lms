package com.lms.sc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Note;
import com.lms.sc.entity.SiteUser;


public interface NoteRepository extends JpaRepository<Note, Integer> {
	List<Note> findByAuthor(SiteUser author);

}
