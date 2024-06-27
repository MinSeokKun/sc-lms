package com.lms.sc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserLecture;

public interface UserLectureRepository extends JpaRepository<UserLecture, Long> {
	List<UserLecture> findByUser(SiteUser user);
	
	List<UserLecture> findByUserAndLecture(SiteUser user, Lecture lecture);
	
//	@Query("SELECT ul.video FROM UserLecture ul WHERE ul.user = :user")
//	List<Video> findVideosByUser(@Param("user") SiteUser user);
	
}
