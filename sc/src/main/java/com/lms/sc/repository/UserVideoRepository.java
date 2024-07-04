package com.lms.sc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;

public interface UserVideoRepository extends JpaRepository<UserVideo, Long> {
	
	Optional<UserVideo> findByUserAndVideo(SiteUser user, Video video);
	
	long countByUserAndWatchedTrue(SiteUser user);
	
	void deleteAllByVideo(Video video);

//	List<UserVideo> findByUserVideo(UserVideo userVideo);
	
//	UserVideo getUserVidoe(long userId, long vidId);
}
