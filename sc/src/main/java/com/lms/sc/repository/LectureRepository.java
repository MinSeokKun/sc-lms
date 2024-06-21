package com.lms.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
//	Optional<Lecture> findById(long id);
}
