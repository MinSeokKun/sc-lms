package com.lms.sc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Video;


public interface VideoRepository extends JpaRepository<Video, Long> {
}
