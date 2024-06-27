package com.lms.sc;



import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.lms.sc.entity.Lecture;
import com.lms.sc.entity.SiteUser;
import com.lms.sc.entity.Video;
import com.lms.sc.repository.LectureRepository;
import com.lms.sc.repository.UserRepository;
import com.lms.sc.repository.VideoRepository;
import com.lms.sc.service.LectureService;


@SpringBootTest
class ShTests {

	@Autowired
	private LectureRepository lr;
	
	@Autowired
	private VideoRepository lvr;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	private LectureService ls;
	
	@Test
	void lectureTest() {
		Lecture lec = new Lecture();
		lec.setTitle("python 강의");
		lec.setContent("python을 잘 배워볼까요~?");
		lec.setCreateDate(LocalDateTime.now());
		
		
        System.out.println("@@@@@@@@@@@Saved Lecture: " + lr.save(lec));
	}
	
//	@Test
	void lecVideoTest() {
		Lecture lecture = lr.findById(1L).orElse(null);
		Video lecVideo = new Video();
		lecVideo.setTitle("자바");
		lecVideo.setUrl("123123123");
		lecVideo.setLecture(lecture);
		
		lvr.save(lecVideo);
	}

}
