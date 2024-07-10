package com.lms.sc.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.UserVideo;
import com.lms.sc.entity.Video;


public interface UserVideoRepository extends JpaRepository<UserVideo, Long> {
	
	Optional<UserVideo> findByUserAndVideo(SiteUser user, Video video);
	
	long countByUserAndWatchedTrue(SiteUser user);
	
	void deleteAllByVideo(Video video);
	
	void deleteByVideoAndUser(Video video, SiteUser user);

	
	@Query("SELECT uv FROM UserVideo uv JOIN uv.video v JOIN v.lecture l WHERE uv.user = :user AND l = :lecture AND uv.watched = :watched")
    List<UserVideo> findByUserAndLectureAndWatched(
            @Param("user") SiteUser user,
            @Param("lecture") Lecture lecture,
            @Param("watched") boolean watched);

	
	List<UserVideo> findByUserAndWatchedAtBetween(SiteUser user, Date startDate, Date endDate);
	
//	List<UserVideo> findByUserVideo(UserVideo userVideo);
	
//	UserVideo getUserVidoe(long userId, long vidId);
}
