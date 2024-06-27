package com.lms.sc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Video;


public interface VideoRepository extends JpaRepository<Video, Long> {
	Optional<Video> findById(long id);
	List<Video> findByLectureId(long lectureId);
}
