package com.lms.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
